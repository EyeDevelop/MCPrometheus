package org.rootedinc.prometheus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.util.Vector;
import org.rootedinc.prometheus.Prometheus;
import org.rootedinc.prometheus.interfaces.listeners.EventListener;
import org.rootedinc.prometheus.util.SignActions;

import java.util.HashMap;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class SignListener extends EventListener {

    // Keep track of the Bukkit scheduled ID.
    private static int redstoneCheckID = -1;

    // Keep track of the signs in use.
    private HashMap<Location, String> signsInUse = new HashMap<>();
    private HashMap<Location, Vector> signFaces = new HashMap<>();

    // Code being run every 20 ticks to check for redstone next to signs.
    private Runnable checkSignRedstone = new Runnable() {
        @Override
        public void run() {
            // Check redstone for each sign.
            for (Location signLocation : signsInUse.keySet()) {
                Block signBlock = signLocation.getWorld().getBlockAt(signLocation);

                // Find what that sign is supposed to do.
                String command = signsInUse.get(signLocation);

                // Check that it exists.
                if (command == null)
                    return;

                // Get the Vector pointing to behind the sign.
                Vector toBehind = signFaces.get(signLocation);

                // Get the neighbour blocks left, right and back.
                Vector toLeft = toBehind.clone().rotateAroundAxis(new Vector(0, 1, 0), Math.toRadians(90));
                toLeft.setX(Math.round(toLeft.getX()));
                toLeft.setZ(Math.round(toLeft.getZ()));

                Block blockLeft = signBlock.getRelative(toLeft.getBlockX(), 0, toLeft.getBlockZ());

                Vector toRight = toBehind.clone().rotateAroundAxis(new Vector(0, 1, 0), Math.toRadians(270)).normalize();
                toRight.setX(Math.round(toRight.getX()));
                toRight.setZ(Math.round(toRight.getZ()));

                Block blockRight = signBlock.getRelative(toRight.getBlockX(), 0, toRight.getBlockZ());
                Block blockBack = signBlock.getRelative(toBehind.getBlockX(), 0, toBehind.getBlockZ());

                Vector toFront = toBehind.clone().multiply(-1);
                Block blockFront = signBlock.getRelative(toFront.getBlockX(), 0, toFront.getBlockZ());

                // Check if the blocks to the side are powered.
                boolean leftPowered = blockLeft.isBlockIndirectlyPowered() && blockLeft.getType() != Material.AIR;
                boolean rightPowered = blockRight.isBlockIndirectlyPowered() && blockRight.getType() != Material.AIR;
                boolean frontPowered = blockFront.isBlockIndirectlyPowered() && blockFront.getType() != Material.AIR;

                // Run the action.
                SignActions.run(command, leftPowered, rightPowered, frontPowered, blockBack);
            }
        }
    };


    /**
     * When signs are placed, they need to be checked if they're our special signs.
     *
     * @param e The SignChangeEvent.
     */
    @EventHandler
    public void signPlacedEvent(SignChangeEvent e) {
        // First check if the commands are ready.
        SignActions.prepareActions();

        // Only do something when the sign has a specified first line.
        String firstLine = e.getLine(0);
        Location signLoc = e.getBlock().getLocation();

        if (firstLine != null) {
            if (SignActions.signCommands.containsKey(firstLine)) {
                signsInUse.put(signLoc, firstLine);

                // Figure out the facing Vector.
                BlockFace signSouthFace = e.getPlayer().getFacing().getOppositeFace();
                Block signNorthBlock = e.getBlock().getRelative(signSouthFace);

                Vector signToSouth = signLoc.toVector().subtract(signNorthBlock.getLocation().toVector()).normalize();
                signFaces.put(signLoc, signToSouth);

                Prometheus.Log(Level.INFO, firstLine + ": Sign placed at: " + stringifyLocation(signLoc));
            }
        }

        // Start a repeating task to check for redstone activation near the signs.
        if (signsInUse.keySet().size() > 0) {
            redstoneCheckID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Prometheus.instance, checkSignRedstone, 20, 2);
        }
    }

//    @EventHandler
//    public void redstoneActivatedNearSignEvent(BlockRedstoneEvent e) {
//        // Grab the sign closest to the redstone change.
//        Location redstoneChangeLocation = e.getBlock().getLocation();
//        Block closestSign = null;
//
//        for (Location l : signsInUse.keySet()) {
//            if (l.distance(redstoneChangeLocation) <= 1) {
//                closestSign = l.getWorld().getBlockAt(l);
//                break;
//            }
//        }
//        if (closestSign == null)
//            return;
//
//        // Find what that sign is supposed to do.
//        String command = signsInUse.get(closestSign.getLocation());
//
//        // Check that it exists.
//        if (command == null)
//            return;
//
//        // Get the neighbour blocks left, right and back.
//        // Make sure the new current is larger than 1 and the block is powered.
//        Block blockWest = closestSign.getRelative(BlockFace.WEST);
//        boolean leftPowered;
//        if (redstoneChangeLocation.distance(blockWest.getLocation()) < 1) {
//            leftPowered = blockWest.isBlockIndirectlyPowered() && e.getNewCurrent() > 1;
//        } else {
//            leftPowered = blockWest.isBlockIndirectlyPowered();
//        }
//
//        // Do the same for east.
//        Block blockEast = closestSign.getRelative(BlockFace.EAST);
//        boolean rightPowered;
//        if (redstoneChangeLocation.distance(blockEast.getLocation()) < 1) {
//            rightPowered = blockEast.isBlockIndirectlyPowered() && e.getNewCurrent() > 1;
//        } else {
//            rightPowered = blockEast.isBlockIndirectlyPowered();
//        }
//
//        Block blockSouth = closestSign.getRelative(BlockFace.SOUTH);
//
//        Prometheus.Log(Level.INFO, String.format("West: %b, East: %b, South: %b", blockWest.isBlockIndirectlyPowered(), blockEast.isBlockIndirectlyPowered(), blockSouth.isBlockIndirectlyPowered()));
//
//        // Run the action.
//        SignActions.run(command, leftPowered, rightPowered, blockSouth);
//    }

    /**
     * Helper function to convert a Location object to a String.
     *
     * @param l The Location object.
     * @return The Location as string.
     */
    private String stringifyLocation(Location l) {
        return l.toVector().toString() + l.getWorld().toString();
    }

    /**
     * Signs need to be removed from signsInUse to prevent glitches from happening.
     *
     * @param e The BlockBreakEvent.
     */
    @EventHandler
    public void signBreakEvent(BlockBreakEvent e) {
        if (signsInUse.containsKey(e.getBlock().getLocation())) {
            Prometheus.Log(Level.INFO, "Special sign at " + stringifyLocation(e.getBlock().getLocation()) + " removed.");
        }

        signsInUse.remove(e.getBlock().getLocation());

        // Stop the task if no signs are available.
        if (signsInUse.keySet().size() <= 0) {
            Bukkit.getScheduler().cancelTask(redstoneCheckID);
        }
    }
}

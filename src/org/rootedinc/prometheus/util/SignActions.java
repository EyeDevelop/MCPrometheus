package org.rootedinc.prometheus.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class SignActions {
    public static HashMap<String, SignActionRunnable> signCommands = new HashMap<>();
    private static boolean isPrepared = false;

    /**
     * Function that is called every tick when a sign from SignListener is active.
     *
     * @param command      The SignAction to be run.
     * @param leftPowered  Whether the block left of the sign is powered.
     * @param rightPowered Whether the block right of the sign is powered.
     * @param frontPowered Whether the block in front of the sign is powered.
     * @param backBlock    The backBlock to modify.
     */
    public static void run(String command, boolean leftPowered, boolean rightPowered, boolean frontPowered, Block backBlock) {
        // First make sure everything is prepared.
        if (!isPrepared) {
            prepareActions();
        }

        // Get the right action.
        SignActionRunnable action = signCommands.get(command);

        // Update the settings for the action to run properly.
        action.updateSettings(leftPowered, rightPowered, frontPowered, backBlock);

        // Run the action.
        action.run();
    }

    /**
     * Ready the signCommands list.
     */
    public static void prepareActions() {
        // Don't prepare if already prepared.
        if (isPrepared) {
            return;
        }

        // Prepare all the Sign actions.
        SignActionRunnable logicAnd = new LogicAnd();
        SignActionRunnable logicOr = new LogicOr();
        SignActionRunnable logicXor = new LogicXOR();
        SignActionRunnable logicNot = new LogicNOT();
        SignActionRunnable bigYeet = new BigYeet();

        // Register the Sign actions.
        signCommands.put("[AND]", logicAnd);
        signCommands.put("[OR]", logicOr);
        signCommands.put("[XOR]", logicXor);
        signCommands.put("[NOT]", logicNot);
        signCommands.put("BIGYEET", bigYeet);

        // We are done preparing.
        isPrepared = true;
    }

    // Define all the Sign Actions.
    private static class LogicAnd extends SignActionRunnable {

        @Override
        public void run() {
            if (leftPowered && rightPowered) {
                backBlock.setType(Material.REDSTONE_BLOCK);
            } else {
                backBlock.setType(Material.AIR);
            }
        }
    }

    private static class LogicOr extends SignActionRunnable {

        @Override
        public void run() {
            if (leftPowered || rightPowered) {
                backBlock.setType(Material.REDSTONE_BLOCK);
            } else {
                backBlock.setType(Material.AIR);
            }
        }
    }

    private static class LogicXOR extends SignActionRunnable {

        @Override
        public void run() {
            if ((leftPowered || rightPowered) && !(leftPowered && rightPowered)) {
                backBlock.setType(Material.REDSTONE_BLOCK);
            } else {
                backBlock.setType(Material.AIR);
            }
        }
    }

    private static class LogicNOT extends SignActionRunnable {

        @Override
        public void run() {
            if (frontPowered) {
                backBlock.setType(Material.AIR);
            } else {
                backBlock.setType(Material.REDSTONE_BLOCK);
            }
        }
    }

    private static class BigYeet extends SignActionRunnable {

        @Override
        public void run() {
            if (frontPowered) {
                Location tntSpawnLoc = backBlock.getLocation().add(1, 0, 0);
                for (int i = 0; i < 30; i++) {
                    tntSpawnLoc.getWorld().spawnEntity(tntSpawnLoc, EntityType.PRIMED_TNT);
                }
            }
        }
    }
}

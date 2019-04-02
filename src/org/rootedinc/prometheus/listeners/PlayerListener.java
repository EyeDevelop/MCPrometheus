package org.rootedinc.prometheus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.rootedinc.prometheus.interfaces.listeners.EventListener;
import org.rootedinc.prometheus.interfaces.listeners.IListenerManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * Class for Player events.
 */
public class PlayerListener extends EventListener {
    // Class variables.
    public static HashMap<UUID, Vector> playerYeet = new HashMap<>();

    public PlayerListener(IListenerManager listenerManager) {
        super(listenerManager);
    }

    /**
     * Function to reset the map on (re)load so the plugin doesn't crash.
     */
    @Override
    public void onReload() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            // Update value in map.
            playerYeet.put(p.getUniqueId(), new Vector(0d, 0d, 0d));
        }
    }

    /**
     * @param e The PlayerJoinEvent object.
     */
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        // Get the player.
        Player p = e.getPlayer();

        // Update the map.
        playerYeet.put(p.getUniqueId(), new Vector(0d, 0d, 0d));
    }

    /**
     * @param e The PlayerInteractEvent object.
     */
    @EventHandler
    public void playerActionEvent(PlayerInteractEvent e) {
        // Get the required variables.
        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack inHand = e.getItem();
        Block b = e.getClickedBlock();

        // Check if all variables are not null.
        if (inHand == null || b == null) {
            return;
        }

        // Check if the player is in the map with a value greater than 0..
        if (playerYeet.get(p.getUniqueId()).lengthSquared() == 0) {
            return;
        }

        // Check if the player right clicked the block.
        if (action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Check if the player was holding a stick with the Yeet name.
        if (inHand.getType() != Material.STICK) {
            return;
        }
        if (!inHand.getItemMeta().getDisplayName().equalsIgnoreCase("Yeeter")) {
            return;
        }

        // Perform the actual yeet.
        // Get the block location and apply offset.
        Location l = b.getLocation();
        l.add(new Vector(0.5f, 0.5f, 0.5f));

        // Summon the FallingBlock.
        FallingBlock newBlock = b.getWorld().spawnFallingBlock(l, b.getBlockData());

        // Apply the vertical yeet.
        Vector velocity = newBlock.getVelocity();
        Vector deltaVelocity = playerYeet.get(p.getUniqueId());
        velocity.add(deltaVelocity);
        newBlock.setVelocity(velocity);

        // Set the static block to air.
        b.setType(Material.AIR);
    }

    /**
     * @param e The PlayerInteractAtEntityEvent object.
     */
    @EventHandler
    public void playerEntityInteract(PlayerInteractEntityEvent e) {
        // Get the required variables.
        Player p = e.getPlayer();
        LivingEntity target = (LivingEntity) e.getRightClicked();
        ItemStack inHand1 = p.getInventory().getItemInMainHand();
        ItemStack inHand2 = p.getInventory().getItemInOffHand();

        // Check if the player is in the map with a value greater than 0..
        if (playerYeet.get(p.getUniqueId()).lengthSquared() == 0) {
            return;
        }

        // Check if the player was holding a stick with the Yeet name.
        if (inHand1.getType() != Material.STICK && inHand2.getType() != Material.STICK) {
            return;
        }
        if (!inHand1.hasItemMeta() && !inHand2.hasItemMeta()) {
            return;
        }
        if (!inHand1.getItemMeta().getDisplayName().equalsIgnoreCase("Yeeter") && !inHand2.getItemMeta().getDisplayName().equalsIgnoreCase("Yeeter")) {
            return;
        }

        // Perform the actual yeet.
        Vector velocity = target.getVelocity();
        Vector deltaVelocity = playerYeet.get(p.getUniqueId());
        velocity.add(deltaVelocity);
        target.setVelocity(velocity);
    }
}

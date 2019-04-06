package org.rootedinc.prometheus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.rootedinc.prometheus.Prometheus;
import org.rootedinc.prometheus.interfaces.listeners.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Class for Player events.
 */
@SuppressWarnings("unused")
public class EntityListener extends EventListener {
    // Class variables.
    public static HashMap<UUID, Vector> playerYeet = new HashMap<>();
    public static HashMap<UUID, Integer> playerFIL = new HashMap<>();
    public static boolean breedCausesDeath = false;

    /**
     * Helper function to calculate new velocity.
     *
     * @param oldVelocity The old velocity of the Entity.
     * @param direction   The direction the player is looking.
     * @param offset      The offset values to multiply to the direction.
     */
    private Vector calculateNewVelocity(Vector oldVelocity, Vector direction, Vector offset) {
        // Get whether the player is facing the Z or X axis.
        int indexOfLargest = 0;
        double largest = 0;
        double[] directionValues = {direction.getX(), direction.getY(), direction.getZ()};
        for (int i = 0; i < 3; i++) {
            if (Math.abs(directionValues[i]) > largest) {
                largest = Math.abs(directionValues[i]);
                indexOfLargest = i;
            }
        }
        double directionOffset = Math.round(directionValues[indexOfLargest]);

        // Set the forward and side variables appropriately.
        if (indexOfLargest == 2) {
            // Switch Z and X if player is facing Z axis.
            double offsetX = offset.getX();
            double offsetZ = offset.getZ();

            offset.setX(offsetZ);
            offset.setZ(offsetX);
        }

        // Make sure the offset velocity is directionally correct.
        offset.multiply(directionOffset);

        // Make sure the Y is still positive.
        offset.setY(Math.abs(offset.getY()));

        // Add that vector to the old velocity and return that.
        oldVelocity.add(offset);

        return oldVelocity;
    }

    /**
     * Handler for the PlayerInteractEvent.
     *
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

        // Check if the player is in the map.
        if (!playerYeet.containsKey(p.getUniqueId())) {
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
        Vector direction = p.getLocation().getDirection().normalize();
        Vector offset = playerYeet.get(p.getUniqueId()).clone();

        newBlock.setVelocity(calculateNewVelocity(velocity, direction, offset));

        // Set the static block to air.
        b.setType(Material.AIR);
    }

    /**
     * Handler for PlayerInteract (with) Entity event.
     *
     * @param e The PlayerInteractAtEntityEvent object.
     */
    @EventHandler
    public void playerEntityInteract(PlayerInteractEntityEvent e) {
        // Get the required variables.
        Player p = e.getPlayer();
        Entity target = e.getRightClicked();
        ItemStack inHand1 = p.getInventory().getItemInMainHand();
        ItemStack inHand2 = p.getInventory().getItemInOffHand();

        // Check if the player is in the map.
        if (!playerYeet.containsKey(p.getUniqueId())) {
            return;
        }

        // Check if the player was holding a stick with the Yeeter name.
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
        Vector direction = p.getLocation().getDirection().normalize();
        Vector offset = playerYeet.get(p.getUniqueId()).clone();

        target.setVelocity(calculateNewVelocity(velocity, direction, offset));
    }

    /**
     * Handler for when two animals breed.
     *
     * @param e The EntityBreedEvent.
     */
    @EventHandler
    public void dangerBreed(EntityBreedEvent e) {
        LivingEntity father = e.getFather();
        LivingEntity mother = e.getMother();
        LivingEntity child = e.getEntity();

        // First check if the event is enabled.
        if (!breedCausesDeath) {
            return;
        }

        // Explode the child.
        father.getWorld().createExplosion(father.getLocation(), 20f, true);

        // Add nausea to subject nearby.
        // Define PotionEffects
        PotionEffect peBlindness = new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 1, false, false);
        PotionEffect pePoison = new PotionEffect(PotionEffectType.POISON, 60 * 20, 0, false, false);
        PotionEffect peNausea = new PotionEffect(PotionEffectType.CONFUSION, 5 * 20, 1, false, false);

        // Store them in a list.
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(peBlindness);
        effects.add(pePoison);
        effects.add(peNausea);

        // Apply the effects to nearby entities in a 30 block radius.
        List<Entity> nearEntities = father.getNearbyEntities(100, 100, 100);
        for (Entity entity : nearEntities) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addPotionEffects(effects);
            }
        }
    }

    /**
     * The game Floor is Lava.
     *
     * @param e The PlayerMoveEvent.
     */
    @EventHandler
    public void playerWalking(PlayerMoveEvent e) {
        // Only run if the player is in the list with a value > 0.
        Player p = e.getPlayer();
        if (!playerFIL.containsKey(p.getUniqueId())) {
            return;
        }

        // Set the floor to lava if on the specified y coordinate.
        if (p.getLocation().getBlockY() == playerFIL.get(p.getUniqueId())) {
            Location blockBelowPlayerLoc = p.getLocation().clone().add(0, -1, 0);
            final Block blockBelowPlayer = p.getWorld().getBlockAt(blockBelowPlayerLoc);

            // Schedule the task to set the block to lava with 1s (20 ticks) delay.
            Bukkit.getScheduler().scheduleSyncDelayedTask(Prometheus.instance, new Runnable() {
                @Override
                public void run() {
                    blockBelowPlayer.setType(Material.LAVA);
                }
            }, 20);
        }
    }
}

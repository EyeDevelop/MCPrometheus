package org.rootedinc.prometheus.commands.SubPrometheus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.rootedinc.prometheus.interfaces.command.SubCommand;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

public class SubTower extends SubCommand {
    /**
     * Constructor for the custom SubCommand.
     */
    public SubTower() {
        super("tower", "Makes a tower of entities.", "/prometheus tower <amount>");
    }

    /**
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      Exactly what the player typed.
     * @param subArgs       The arguments given to the subcommand.
     */
    @Override
    public void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs) {
        // Tower height has to be specified.
        if (subArgs.length < 1) {
            displayHelp(commandSender);
            return;
        }

        // Only for players.
        if (! (commandSender instanceof Player))
            return;

        // Get a specified amount.
        int amount = 0;
        try {
            amount = Integer.parseInt(subArgs[0]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(prefixifyMsg("That's not a valid amount."));
        }

        // Get the player.
        Player p = (Player) commandSender;

        // Setup the tower parameters.
        EntityType entityType = EntityType.VILLAGER;
        LivingEntity livingEntity = (LivingEntity) p.getWorld().spawnEntity(p.getLocation(), entityType);

        // Make the tower.
        Entity previousEntity = livingEntity;
        for (int i = 0; i < amount; i++) {
            Entity newPassenger = p.getWorld().spawnEntity(livingEntity.getLocation(), entityType);

            previousEntity.addPassenger(newPassenger);
            previousEntity = newPassenger;
        }
    }
}

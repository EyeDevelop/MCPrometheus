package org.rootedinc.prometheus.commands.SubPrometheus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rootedinc.prometheus.interfaces.command.SubCommand;
import org.rootedinc.prometheus.listeners.EntityListener;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

public class SubDangerBreed extends SubCommand {

    /**
     * Constructor for the custom SubCommand.
     */
    public SubDangerBreed() {
        super("dangerbreed", "Makes breeding nuclear and dangerous.", "/prometheus dangerbreed <on/off>");
    }

    /**
     * Actual code executed when SubDangerBreed is executed.
     *
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      Exactly what the player typed.
     * @param subArgs       The arguments given to the subcommand.
     */
    @Override
    public void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs) {
        Player p = null;

        // If the sender is a player, cast sender to a player.
        if (commandSender instanceof Player) {
            p = (Player) commandSender;
        }

        // Check if that happened.
        if (p == null) {
            commandSender.sendMessage(prefixifyMsg("This command is only for players."));
            return;
        }

        if (subArgs.length >= 1) {
            // Check if the user wants to enable or disable breeding death.
            boolean enableDeath = subArgs[0].equalsIgnoreCase("on");

            // Enable death.
            p.sendMessage(prefixifyMsg("Breeding is now " + (enableDeath ? "" : "not ") + "nuclear."));
            EntityListener.breedCausesDeath = enableDeath;
        } else {
            displayHelp(commandSender);
        }
    }
}

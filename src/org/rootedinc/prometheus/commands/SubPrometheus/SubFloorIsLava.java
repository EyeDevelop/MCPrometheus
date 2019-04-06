package org.rootedinc.prometheus.commands.SubPrometheus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rootedinc.prometheus.interfaces.command.SubCommand;
import org.rootedinc.prometheus.listeners.EntityListener;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

public class SubFloorIsLava extends SubCommand {

    /**
     * Constructor for the custom SubCommand.
     */
    public SubFloorIsLava() {
        super("floorislava", "The age old game of Floor is Lava.", "/prometheus floorislava <on/off>");
    }

    /**
     * The actual code being ran.
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      Exactly what the player typed.
     * @param subArgs       The arguments given to the subcommand.
     */
    @Override
    public void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs) {
        // Command is for players only.
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(prefixifyMsg("Command is only for players."));
            return;
        }

        // Check if on/off has been specified.
        if (subArgs.length < 1) {
            displayHelp(commandSender);
            return;
        }

        // Check if the player wants to play.
        boolean playFIL = subArgs[0].equalsIgnoreCase("on");

        // Get the height of the player.
        Player p = (Player) commandSender;
        int height = playFIL ? p.getLocation().getBlockY() : -1; // Ternary.

        // Set the value.
        if (height > 0) {
            EntityListener.playerFIL.put(p.getUniqueId(), height);
        } else {
            EntityListener.playerFIL.remove(p.getUniqueId());
        }

        p.sendMessage(prefixifyMsg(String.format("Game is now %s!", playFIL ? "active" : "stopped")));
    }
}

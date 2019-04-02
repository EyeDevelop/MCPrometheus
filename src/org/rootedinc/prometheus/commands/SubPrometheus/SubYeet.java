package org.rootedinc.prometheus.commands.SubPrometheus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.rootedinc.prometheus.interfaces.command.ICommandEx;
import org.rootedinc.prometheus.interfaces.command.SubCommand;
import org.rootedinc.prometheus.listeners.PlayerListener;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

/**
 * The definition for /prometheus yeet.
 */
public class SubYeet extends SubCommand {
    /**
     * @param name The subcommand name.
     * @param shortDescription A short description of what this subcommand does.
     * @param usage The correct usage for this command.
     * @param commandParent The parent command.
     */
    public SubYeet(String name, String shortDescription, String usage, ICommandEx commandParent) {
        super(name, shortDescription, usage, commandParent);
    }

    /**
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

        // Get the Vector values from the player.
        if (subArgs.length >= 3) {
            String Sx, Sy, Sz;
            Sx = subArgs[0];
            Sy = subArgs[1];
            Sz = subArgs[2];

            double x, y, z;

            // Test if these are float values.
            try {
                x = Double.parseDouble(Sx);
                y = Double.parseDouble(Sy);
                z = Double.parseDouble(Sz);
            } catch (NumberFormatException e) {
                p.sendMessage(prefixifyMsg("That is not a valid (x y z) vector."));
                return;
            }

            // Values are usable now.
            // Store them in the appropriate HashMap.
            p.sendMessage(prefixifyMsg(String.format("Yeeting is now enabled with (%f, %f, %f).", x, y ,z)));
            PlayerListener.playerYeet.put(p.getUniqueId(), new Vector(x, y, z));
        }

        else if (subArgs.length >= 1) {
            // Check if the user wants to enable or disable yeeting.
            boolean enableYeet = subArgs[0].equalsIgnoreCase("on");
            if (!enableYeet) {
                // Disable yeeting.
                p.sendMessage(prefixifyMsg("Yeeting is now disabled."));
                PlayerListener.playerYeet.put(p.getUniqueId(), new Vector());
                return;
            }

            // Enable yeeting.
            p.sendMessage(prefixifyMsg("Yeeting is now enabled."));
            PlayerListener.playerYeet.put(p.getUniqueId(), new Vector(0d, 2d, 0d));
        }

        else {
            displayHelp(commandSender);
        }
    }
}

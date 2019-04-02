package org.rootedinc.prometheus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.rootedinc.prometheus.commands.SubPrometheus.SubYeet;
import org.rootedinc.prometheus.interfaces.command.CommandEx;
import org.rootedinc.prometheus.interfaces.command.SubCommand;

/**
 * The definition of the /prometheus command.
 */
public class CmdPrometheus extends CommandEx {

    // Define subcommands.
    SubCommand subYeet = new SubYeet("yeet", "Throw stuff around with a stick.", "/pth yeet <on/off/x y z>", this);

    /**
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     */
    @Override
    public void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs) {
        // Display subcommands.
        displayHelp(commandSender, cmdLabel);
    }

}

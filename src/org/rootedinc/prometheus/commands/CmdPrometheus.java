package org.rootedinc.prometheus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.rootedinc.prometheus.commands.SubPrometheus.SubDangerBreed;
import org.rootedinc.prometheus.commands.SubPrometheus.SubFloorIsLava;
import org.rootedinc.prometheus.commands.SubPrometheus.SubTower;
import org.rootedinc.prometheus.commands.SubPrometheus.SubYeet;
import org.rootedinc.prometheus.interfaces.command.CommandEx;
import org.rootedinc.prometheus.interfaces.command.SubCommand;

/**
 * The definition of the /prometheus command.
 */
public class CmdPrometheus extends CommandEx {

    /**
     * Function for initialisation of command.
     */
    public CmdPrometheus() {
        super("prometheus");

        // Define and register subcommands.
        SubCommand subYeet = new SubYeet();
        registerSubCommand(subYeet.getName(), subYeet);

        SubCommand subDeath = new SubDangerBreed();
        registerSubCommand(subDeath.getName(), subDeath);

        SubCommand subFIS = new SubFloorIsLava();
        registerSubCommand(subFIS.getName(), subFIS);

        SubCommand subTower = new SubTower();
        registerSubCommand(subTower.getName(), subTower);
    }

    /**
     * Main code being executed when the Command is ran.
     *
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     */
    @Override
    public void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs) {
        // Display subcommands.
        displayHelp(commandSender, cmdLabel, 1);
    }

}

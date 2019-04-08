package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

/**
 *
 */
public abstract class SubCommand implements ISubCommand {

    private String name;
    private String shortDescription;
    private String usage;

    /**
     * Constructor for the custom SubCommand.
     *
     * @param name             The subcommand name.
     * @param shortDescription A short description of what this subcommand does.
     * @param usage            The correct usage for this command.
     */
    public SubCommand(String name, String shortDescription, String usage) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.usage = usage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public abstract void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs);

    @Override
    public void displayHelp(CommandSender commandSender) {
        // Send help to sender.
        String helpMsg = "Help for " + name + ":\n";
        helpMsg += getUsage() + ": " + getShortDescription();

        commandSender.sendMessage(prefixifyMsg(helpMsg));
    }
}

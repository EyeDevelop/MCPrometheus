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
     * @param name The subcommand name.
     * @param shortDescription A short description of what this subcommand does.
     * @param usage The correct usage for this command.
     */
    public SubCommand(String name, String shortDescription, String usage) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.usage = usage;
    }

    /**
     * @param name The subcommand name.
     * @param shortDescription A short description of what this subcommand does.
     * @param commandParent The parent command to which this subcommand (soon) belongs.
     * @param usage The correct usage for this command.
     */
    public SubCommand(String name, String shortDescription, String usage, ICommandEx commandParent) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.usage = usage;

        // Register command.
        commandParent.registerSubCommand(this.name, this);
    }

    /**
     * @return The short description.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @return The subcommand name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The subcommands usage.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @param commandSender The initiator of the command.
     * @param command The command object.
     * @param cmdLabel Exactly what the player typed.
     * @param subArgs The arguments given to the subcommand.
     */
    @Override
    public abstract void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs);

    /**
     * @param commandSender The initiator of the command.
     */
    @Override
    public void displayHelp(CommandSender commandSender) {
        // Send help to sender.
        String helpMsg = "Help for " + name + ":\n";
        helpMsg += getUsage() + ": " + getShortDescription();

        commandSender.sendMessage(prefixifyMsg(helpMsg));
    }
}

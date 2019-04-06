package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements ICommandManager {

    private List<ICommandEx> commands = new ArrayList<>();
    private JavaPlugin parentPlugin;


    /**
     * Constructor that takes a parent plugin for registering commands.
     *
     * @param parentPlugin The parent plugin.
     */
    public CommandManager(JavaPlugin parentPlugin) {
        this.parentPlugin = parentPlugin;
    }


    /**
     * Function to register a command in this manager.
     *
     * @param command The command to add.
     */
    @Override
    public void registerCommand(ICommandEx command) {
        commands.add(command);
    }

    /**
     * Function to retrieve all commands registered.
     *
     * @return
     */
    @Override
    public List<ICommandEx> getCommands() {
        return commands;
    }

    /**
     * Function to perform the Bukkit registration.
     */
    @Override
    public void bukkitRegister() {
        for (ICommandEx command : commands) {
            // Get the command.
            PluginCommand c = parentPlugin.getCommand(command.getCommandName());

            // Register the command with Bukkit.
            if (c != null) {
                c.setExecutor(command);
            }
        }
    }
}

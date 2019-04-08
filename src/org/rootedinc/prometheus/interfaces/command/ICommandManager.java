package org.rootedinc.prometheus.interfaces.command;

import java.util.List;

public interface ICommandManager {

    /**
     * Function to register a command in this manager.
     *
     * @param command The command to add.
     */
    void registerCommand(ICommandEx command);

    /**
     * Function to retrieve all commands registered.
     *
     * @return The list of CommandEx objects.
     */
    List<ICommandEx> getCommands();

    /**
     * Function to perform the Bukkit registration.
     */
    void bukkitRegister();
}

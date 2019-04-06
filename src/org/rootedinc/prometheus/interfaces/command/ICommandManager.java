package org.rootedinc.prometheus.interfaces.command;

import java.util.List;

public interface ICommandManager {

    void registerCommand(ICommandEx command);

    List<ICommandEx> getCommands();

    void bukkitRegister();
}

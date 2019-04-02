package org.rootedinc.prometheus.interfaces.listeners;

public interface IListenerManager {

    void registerListener(IEventListener listener);

    void doSimpleBukkitRegister();

    void doBukkitRegister();

    void onReload();

}

package org.rootedinc.prometheus.util;

import org.bukkit.block.Block;

public abstract class SignActionRunnable implements Runnable {

    boolean leftPowered;
    boolean rightPowered;
    boolean frontPowered;
    Block backBlock;

    /**
     * Dummy constructor.
     */
    SignActionRunnable() {

    }

    /**
     * Makes sure the Runnable can use the variables needed for a SignAction.
     * @param blockLeft Whether the block to the left of the sign is powered.
     * @param blockRight Whether the block to the right of the sign is powered.
     * @param blockFront Whether the block in front of the sign is powered.
     * @param blockBack The backBlock object.
     */
    void updateSettings(boolean blockLeft, boolean blockRight, boolean frontPowered, Block blockBack) {
        this.leftPowered = blockLeft;
        this.rightPowered = blockRight;
        this.frontPowered = frontPowered;
        this.backBlock = blockBack;
    }

    /**
     * The actual SignAction that needs to be instantiated.
     */
    @Override
    public abstract void run();
}

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
     * @param leftPowered Whether the block to the left of the sign is powered.
     * @param rightPowered Whether the block to the right of the sign is powered.
     * @param frontPowered Whether the block in front of the sign is powered.
     * @param blockBack The backBlock object.
     */
    void updateSettings(boolean leftPowered, boolean rightPowered, boolean frontPowered, Block blockBack) {
        this.leftPowered = leftPowered;
        this.rightPowered = rightPowered;
        this.frontPowered = frontPowered;
        this.backBlock = blockBack;
    }

    /**
     * The actual SignAction that needs to be instantiated.
     */
    @Override
    public abstract void run();
}

package de.derioo.inventoryframework.interfaces;

import net.kyori.adventure.text.Component;

/**
 * Used to create title animations
 */
public interface TitleAnimation {



    /**
     * Prepares the animation
     * @param type the animation type
     * @return the animation
     */
    TitleAnimation prepare(TitleAnimation.AnimationType type);


    /**
     * Starts the animation
     * @param delayBetweenTextChanges sets the delay between every change
     * @param unit the unit of the delay
     * @param inputs the titles
     * @return the animation
     * @deprecated use the component method
     */
    @Deprecated
    TitleAnimation start(long delayBetweenTextChanges, InventoryAnimation.TimeUnit unit, String... inputs);

    /**
     * Starts the animation
     * @param delayBetweenTextChanges sets the delay between every change
     * @param unit the unit of the delay
     * @param inputs the titles
     * @return the animation
     */
    TitleAnimation start(long delayBetweenTextChanges, InventoryAnimation.TimeUnit unit, Component... inputs);


    /**
     * Different animation types
     */
    enum AnimationType {

        /**
         * Use this if you want to cycle the animation
         */
        CYCLE_INFINITE,

        /**
         * Used to cycle it first normally then backwards and then normally, ...
         */
        CYCLE_INFINITE_BACK_AND_FRONT,

        /**
         * Cycles it just once
         */
        CYCLE_ONCE,



    }
}

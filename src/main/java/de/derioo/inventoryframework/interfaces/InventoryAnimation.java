package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Used to create Item Animations aka. InventoryAnimations.
 */
public interface InventoryAnimation {

    /**
     * Prepares the animation with the specified type.
     *
     * @param type The type of animation.
     * @return The prepared animation.
     */
    InventoryAnimation prepare(AnimationType type);

    /**
     * Indicates that the item should be hidden after the animation finishes.
     *
     * @return The animation.
     */
    InventoryAnimation hideAfterAnimation();

    /**
     * Indicates that the animation should cycle infinitely.
     *
     * @return The animation.
     */
    InventoryAnimation cycleInfinite();

    /**
     * Starts the animation with the provided delay between items and the given animation items.
     *
     * @param delayBetweenItems The delay between items in the animation.
     * @param unit              The time unit of the delay.
     * @param items             The items to animate.
     * @return The animation.
     */
    InventoryAnimation start(long delayBetweenItems, TimeUnit unit, AnimationItem... items);

    /**
     * Starts the animation with the provided delay between items and the given animation items.
     *
     * @param delayBetweenItems The delay between items in the animation.
     * @param unit              The time unit of the delay.
     * @param items             The list of items to animate.
     * @return The animation.
     */
    InventoryAnimation start(long delayBetweenItems, TimeUnit unit, List<AnimationItem> items);

    /**
     * Represents an item in an animation with its properties.
     */
    class AnimationItem {

        @Getter
        private final SmartItem item;
        @Getter
        private final int animationLength;
        @Getter
        private final int startSlot;
        @Getter
        @Setter
        private int currentSlot;
        @Getter
        @Setter
        private int stepCount = 0;

        /**
         * Creates a new AnimationItem with the provided SmartItem, animation length, and start slot.
         *
         * @param item           The SmartItem to animate.
         * @param animationLength The length of the animation (number of steps).
         * @param startSlot      The starting slot index for the animation.
         */
        public AnimationItem(SmartItem item, int animationLength, int startSlot) {
            this.item = item;
            this.animationLength = animationLength;
            this.startSlot = startSlot;
            this.currentSlot = this.startSlot;
        }
    }

    /**
     * Represents the time units used for delays in the animation.
     */
    enum TimeUnit {

        /**
         * Used to indicate milliseconds
         */
        MILLISECONDS,

        /**
         * Used to indicate ticks
         */
        TICKS,
        /**
         * Used to indicate seconds
         */
        SECONDS,

        /**
         * Used to indicate minutes
         */
        MINUTES
    }

    /**
     * Represents the types of animations that can be created.
     */
    enum AnimationType {

        /**
         * Represents a horizontal line from left to right
         */
        HORIZONTAL_LEFT_RIGHT,

        /**
         * Represents a horizontal line from right to left
         */
        HORIZONTAL_RIGHT_LEFT,

        /**
         * Represents a vertical line from up to down
         */
        VERTICAL_UP_DOWN,

        /**
         * Represents a vertical line down up to up
         */
        VERTICAL_DOWN_UP,

        /**
         * Represents a line from the upper left corner to the bottom right corner
         */
        CROSS_LEFT_UP_RIGHT_DOWN,

        /**
         * Represents a line from the upper right corner to the bottom left corner
         */
        CROSS_RIGHT_UP_LEFT_DOWN
    }
}
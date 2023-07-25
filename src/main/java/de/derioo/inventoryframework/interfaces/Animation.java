package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

public interface Animation {

    /**
     * Prefers the animation
     * @param type the animation type
     * @return the animation
     */
    Animation prepare(AnimationType type);


    /**
     * Used when the item should hide after animation
     * @return the animation
     */
    Animation hideAfterAnimation();

    /**
     * Cycles the animation forever
     * @return the animation
     */
    Animation cycleInfinite();


    /**
     * Starts the animation
     * @param delayBetweenItems sets the delay between items
     * @param unit sets the unit of the delay
     * @param items the items
     * @return the animation
     */
    Animation start(long delayBetweenItems,TimeUnit unit ,AnimationItem... items);

    /**
     * Starts the animation
     * @param delayBetweenItems sets the delay between items
     * @param unit sets the unit of the delay
     * @param items the items
     * @return the animation
     */
    Animation start(long delayBetweenItems,TimeUnit unit, List<AnimationItem> items);


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

        public AnimationItem(SmartItem item, int animationLength, int startSlot) {
            this.item = item;
            this.animationLength = animationLength;
            this.startSlot = startSlot;
            this.currentSlot = this.startSlot;
        }




    }


    enum TimeUnit {

        MILLISECONDS,

        TICKS,

        SECONDS,

        MINUTES,
    }

    enum AnimationType{

        HORIZONTAL_LEFT_RIGHT,

        HORIZONTAL_RIGHT_LEFT,

        VERTICAL_UP_DOWN,

        VERTICAL_DOWN_UP,

        CROSS_LEFT_UP_RIGHT_DOWN,

        CROSS_RIGHT_UP_LEFT_DOWN,

    }




}

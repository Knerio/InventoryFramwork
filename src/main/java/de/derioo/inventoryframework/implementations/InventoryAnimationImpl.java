package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryAnimation;
import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * An implementation of the InventoryAnimation interface for creating item animations in inventories.
 */
public class InventoryAnimationImpl implements InventoryAnimation {

    private final InventoryBuilder builder;
    private final Plugin plugin;
    private AnimationType type;
    private boolean cycle;

    private boolean hideAfterAnimation = false;

    /**
     * Creates a new InventoryAnimationImpl with the specified InventoryBuilder and Plugin.
     *
     * @param builder The InventoryBuilder to animate.
     * @param plugin  The Plugin instance for scheduling tasks.
     */
    public InventoryAnimationImpl(InventoryBuilder builder, Plugin plugin) {
        this.builder = builder;
        this.plugin = plugin;
    }

    @Override
    public InventoryAnimation prepare(AnimationType t) {
        this.type = t;
        return this;
    }

    @Override
    public InventoryAnimation hideAfterAnimation() {
        this.hideAfterAnimation = true;
        return this;
    }

    @Override
    public InventoryAnimation cycleInfinite() {
        this.cycle = true;
        return this;
    }

    @Override
    public InventoryAnimation start(long delayBetweenItems, TimeUnit unit, AnimationItem... items) {
        long delayBetweenItemsInTicks = this.getTicksFromTimeUnit(delayBetweenItems, unit);

        InventoryContents contents = this.builder.getContents();

        final Map<Integer, SmartItem> map = new HashMap<>();

        for (int k = 0; k < contents.getItems().length; k++) {
            if (contents.getItem(k) == null) {
                map.put(k, SmartItem.get(new ItemStack(Material.AIR)));
                continue;
            }
            map.put(k, contents.getItem(k));
        }

        new BukkitRunnable() {
            int displayedItems = 0;

            @Override
            public void run() {
                SmartItem[] newContents = contents.getItems();

                // First load the map into the array
                for (AnimationItem item : items) {
                    newContents[item.getStartSlot()] = map.get(item.getStartSlot());
                    newContents[item.getCurrentSlot()] = map.get(item.getCurrentSlot());
                }

                // Render the next slots
                for (int i = 0; i < displayedItems; i++) {
                    AnimationItem item = items[i];
                    int nextSlot = getNextSlotByType(item.getCurrentSlot(), type, displayedItems + 1);
                    if (item.getStepCount() > item.getAnimationLength()) continue;
                    item.setCurrentSlot(nextSlot);
                    item.setStepCount(item.getStepCount() + 1);
                    newContents[item.getCurrentSlot()] = item.getItem();
                }

                // Check if the next item can or has to be rendered
                if (displayedItems < items.length) {
                    newContents[items[displayedItems].getStartSlot()] = items[displayedItems].getItem();
                    displayedItems++;
                }

                // Check if the runnable has to be canceled
                AnimationItem lastItem = items[items.length - 1];
                if (lastItem.getStepCount() == lastItem.getAnimationLength() + 1) {
                    this.cancel();
                    if (hideAfterAnimation)
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            for (AnimationItem item : items) {
                                newContents[item.getStartSlot()] = map.get(item.getStartSlot());
                                newContents[item.getCurrentSlot()] = map.get(item.getCurrentSlot());
                            }
                            contents.update();
                            if (cycle) {
                                // Reset data from the items to start a new Animation
                                removeDataFromItems(items);
                                start(delayBetweenItems, unit, items);
                            }
                        }, delayBetweenItemsInTicks);
                    if (!hideAfterAnimation && cycle) {
                        // Reset data from the items to start a new Animation
                        removeDataFromItems(items);
                        start(delayBetweenItems, unit, items);
                    }
                    contents.update();
                }

                contents.setItems(newContents);
                contents.update();
            }
        }.runTaskTimer(plugin, delayBetweenItemsInTicks, delayBetweenItemsInTicks);

        return this;
    }

    private void removeDataFromItems(AnimationItem... items) {
        Arrays.stream(items).forEach(item -> {
            item.setStepCount(0);
            item.setCurrentSlot(item.getStartSlot());
        });
    }

    @Override
    public InventoryAnimation start(long delayBetweenItems, TimeUnit unit, List<AnimationItem> items) {
        AnimationItem[] array = items.toArray(new AnimationItem[]{});

        this.start(delayBetweenItems, unit, array);
        return this;
    }

    private long getTicksFromTimeUnit(long time, TimeUnit unit) {
        long ticks;
        switch (unit) {
            case MINUTES -> ticks = time * 60 * 20;
            case SECONDS -> ticks = time * 20;
            case MILLISECONDS -> ticks = time / 50;
            case TICKS -> ticks = time;
            default -> ticks = -1;
        }
        return ticks;
    }

    private int getNextSlotByType(int slot, AnimationType type, int cycle) {
        switch (type) {
            case VERTICAL_UP_DOWN -> {
                return slot + 9;
            }
            case VERTICAL_DOWN_UP -> {
                return slot - 9;
            }
            case HORIZONTAL_LEFT_RIGHT -> {
                return slot + 1;
            }
            case HORIZONTAL_RIGHT_LEFT -> {
                return slot - 1;
            }
            case CROSS_LEFT_UP_RIGHT_DOWN -> {
                return slot + 10;
            }
            case CROSS_RIGHT_UP_LEFT_DOWN -> {
                return slot - 10;
            }
            default -> {
                return -1;
            }
        }
    }
}

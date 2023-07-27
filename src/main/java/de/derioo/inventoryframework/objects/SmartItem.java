package de.derioo.inventoryframework.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents a smart wrapper for an ItemStack with additional functionality.
 */
public class SmartItem {

    @Getter
    @Setter
    private ItemStack item;

    @Getter
    private final BiConsumer<InventoryClickEvent, SmartItem> consumer;

    /**
     * Constructs a new SmartItem with the given ItemStack and event consumer.
     * @param item the ItemStack to wrap
     * @param consumer the event consumer to handle click events
     */
    public SmartItem(ItemStack item, BiConsumer<InventoryClickEvent, SmartItem> consumer) {
        this.item = item;
        this.consumer = consumer;
    }

    /**
     * Gets a new SmartItem from the given ItemStack and event consumer.
     * @param item the ItemStack to wrap
     * @param consumer the event consumer to handle click events
     * @return the new SmartItem instance
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull SmartItem get(ItemStack item, BiConsumer<InventoryClickEvent, SmartItem> consumer) {
        return new SmartItem(item, consumer);
    }

    /**
     * Gets a new SmartItem from the given ItemStack.
     * @param item the ItemStack to wrap
     * @return the new SmartItem instance with an empty event consumer
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull SmartItem get(ItemStack item) {
        return new SmartItem(item, (event, i) -> {} );
    }

    /**
     * Gets a new SmartItem from the given Material and event consumer.
     * @param material the Material to create the ItemStack from
     * @param consumer the event consumer to handle click events
     * @return the new SmartItem instance
     */
    @Contract("_, _ -> new")
    public static @NotNull SmartItem get(Material material, BiConsumer<InventoryClickEvent, SmartItem> consumer) {
        return new SmartItem(new ItemStack(material), consumer);
    }

    /**
     * Gets a new SmartItem from the given Material.
     * @param material the Material to create the ItemStack from
     * @return the new SmartItem instance with an empty event consumer
     */
    @Contract("_ -> new")
    public static @NotNull SmartItem get(Material material) {
        return new SmartItem(new ItemStack(material), (event, item) -> {});
    }

    /**
     * Sets the type of the inner ItemStack.
     * @param type the new Material type to set
     */
    public void setType(Material type){
        this.item.setType(type);
    }

    /**
     * Clones the SmartItem to a new instance.
     * @return the cloned SmartItem instance
     */
    public SmartItem clone(){
        return new SmartItem(item.clone(), consumer);
    }
}
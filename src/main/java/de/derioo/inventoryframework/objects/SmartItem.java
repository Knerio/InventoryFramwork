package de.derioo.inventoryframework.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class SmartItem {

    @Getter
    @Setter
    private ItemStack item;

    @Getter
    private BiConsumer<InventoryClickEvent, SmartItem> consumer;
    

    public SmartItem(ItemStack item, BiConsumer<InventoryClickEvent, SmartItem> consumer) {
        this.item = item;
        this.consumer = consumer;
    }

    /**
     * Gets a SmartItem from an ItemStack
     * @param item the item
     * @param consumer the event
     * @return the item
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull SmartItem get(ItemStack item, BiConsumer<InventoryClickEvent, SmartItem> consumer) {
        return new SmartItem(item, consumer);
    }


    /**
     * Gets a SmartItem from an ItemStack
     * @param item the item
     * @return the item
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull SmartItem get(ItemStack item) {
        return new SmartItem(item, (event, i) -> {} );
    }

    /**
     * Gets a SmartItem from a Material
     * @param material the material
     * @return the item
     */
    @Contract("_, _ -> new")
    public static @NotNull SmartItem get(Material material, BiConsumer<InventoryClickEvent, SmartItem> consumer) {
        return new SmartItem(new ItemStack(material), consumer);
    }

    /**
     * Gets a SmartItem from a Material
     * @param material the material
     * @return the item
     */
    @Contract("_ -> new")
    public static @NotNull SmartItem get(Material material) {
        return new SmartItem(new ItemStack(material), (event, item) -> {});
    }

    /**
     * Sets the type of the inner ItemStack
     * @param type the new type
     */
    public void setType(Material type){
        this.item.setType(type);
    }


    /**
     * Clones to smartItem to a new Instance
     * @return the cloned item
     */
    public SmartItem clone(){
        return new SmartItem(item.clone(), consumer);
    }

}

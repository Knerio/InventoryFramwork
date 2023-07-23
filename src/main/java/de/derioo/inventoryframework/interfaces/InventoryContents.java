package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface InventoryContents {

    /**
     * Gets the items as a SmartItem Array
     * @return the array
     */
    SmartItem[] getItems();

    /**
     * Gets the items as a ItemStack Array
     * @return the array
     */
    ItemStack[] getRawItems();

    void setMaxPages(int pages);

    /**
     * Sets a new array
     * @param items the new array
     */
    void setItems(SmartItem[] items);

    /**
     * Gets an item in an index
     * @param index the index
     * @return the item
     */
    @Nullable SmartItem getItem(int index);



    /**
     * Sets an item in at index
     * @param index the index
     * @param item the item
     */
    void set(int index, SmartItem item);

    /**
     * Sets an item in at index
     * @param index the index
     * @param item the item
     */
    void set(int index, ItemStack item);

    /**
     * Fills the whole inventory with the material
     */
    void fill(Material material);

    /**
     * Fills the whole inventory with the material
     */
    void fill(Material material, String name);

    /**
     *Fills the whole inventory with the item
     */
    void fill(SmartItem item);


    PageSystem getPageSystem();

    InventoryBuilder getBuilder();

    void update();

}

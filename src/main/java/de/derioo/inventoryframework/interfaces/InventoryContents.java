package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface InventoryContents {

    /**
     * Gets the inner Array of items
     * @return the array
     */
    SmartItem[] getItems();

    /**
     * Gets the Items in from of ItemStacks
     * @see InventoryContents#getItems()
     * @return the array
     */
    ItemStack[] getRawItems();

    /**
     * Sets the inner array
     * @param items the new Array
     */
    void setItems(SmartItem[] items);

    /**
     * Gets a item
     * @param index the index
     * @return the Item
     */
    @Nullable SmartItem getItem(int index);


    /**
     * Sets an item
     * @param index the index
     */
    void set(int index, SmartItem item);

    /**
     * Sets an item
     * @param index the index
     */
    void set(int index, ItemStack item);

    /**
     * Fills the whole inventory with the material
     * @param material the material
     */
    void fill(Material material);

    /**
     * Fills the whole inventory with the material and nam
     * @param material the material
     * @param name the name
     */
    void fill(Material material, String name);


    void fill(SmartItem item);


    /**
     * Fills the borders with the material
     * @param material the material
     */
    void fillBorders(Material material);

    /**
     * Fills the borders with the material and name
     * @param material the material
     * @param name the name
     */
    void fillBorders(Material material, String name);



    /**
     * Fills the borders with the item
     * @param item the item
     */
    void fillBorders(SmartItem item);


    /**
     * Gets the PageSystem of the contents
     * @return the System
     */
    PageSystem getPageSystem();

    /**
     * Gets the InventoryBuilder of the contents
     * @return the InventoryBuilder
     */
    InventoryBuilder getBuilder();

    /**
     * Updates the inventory
     */
    void update();

}

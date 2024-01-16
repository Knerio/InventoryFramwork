package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * Represents the contents of an inventory with methods to manipulate and manage its items.
 */
public interface InventoryContents {

    /**
     * Gets the inner array of SmartItems representing the contents of the inventory.
     * @return the array of SmartItems
     */
    SmartItem[] getItems();

    /**
     * Gets the items in the form of ItemStacks from the inventory.
     * @see InventoryContents#getItems()
     * @return the array of ItemStacks
     */
    ItemStack[] getRawItems();

    /**
     * Sets the inner array of SmartItems representing the contents of the inventory.
     * @param items the new array of SmartItems
     */
    void setItems(SmartItem[] items);

    /**
     * Gets a SmartItem at the specified index in the inventory.
     * @param index the index of the SmartItem
     * @return the SmartItem at the given index, or null if not present
     */
    @Nullable SmartItem getItem(int index);

    /**
     * Sets a SmartItem at the specified index in the inventory.
     * @param index the index to set the SmartItem at
     * @param item the SmartItem to set
     */
    void set(int index, SmartItem item);

    /**
     * Sets a regular ItemStack at the specified index in the inventory.
     * @param index the index to set the ItemStack at
     * @param item the ItemStack to set
     */
    void set(int index, ItemStack item);

    /**
     * Fills the entire inventory with the specified material.
     * @param material the material to fill the inventory with
     */
    void fill(Material material);

    /**
     * Fills the entire inventory with the specified material and name.
     * @param material the material to fill the inventory with
     * @param name the name to set for the filled items
     * @deprecated Use the method with components instead of string
     */
    @Deprecated
    void fill(Material material, String name);


    /**
     * Fills the entire inventory with the specified material and name.
     * @param material the material to fill the inventory with
     * @param name the name to set for the filled items
     */
    void fill(Material material, Component name);

    /**
     * Fills the entire inventory with the specified SmartItem.
     * @param item the SmartItem to fill the inventory with
     */
    void fill(SmartItem item);

    /**
     * Fills the borders of the inventory with the specified material.
     * @param material the material to fill the borders with
     */
    void fillBorders(Material material);

    /**
     * Fills the borders of the inventory with the specified material and name.
     * @param material the material to fill the borders with
     * @param name the name to set for the filled border items
     * @deprecated Use the method with components instead of string
     */
    @Deprecated
    void fillBorders(Material material, String name);

    /**
     * Fills the borders of the inventory with the specified material and name.
     * @param material the material to fill the borders with
     * @param name the name to set for the filled border items
     */
    void fillBorders(Material material, Component name);

    /**
     * Fills the borders of the inventory with the specified SmartItem.
     * @param item the SmartItem to fill the borders with
     */
    void fillBorders(SmartItem item);

    /**
     * Gets the PageSystem associated with the inventory contents for managing page navigation.
     * @return the PageSystem
     */
    PageSystem getPageSystem();

    /**
     * Gets the InventoryBuilder associated with the inventory contents.
     * @return the InventoryBuilder
     */
    InventoryBuilder getBuilder();

    /**
     * Updates the contents of the inventory with the current SmartItem data.
     * Call this method after making any changes to the SmartItems in the inventory to apply the changes.
     */
    void update();

    /**
     * Changes the title
     * @param newTitle the title
     * @return the inv
     * @deprecated use components instead of strings
     */
    @Deprecated
    Inventory changeTitle(String newTitle);

    /**
     * Changes the title
     * @param newTitle the title
     * @return the inv
     */
    @Deprecated
    Inventory changeTitle(Component newTitle);
}

package de.derioo.inventoryframework.interfaces;

import de.derioo.inventoryframework.objects.SmartItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface InventoryContents {


    SmartItem[] getItems();


    ItemStack[] getRawItems();


    void setItems(SmartItem[] items);

    @Nullable SmartItem getItem(int index);



    void set(int index, SmartItem item);

    void set(int index, ItemStack item);

    void fill(Material material);

    void fill(Material material, String name);

    void fillBorders(Material material);

    void fillBorders(Material material, String name);

    void fill(SmartItem item);


    PageSystem getPageSystem();

    InventoryBuilder getBuilder();

    void update();

}

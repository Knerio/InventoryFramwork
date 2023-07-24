package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryBuilder;
import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.PageSystem;
import de.derioo.inventoryframework.objects.SmartItem;
import de.derioo.inventoryframework.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class InventoryContentsImpl implements InventoryContents {


    private SmartItem[] contents;
    private PageSystem pageSystem;
    private final InventoryBuilder builder;

    public InventoryContentsImpl(int size, InventoryBuilder builder, int maxPages) {
        contents = new SmartItem[size];
        this.builder = builder;
        pageSystem = new PageSystemImpl(maxPages, this);
    }

    public InventoryContentsImpl(int size, InventoryBuilder builder) {
        contents = new SmartItem[size];
        this.builder = builder;
    }


    @Override
    public SmartItem[] getItems() {
        return contents;
    }

    @Override
    public ItemStack[] getRawItems() {
        return (ItemStack[]) Arrays.stream(contents).map(SmartItem::getItem).toArray();
    }

    @Override
    public void setItems(SmartItem[] items) {
        contents = items;
    }

    @Nullable
    @Override
    public SmartItem getItem(int index) {
        return contents[index];
    }

    @Override
    public void set(int index, SmartItem item) {
        contents[index] = item;
    }

    @Override
    public void set(int index, ItemStack item) {
        this.set(index, SmartItem.get(item));
    }


    @Override
    public void fill(Material material) {
        fill(material, " ");
    }

    @Override
    public void fill(Material material, String name) {
        fill(SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
    }

    @Override
    public void fillBorders(Material material) {
        fillBorders(material, " ");
    }

    @Override
    public void fillBorders(Material material, String name) {
        for (int i = 0; i < 10; i++) {
            this.set(i, SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
        }
        int size = getItems().length;
        if (size > 9){
            for (int i = size-9; i < size; i++) {
                this.set(i, SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
            }
        }
        int rows = (size + 1) / 9;

        for(int i = 9; i < rows * 9 - 1; i += 9) {
            if (i == 0)continue;
            this.set(i, SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
            this.set(i+8, SmartItem.get(new ItemBuilder(material).setName(name).toItemStack()));
        }
    }

    @Override
    public void fill(SmartItem item) {
        for (int i = 0; i < contents.length; i++) {
            contents[i] = item.clone();
        }
    }

    @Override
    public PageSystem getPageSystem() {
        if (pageSystem == null) throw new IllegalStateException("PageSystem is null because this.setup want correctly");
        return pageSystem;
    }

    @Override
    public InventoryBuilder getBuilder() {
        return builder;
    }

    @Override
    public void update() {
        if (pageSystem == null) pageSystem = new PageSystemImpl(1, builder.getContents());
        getBuilder().getProvider().init(getBuilder().getPlayer(), getBuilder().getContents());
    }


}

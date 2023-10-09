package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.PageSystem;

/**
 * An implementation class of a page system
 */
public class PageSystemImpl implements PageSystem {

    private int page;
    private final int maxPage;
    private final InventoryContents contents;

    /**
     * Used to create an instance
     * @param maxPage the maxPages
     * @param contents the contents
     */
    public PageSystemImpl(int maxPage, InventoryContents contents){
        this.page = 1;
        this.maxPage = maxPage;
        this.contents = contents;
    }

    @Override
    public int currentPage() {
        return this.page;
    }

    @Override
    public void nextPage() {
        if (this.page == this.maxPage)throw new IllegalStateException("Cannot go above MaxPage");
        this.page++;
        onPageChange();
    }

    @Override
    public void previousPage() {
        if (this.page == 1){
            throw new IllegalStateException("Cannot go to 0 Page");
        }
        this.page--;
        onPageChange();
    }

    @Override
    public void set(int page) {
        if (page <= 0 || page > this.maxPage)throw new IllegalStateException("Cannot go above or below max / min page");
        this.page = page;
        onPageChange();
    }

    @Override
    public boolean isFirst() {
        return this.currentPage() == 1;
    }

    @Override
    public boolean isLast() {
        return this.currentPage() == maxPage;
    }

    /**
     * Updates the contents if a page changes
     */
    public void onPageChange(){
        this.contents.update();
        this.contents.getBuilder().reload();
    }
}

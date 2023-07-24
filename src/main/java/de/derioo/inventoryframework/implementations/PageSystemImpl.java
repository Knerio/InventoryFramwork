package de.derioo.inventoryframework.implementations;

import de.derioo.inventoryframework.interfaces.InventoryContents;
import de.derioo.inventoryframework.interfaces.PageSystem;

public class PageSystemImpl implements PageSystem {

    private int page;
    private final int maxPage;
    private final InventoryContents contents;

    public PageSystemImpl(int maxPage, InventoryContents contents){
        page = 1;
        this.maxPage = maxPage;
        this.contents = contents;
    }

    @Override
    public int currentPage() {
        return page;
    }

    @Override
    public void nextPage() {
        if (page == maxPage)throw new IllegalStateException("Cannot go above MaxPage");
        page++;
        onPageChange();
    }

    @Override
    public void previousPage() {
        if (page == 1){
            throw new IllegalStateException("Cannot go to 0 Page");
        }
        page--;
        onPageChange();
    }

    @Override
    public void set(int page) {
        if (page <= 0 || page > maxPage)throw new IllegalStateException("Cannot go above or below max / min page");
        this.page = page;
        onPageChange();
    }

    @Override
    public boolean isFirst() {
        return currentPage() == 1;
    }

    @Override
    public boolean isLast() {
        return currentPage() == maxPage;
    }


    public void onPageChange(){
        contents.update();
    }
}

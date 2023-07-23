package de.derioo.inventoryframework.interfaces;

public interface PageSystem {

    /**
     * Gets
     * @return
     */
    int currentPage();

    void nextPage();

    void previousPage();

    void set(int page);

    boolean isFirst();

    boolean isLast();


    void setMaxPages(int pages);
}

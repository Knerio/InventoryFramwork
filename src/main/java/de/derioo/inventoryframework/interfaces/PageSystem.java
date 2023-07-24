package de.derioo.inventoryframework.interfaces;

public interface PageSystem {

    int currentPage();

    void nextPage();

    void previousPage();

    void set(int page);

    boolean isFirst();

    boolean isLast();

}

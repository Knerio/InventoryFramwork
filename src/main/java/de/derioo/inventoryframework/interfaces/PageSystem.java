package de.derioo.inventoryframework.interfaces;

import org.checkerframework.checker.index.qual.NonNegative;

public interface PageSystem {

    /**
     * Gets the current page
     * Starting at 1
     * @return the page
     */
    int currentPage();

    /**
     * Goes to the nextPage
     */
    void nextPage();

    /**
     * Goes to the nextPage
     */
    void previousPage();

    /**
     * Sets the current page
     * @see PageSystem#nextPage()
     * @see PageSystem#previousPage()
     * @param page the page starting at 1
     */
    void set(@NonNegative int page);

    /**
     * Checks if user is on the first page
     * @return if the user is on the first page
     */
    boolean isFirst();

    /**
     * Checks if user is on the last page
     * @return if the user is on the last page
     */
    boolean isLast();

}

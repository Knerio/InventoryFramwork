package de.derioo.inventoryframework.exeptions;

/**
 * Is thrown when the frameWork isn't configured
 * use new InventoryFramework in onEnable
 */
public class FrameworkNotConfiguredException extends RuntimeException {

    /**
     * Used to create an exception with string
     * @param s the name
     */
    public FrameworkNotConfiguredException(String s) {
        super(s);
    }

}

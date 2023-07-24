package de.derioo.inventoryframework.exeptions;

/**
 * Is thrown when the frameWork isn't configured
 * use new InventoryFramework in onEnable
 */
public class FrameworkNotConfiguredException extends RuntimeException {

    public FrameworkNotConfiguredException(String s) {
        super(s);
    }

}

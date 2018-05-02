package com.impaq.pointofsale.devices.input;

import java.util.EventObject;

public class ScanEvent extends EventObject {
    private String barcode;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ScanEvent(Object source, String barcode) {
        super(source);
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }
}

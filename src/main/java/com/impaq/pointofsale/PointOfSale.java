package com.impaq.pointofsale;

import com.impaq.pointofsale.db.Database;
import com.impaq.pointofsale.db.ProductNotFoundException;
import com.impaq.pointofsale.devices.input.BarcodeScanner;
import com.impaq.pointofsale.devices.input.ScanEvent;
import com.impaq.pointofsale.devices.input.ScanListener;
import com.impaq.pointofsale.devices.output.LcdDisplay;
import com.impaq.pointofsale.devices.output.Printer;
import com.impaq.pointofsale.model.Product;
import com.impaq.pointofsale.model.Receipt;

import java.util.ResourceBundle;

public class PointOfSale implements ScanListener {
    private final String ERROR_MESSAGE_PRODUCT_NOT_FOUND;
    private final String ERROR_MESSAGE_INVALID_BARCODE;
    private final String EXIT;
    private BarcodeScanner scanner;
    private LcdDisplay display;
    private Printer printer;
    private Database db;
    private Receipt receipt;


    public PointOfSale(BarcodeScanner scanner, LcdDisplay display, Printer printer, Database db) {
        this.scanner = scanner;
        this.display = display;
        this.printer = printer;
        this.db = db;
        receipt = new Receipt();
        scanner.addScanListener(this);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Messages");
        ERROR_MESSAGE_INVALID_BARCODE = resourceBundle.getString("error.InvalidBarcode");
        ERROR_MESSAGE_PRODUCT_NOT_FOUND = resourceBundle.getString("error.ProductNotFound");
        EXIT = resourceBundle.getString("input.exit");
    }


    @Override
    public void onScan(ScanEvent event) {
        String barcode = event.getBarcode();
        if (isInvalidBarcode(barcode)) onInvalidBarcode();
        else if (isExitInput(barcode)) onExit();
        else {
            try {
                Product product = db.findProduct(barcode);
                onExistProduct(product);
            } catch (ProductNotFoundException e) {
                onProductNotFound();
            }
        }
    }

    private boolean isExitInput(String barcode) {
        return barcode.equals(EXIT);
    }

    private boolean isInvalidBarcode(String barcode) {
        return barcode == null || barcode.isEmpty();
    }

    private void onInvalidBarcode() {
        display.print(ERROR_MESSAGE_INVALID_BARCODE);
    }

    private void onProductNotFound() {
        display.print(ERROR_MESSAGE_PRODUCT_NOT_FOUND);
    }

    private void onExistProduct(Product product) {
        display.print(product.toString());
        receipt.addProduct(product);
    }

    private void onExit() {
        display.print(receipt.getTotalPrice().toString());
        printer.print(receipt.toString());
    }
}

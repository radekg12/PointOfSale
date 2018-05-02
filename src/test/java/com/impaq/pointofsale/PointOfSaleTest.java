package com.impaq.pointofsale;

import com.impaq.pointofsale.db.Database;
import com.impaq.pointofsale.db.ProductNotFoundException;
import com.impaq.pointofsale.devices.input.BarcodeScanner;
import com.impaq.pointofsale.devices.input.ScanEvent;
import com.impaq.pointofsale.devices.output.LcdDisplay;
import com.impaq.pointofsale.devices.output.Printer;
import com.impaq.pointofsale.model.Product;
import com.impaq.pointofsale.model.Receipt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ResourceBundle;

@RunWith(MockitoJUnitRunner.class)
public class PointOfSaleTest {
    @Mock
    private BarcodeScanner scanner;
    @Mock
    private Printer printer;
    @Mock
    private LcdDisplay display;
    @Mock
    private Database db;
    private PointOfSale pos;
    private String errorMessageProductNotFound;
    private String errorMessageInvalidBarcode;
    private Product product1;
    private Product product2;
    private ScanEvent eventWithCorrectBarcode1;
    private ScanEvent eventWithCorrectBarcode2;
    private ScanEvent eventWithNotFoundBarcode;
    private ScanEvent eventWithEmptyBarcode;
    private ScanEvent eventExitInput;


    @Before
    public void setUp() throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Messages");
        errorMessageInvalidBarcode = resourceBundle.getString("error.InvalidBarcode");
        errorMessageProductNotFound = resourceBundle.getString("error.ProductNotFound");
        String exit = resourceBundle.getString("input.exit");

        pos = new PointOfSale(scanner, display, printer, db);
        String correctBarcode1 = "11111111111";
        String correctBarcode2 = "22222222222";
        String notFoundBarcode = "3333333333";
        String emptyBarcode = "";
        product1 = new Product("Book", 25.55);
        product2 = new Product("Pencil", 3.20);
        eventWithCorrectBarcode1 = new ScanEvent(scanner, correctBarcode1);
        eventWithCorrectBarcode2 = new ScanEvent(scanner, correctBarcode2);
        eventWithNotFoundBarcode = new ScanEvent(scanner, notFoundBarcode);
        eventWithEmptyBarcode = new ScanEvent(scanner, emptyBarcode);
        eventExitInput = new ScanEvent(scanner, exit);

        Mockito.when(db.findProduct(correctBarcode1)).thenReturn(product1);
        Mockito.when(db.findProduct(correctBarcode2)).thenReturn(product2);
        ProductNotFoundException exception = new ProductNotFoundException();
        Mockito.when(db.findProduct(notFoundBarcode)).thenThrow(exception);
    }

    @Test
    public void shouldPrintNameAndPriceOfProductOnLcdDisplayIfTheProductIsFoundInDatabase() {
        //when
        pos.onScan(eventWithCorrectBarcode1);
        //then
        Mockito.verify(display, Mockito.times(1)).print(product1.toString());
    }

    @Test
    public void shouldPrintErrorMessageOnLcdDisplayIfTheProductIsNotFoundInDatabase() {
        //when
        pos.onScan(eventWithNotFoundBarcode);
        //then
        Mockito.verify(display, Mockito.times(1)).print(errorMessageProductNotFound);
    }

    @Test
    public void shouldPrintErrorMessageOnLcdDisplayIfTheCodeScannedIsEmpty() {
        //when
        pos.onScan(eventWithEmptyBarcode);
        //then
        Mockito.verify(display, Mockito.times(1)).print(errorMessageInvalidBarcode);
    }

    @Test
    public void shouldPrintListOfAllScannedItemsAndTotalSumOnPrinterIfExitIsInput() {
        //when
        pos.onScan(eventWithCorrectBarcode1);
        pos.onScan(eventWithCorrectBarcode2);
        pos.onScan(eventExitInput);
        //then
        Mockito.verify(printer, Mockito.times(1)).print(
                product1.toString() + "\n" + product2.toString() + Receipt.SEPARATOR
                        + product1.getPrice().add(product2.getPrice()));
    }

    @Test
    public void shouldPrintTotalSumForAllItemsOnLcdDisplayIfExitIsInput() {
        //when
        pos.onScan(eventWithCorrectBarcode1);
        pos.onScan(eventWithCorrectBarcode2);
        pos.onScan(eventExitInput);
        //then
        Mockito.verify(display, Mockito.times(1)).print(product1.toString());
        Mockito.verify(display, Mockito.times(1)).print(product2.toString());
        Mockito.verify(display, Mockito.times(1)).print(
                product1.getPrice().add(product2.getPrice()).toString());
    }

}
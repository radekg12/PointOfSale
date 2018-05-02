package com.impaq.pointofsale.devices.input;


public interface BarcodeScanner {

    String scan();

    void addScanListener(ScanListener listener);

    void removeScanListener(ScanListener listener);

    void fireScan();
}
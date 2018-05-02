package com.impaq.pointofsale.devices.input;

import java.util.EventListener;

public interface ScanListener extends EventListener {
    void onScan(ScanEvent event);
}

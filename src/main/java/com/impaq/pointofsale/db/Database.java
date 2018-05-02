package com.impaq.pointofsale.db;

import com.impaq.pointofsale.model.Product;

public interface Database {

    Product findProduct(String barcode) throws ProductNotFoundException;
}

package com.github.livreprogramacao.example.app;

public class WarehouseArray {

    // Operation: receive items for sku (add quantity)
    public void receive(int[] stock, int sku, int qty) {
        checkSku(stock, sku);
        if (qty < 0) throw new IllegalArgumentException("qty must be >= 0");
        stock[sku] += qty;
    }

    // Operation: ship items for sku (subtract quantity if available)
    public boolean ship(int[] stock, int sku, int qty) {
        checkSku(stock, sku);
        if (qty < 0) throw new IllegalArgumentException("qty must be >= 0");
        if (stock[sku] < qty) return false; // insufficient stock
        stock[sku] -= qty;
        return true;
    }

    // Operation: transfer quantity from srcSku to dstSku
    public boolean transfer(int[] stock, int srcSku, int dstSku, int qty) {
        checkSku(stock, srcSku);
        checkSku(stock, dstSku);
        if (qty < 0) throw new IllegalArgumentException("qty must be >= 0");
        if (stock[srcSku] < qty) return false;
        stock[srcSku] -= qty;
        stock[dstSku] += qty;
        return true;
    }

    // Query: check availability
    public boolean isAvailable(int[] stock, int sku, int needed) {
        checkSku(stock, sku);
        return stock[sku] >= needed;
    }

    private void checkSku(int[] stock, int sku) {
        if (sku < 0 || sku >= stock.length) throw new IndexOutOfBoundsException("Invalid SKU: " + sku);
    }

}

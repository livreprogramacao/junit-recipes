package com.github.livreprogramacao.example.App;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseArrayTest {

    //
    private WarehouseArray instance;

    // stockLevel[i] = quantity of SKU i in warehouse
    private int[] stockLevel;

    @BeforeEach
    void setUp() {
        // initialize the object under test.
        instance = new WarehouseArray();
        // initialize with 5 SKUs
        stockLevel = new int[]{10, 0, 5, 20, 3};
    }

    @Test
    void testReceiveIncreasesStock() {
        instance.receive(stockLevel, 1, 7);
        assertEquals(7, stockLevel[1]);
        instance.receive(stockLevel, 0, 3);
        assertEquals(13, stockLevel[0]);
    }

    @Test
    void testShipReducesStockWhenAvailable() {
        boolean ok = instance.ship(stockLevel, 0, 5);
        assertTrue(ok);
        assertEquals(5, stockLevel[0]);
    }

    @Test
    void testShipFailsWhenInsufficientStock() {
        boolean ok = instance.ship(stockLevel, 1, 1); // sku 1 initially 0
        assertFalse(ok);
        assertEquals(0, stockLevel[1]);
    }

    @Test
    void testTransferMovesStockBetweenSkus() {
        boolean ok = instance.transfer(stockLevel, 3, 2, 4); // from sku3 (20) to sku2 (5)
        assertTrue(ok);
        assertEquals(16, stockLevel[3]);
        assertEquals(9, stockLevel[2]);
    }

    @Test
    void testTransferFailsWhenNotEnough() {
        boolean ok = instance.transfer(stockLevel, 4, 0, 10); // sku4 has 3
        assertFalse(ok);
        assertEquals(3, stockLevel[4]);
        assertEquals(10, stockLevel[0]);
    }

    @Test
    void testIsAvailable() {
        assertTrue(instance.isAvailable(stockLevel, 3, 20));
        assertFalse(instance.isAvailable(stockLevel, 2, 6));
    }

    @Test
    void testInvalidSkuThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> instance.receive(stockLevel, -1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> instance.ship(stockLevel, 5, 1));
    }

    @Test
    void testNegativeQtyThrows() {
        assertThrows(IllegalArgumentException.class, () -> instance.receive(stockLevel, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> instance.ship(stockLevel, 0, -2));
        assertThrows(IllegalArgumentException.class, () -> instance.transfer(stockLevel, 0, 1, -3));
    }

    @Test
    void testSequenceOfOperations() {
        // receive, ship, transfer sequence
        instance.receive(stockLevel, 1, 10);        // sku1: 0 -> 10
        assertTrue(instance.ship(stockLevel, 1, 4)); // sku1: 10 -> 6
        assertTrue(instance.transfer(stockLevel, 1, 0, 2)); // sku1: 6 -> 4, sku0: 10 -> 12
        assertEquals(12, stockLevel[0]);
        assertEquals(4, stockLevel[1]);
    }
}
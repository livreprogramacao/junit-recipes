package com.github.livreprogramacao.example.App;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseArrayTest {

    //
    private WarehouseArray instance;

    // stockLevel[i] = quantity of SKU i in warehouse
    private int[] stockLevel;

    private void printDetailOperation(String message) {
        // indexed format
        System.out.printf("%s : %s%n", message, java.util.Arrays.toString(stockLevel));
        System.out.println("| SKU | qty |");
        for (int i = 0; i < stockLevel.length; i++) {
            System.out.printf("| %03d | %03d |%n", i, stockLevel[i]);
        }
    }

    @BeforeEach
    void setUp() {
        // initialize the object under test.
        instance = new WarehouseArray();
        // initialize with 5 SKUs
        stockLevel = new int[]{10, 0, 5, 20, 3};
    }

    @Test
    void testReceiveIncreasesStock() {
        printDetailOperation("🔜 before operation: testReceiveIncreasesStock()");
        instance.receive(stockLevel, 1, 7);
        printDetailOperation("🔚 after operation: testReceiveIncreasesStock()");
        assertEquals(7, stockLevel[1]);

        printDetailOperation("🔜 before operation: testReceiveIncreasesStock()");
        instance.receive(stockLevel, 0, 3);
        printDetailOperation("🔚 after operation: testReceiveIncreasesStock()");
        assertEquals(13, stockLevel[0]);
    }

    @Test
    void testShipReducesStockWhenAvailable() {
        printDetailOperation("🔜 before operation: testShipReducesStockWhenAvailable()");
        boolean ok = instance.ship(stockLevel, 0, 5);
        printDetailOperation("🔚 after operation: testShipReducesStockWhenAvailable()");
        assertTrue(ok);
        assertEquals(5, stockLevel[0]);
    }

    @Test
    void testShipFailsWhenInsufficientStock() {
        printDetailOperation("🔜 before operation: testShipFailsWhenInsufficientStock()");
        boolean ok = instance.ship(stockLevel, 1, 1); // sku 1 initially 0
        printDetailOperation("🔚 after operation: testShipFailsWhenInsufficientStock()");
        assertFalse(ok);
        assertEquals(0, stockLevel[1]);
    }

    @Test
    void testTransferMovesStockBetweenSkus() {
        printDetailOperation("🔜 before operation: testTransferMovesStockBetweenSkus()");
        boolean ok = instance.transfer(stockLevel, 3, 2, 4); // from sku3 (20) to sku2 (5)
        printDetailOperation("🔚 after operation: testTransferMovesStockBetweenSkus()");

        assertTrue(ok);
        assertEquals(16, stockLevel[3]);
        assertEquals(9, stockLevel[2]);
    }

    @Test
    void testTransferFailsWhenNotEnough() {
        printDetailOperation("🔜 before operation: testTransferFailsWhenNotEnough()");
        boolean ok = instance.transfer(stockLevel, 4, 0, 10); // sku4 has 3
        printDetailOperation("🔚 after operation: testTransferFailsWhenNotEnough()");
        assertFalse(ok);
        assertEquals(3, stockLevel[4]);
        assertEquals(10, stockLevel[0]);
    }

    @Test
    void testIsAvailable() {
        printDetailOperation("🔜 before operation: testIsAvailable()");
        assertTrue(instance.isAvailable(stockLevel, 3, 20));
        printDetailOperation("🔚 after operation: testIsAvailable()");

        printDetailOperation("🔜 before operation: testIsAvailable()");
        assertFalse(instance.isAvailable(stockLevel, 2, 6));
        printDetailOperation("🔚 after operation: testIsAvailable()");
    }

    @Test
    void testInvalidSkuThrows() {
        printDetailOperation("🔜 before operation: testInvalidSkuThrows()");
        assertThrows(IndexOutOfBoundsException.class, () -> instance.receive(stockLevel, -1, 1));
        printDetailOperation("🔚 after operation: testInvalidSkuThrows()");

        printDetailOperation("🔜 before operation: testInvalidSkuThrows()");
        assertThrows(IndexOutOfBoundsException.class, () -> instance.ship(stockLevel, 5, 1));
        printDetailOperation("🔚 after operation: testInvalidSkuThrows()");
    }

    @Test
    void testNegativeQtyThrows() {
        printDetailOperation("🔜 before operation: testNegativeQtyThrows()");
        assertThrows(IllegalArgumentException.class, () -> instance.receive(stockLevel, 0, -1));
        printDetailOperation("🔚 after operation: testNegativeQtyThrows()");

        printDetailOperation("🔜 before operation: testNegativeQtyThrows()");
        assertThrows(IllegalArgumentException.class, () -> instance.ship(stockLevel, 0, -2));
        printDetailOperation("🔚 after operation: testNegativeQtyThrows()");

        printDetailOperation("🔜 before operation: testNegativeQtyThrows()");
        assertThrows(IllegalArgumentException.class, () -> instance.transfer(stockLevel, 0, 1, -3));
        printDetailOperation("🔚 after operation: testNegativeQtyThrows()");
    }

    @Test
    void testSequenceOfOperations() {
        // receive, ship, transfer sequence
        printDetailOperation("🔜 before operation: testSequenceOfOperations()");
        instance.receive(stockLevel, 1, 10);        // sku1: 0 -> 10
        printDetailOperation("🔚 after operation: testSequenceOfOperations()");

        printDetailOperation("🔜 before operation: testSequenceOfOperations()");
        assertTrue(instance.ship(stockLevel, 1, 4)); // sku1: 10 -> 6
        printDetailOperation("🔚 after operation: testSequenceOfOperations()");

        printDetailOperation("🔜 before operation: testSequenceOfOperations()");
        assertTrue(instance.transfer(stockLevel, 1, 0, 2)); // sku1: 6 -> 4, sku0: 10 -> 12
        printDetailOperation("🔚 after operation: testSequenceOfOperations()");

        assertEquals(12, stockLevel[0]);
        assertEquals(4, stockLevel[1]);
    }
}
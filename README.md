# README

## Java unit test: warehouse stock simulation (arrays)

Below is a concise JUnit 5 test class that uses arrays to simulate warehouse stock levels and operations (receive, ship, transfer, check). It includes setup, several operation methods operating directly on arrays, and tests that exercise normal and edge cases.

```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarehouseArrayTest {

    // stockLevel[i] = quantity of SKU i in warehouse
    private int[] stockLevel;

    @BeforeEach
    void setUp() {
        // initialize with 5 SKUs
        stockLevel = new int[] { 10, 0, 5, 20, 3 };
    }

    // Operation: receive items for sku (add quantity)
    void receive(int[] stock, int sku, int qty) {
        checkSku(stock, sku);
        if (qty < 0) throw new IllegalArgumentException("qty must be >= 0");
        stock[sku] += qty;
    }

    // Operation: ship items for sku (subtract quantity if available)
    boolean ship(int[] stock, int sku, int qty) {
        checkSku(stock, sku);
        if (qty < 0) throw new IllegalArgumentException("qty must be >= 0");
        if (stock[sku] < qty) return false; // insufficient stock
        stock[sku] -= qty;
        return true;
    }

    // Operation: transfer quantity from srcSku to dstSku
    boolean transfer(int[] stock, int srcSku, int dstSku, int qty) {
        checkSku(stock, srcSku);
        checkSku(stock, dstSku);
        if (qty < 0) throw new IllegalArgumentException("qty must be >= 0");
        if (stock[srcSku] < qty) return false;
        stock[srcSku] -= qty;
        stock[dstSku] += qty;
        return true;
    }

    // Query: check availability
    boolean isAvailable(int[] stock, int sku, int needed) {
        checkSku(stock, sku);
        return stock[sku] >= needed;
    }

    private void checkSku(int[] stock, int sku) {
        if (sku < 0 || sku >= stock.length) throw new IndexOutOfBoundsException("Invalid SKU: " + sku);
    }

    @Test
    void testReceiveIncreasesStock() {
        receive(stockLevel, 1, 7);
        assertEquals(7, stockLevel[1]);
        receive(stockLevel, 0, 3);
        assertEquals(13, stockLevel[0]);
    }

    @Test
    void testShipReducesStockWhenAvailable() {
        boolean ok = ship(stockLevel, 0, 5);
        assertTrue(ok);
        assertEquals(5, stockLevel[0]);
    }

    @Test
    void testShipFailsWhenInsufficientStock() {
        boolean ok = ship(stockLevel, 1, 1); // sku 1 initially 0
        assertFalse(ok);
        assertEquals(0, stockLevel[1]);
    }

    @Test
    void testTransferMovesStockBetweenSkus() {
        boolean ok = transfer(stockLevel, 3, 2, 4); // from sku3 (20) to sku2 (5)
        assertTrue(ok);
        assertEquals(16, stockLevel[3]);
        assertEquals(9, stockLevel[2]);
    }

    @Test
    void testTransferFailsWhenNotEnough() {
        boolean ok = transfer(stockLevel, 4, 0, 10); // sku4 has 3
        assertFalse(ok);
        assertEquals(3, stockLevel[4]);
        assertEquals(10, stockLevel[0]);
    }

    @Test
    void testIsAvailable() {
        assertTrue(isAvailable(stockLevel, 3, 20));
        assertFalse(isAvailable(stockLevel, 2, 6));
    }

    @Test
    void testInvalidSkuThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> receive(stockLevel, -1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> ship(stockLevel, 5, 1));
    }

    @Test
    void testNegativeQtyThrows() {
        assertThrows(IllegalArgumentException.class, () -> receive(stockLevel, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> ship(stockLevel, 0, -2));
        assertThrows(IllegalArgumentException.class, () -> transfer(stockLevel, 0, 1, -3));
    }

    @Test
    void testSequenceOfOperations() {
        // receive, ship, transfer sequence
        receive(stockLevel, 1, 10);        // sku1: 0 -> 10
        assertTrue(ship(stockLevel, 1, 4)); // sku1: 10 -> 6
        assertTrue(transfer(stockLevel, 1, 0, 2)); // sku1: 6 -> 4, sku0: 10 -> 12
        assertEquals(12, stockLevel[0]);
        assertEquals(4, stockLevel[1]);
    }
}
```

Notes:
- Replace initial stock array with real data or dynamic sizing as needed.
- This example uses plain arrays (int[]) to keep it simple; for more features consider objects or maps per SKU.


List some operations for a warehouse that sells eletronics itens
--------------------

- Receiving shipments (inbound inspection, unpacking, put-away)
- Stock counting (cycle counts, full inventory audits)
- Picking (single-order picking, batch picking, wave picking)
- Packing (packaging, adding protective materials, labeling)
- Shipping (carrier selection, manifesting, dispatch)
- Returns processing (RMA inspection, refurbish, restock, dispose)
- Transfers between locations (store transfers, inter-warehouse moves)
- Replenishment (move stock from bulk to pick locations)
- Quality control (functional testing, cosmetic inspection)
- Reserved stock management (allocations for orders, backorders)
- Kitting and bundling (assemble product kits or bundles)
- Warranty handling (registering claims, replacing/repairing units)
- Disposal and recycling (e-waste handling, secure data erasure)
- Serial/IMEI tracking (recording device identifiers for traceability)
- Lot and batch tracking (firmware versions, recall readiness)
- Price/label updates (barcode/price sticker changes)
- Hazardous materials handling (battery packing, shipping regs)
- Returns-to-vendor (RMA returns to suppliers)
- Customer pickup/holds (order staging, customer notification)

## Warehouse simulation processes (for a Java implementation)

### Core inventory processes
- **Receive shipment:** create inbound shipments, validate items, update stock levels, assign SKUs.
- **Put-away:** choose storage locations, move items from receiving to storage bins.
- **Replenishment:** move stock from bulk/backroom to pick locations when thresholds reached.
- **Picking:** select pick strategy (single, batch, wave), generate pick lists, reserve picked quantities.
- **Packing:** group picked items into packages, apply packaging rules, calculate weights/dimensions.
- **Ship/Dispatch:** finalize orders, decrement available stock, generate shipment records and tracking.
- **Returns processing:** accept returns, inspect, decide restock/refurbish/dispose, update inventory.
- **Transfer between locations:** create inter-warehouse or zone transfers and update both inventories.
- **Cycle counting / inventory audit:** schedule counts, reconcile differences, adjust stock.

### Supporting processes & controls
- **Reservation/Allocation:** reserve stock for orders, handle backorders and holds.
- **SKU and location management:** define SKUs, units, location capacity and adjacency.
- **Lot/serial tracking:** record serial numbers, IMEIs, manufacture dates, and lot expiry/recall flags.
- **Quality control/testing:** functional test steps for electronics and quarantine workflows.
- **Returns to vendor / RMA handling:** create RMAs, track vendor returns, crediting.
- **Disposal & e-waste handling:** quarantine and process end-of-life or damaged electronics.

### Operational constraints & events
- **Capacity constraints:** bin/aisle/warehouse volume and weight limits.
- **Business rules:** min/max stock levels, reorder points, lead times, safety stock.
- **Time-based processes:** order cutoffs, carrier schedules, processing delays, work shifts.
- **Failure and exceptions:** damaged items, missing items, incorrect shipments, pick errors.

### Metrics & reporting (for simulation output)
- Throughput (orders/day), pick accuracy, fill rate, order cycle time, inventory turns, on-time shipments, return rate, average dwell time.

### Simulation design elements (implementation guidance)
- **Entities:** Warehouse, Location/Bin, SKU/Item, Order, Shipment, Employee/Worker, Vehicle/Carrier.
- **State:** stock levels per SKU per location, reserved quantities, item conditions.
- **Events:** arrival, pick request, transfer, count, return, damage report.
- **Schedulers:** event queue or discrete-event simulation loop, time step progression.
- **Decision logic:** pick strategies, replenishment triggers, assignment of workers.
- **Randomness & distributions:** lead times, demand rates, damage probability, worker speeds.
- **Persistence/logging:** in-memory model or simple file/DB logging for results.
- **Tests & scenarios:** baseline, peak load, failure injection, policy comparisons.

Use these processes as a checklist to design classes, methods, events, and test scenarios in your Java simulation. Would you like a starter project skeleton or example classes for this?

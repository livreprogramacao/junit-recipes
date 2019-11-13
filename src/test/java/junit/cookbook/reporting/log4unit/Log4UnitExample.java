package junit.cookbook.reporting.log4unit;

import junit.log4j.LoggedTestCase;
import junit.logswingui.TestRunner;

public class Log4UnitExample extends LoggedTestCase {
    public static void main(String[] args) {
        TestRunner.main(new String[]{"-noloading",
                Log4UnitExample.class.getName()});
    }

    protected void setUp() throws Exception {
        debug("** SETUP ENTERED **");
    }

    public void testConnection() {
        info("> entered " + this);
        boolean connected = false;
        info("Initiating connection to server now");
        // create Connection and set connected
        // to true if successful . . .
        connected = true;
        assertTrue(connected);
    }
}
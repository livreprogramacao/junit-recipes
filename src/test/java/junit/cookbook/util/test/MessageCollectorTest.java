package junit.cookbook.util.test;

import junit.cookbook.util.MessageCollector;
import junit.framework.TestCase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MessageCollectorTest extends TestCase {
    private MessageCollector listWriter;
    private List actualMessages;

    protected void setUp() throws Exception {
        actualMessages = new LinkedList();
        listWriter = new MessageCollector(actualMessages);
    }

    public void testSingleMessage() {
        listWriter.addMessage("One message");

        assertEquals(
                Collections.singletonList("One message"),
                actualMessages);
    }

    public void testPrintOn() {
        listWriter.addMessage("One message");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter =
                new PrintWriter(stringWriter);

        listWriter.printOn(printWriter);

        assertEquals(
                "One message\r\n",
                stringWriter.toString());
    }
}

package junit.cookbook.oddsandends.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileSystemOutputStrategyTest extends TestCase {
    private File expectedOutputFile;

    public FileSystemOutputStrategyTest(int testNumber) {
        setName("Test #" + testNumber);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Writing to File tests");

        for (int i = 0; i < 100; i++) {
            suite.addTest(new FileSystemOutputStrategyTest(i) {
                protected void runTest() throws Throwable {
                    testWriteToFile();
                }
            });
        }

        return suite;
    }

    protected void setUp() throws Exception {
        expectedOutputFile = new File("test/data/output.txt");
    }

    public void testWriteToFile() throws Exception {
        assertFalse(expectedOutputFile.exists());
        write("test/data", "output.txt", "Hello, world.");
        assertTrue(expectedOutputFile.exists());
    }

    private void write(String directory, String fileName, String text)
            throws Exception {

        FileWriter fileWriter = new FileWriter(new File(directory, fileName));
        PrintWriter out = new PrintWriter(fileWriter);
        out.println(text);
        out.flush();
        out.close();
    }

    protected void tearDown() throws Exception {
        expectedOutputFile.delete();
    }
}
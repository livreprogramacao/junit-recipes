package junit.cookbook.coffee.service.test;

import junit.cookbook.coffee.service.FileSystemMailService;
import junit.framework.TestCase;

import java.io.File;
import java.io.FilenameFilter;


public class FileSystemMailServiceTest extends TestCase {
    public void testSendMessage() throws Exception {
        FileSystemMailService fileSystemMailService = new FileSystemMailService();
        fileSystemMailService.sendMessage("a", "b", "c", "d");

        File tmpDirectory =
                new File(System.getProperty("java.io.tmpdir"));

        File[] testMailFiles =
                tmpDirectory.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return (name.startsWith("test-mail"));
                    }
                });

        assertEquals(
                "Too many test files. I don't know which one to look at.",
                1,
                testMailFiles.length);
    }
}

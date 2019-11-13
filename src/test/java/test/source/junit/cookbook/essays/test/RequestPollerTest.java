package junit.cookbook.essays.test;

import junit.cookbook.essays.FileLister;
import junit.cookbook.essays.RequestPoller;
import junit.cookbook.essays.RequestProcessor;
import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import java.io.File;

public class RequestPollerTest extends TestCase {
    private File[] expectedFiles =
            new File[]{
                    new File("c:/unittest/tmp/file1.xml"),
                    new File("c:/unittest/tmp/file2.xml"),
                    new File("c:/unittest/tmp/file3.xml"),
                    new File("c:/unittest/tmp/file4.xml")};

    public void testPoll() throws Exception {
        FileLister fakeFileLister = new FileLister() {
            public File[] listFiles() {
                return expectedFiles;
            }
        };

        RequestProcessor spyRequestProcessor = new RequestProcessor() {
            public void process(File[] files) {
                ArrayAssert.assertEquals(
                        "Unexpected poll values",
                        expectedFiles,
                        files);
            }
        };

        RequestPoller poller =
                new RequestPoller(fakeFileLister, spyRequestProcessor);

        poller.poll();
    }
}

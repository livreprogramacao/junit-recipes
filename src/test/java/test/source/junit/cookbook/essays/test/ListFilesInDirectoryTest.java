package junit.cookbook.essays.test;

import junit.cookbook.essays.FileLister;
import junit.framework.TestCase;
import junitx.framework.ArrayAssert;

import java.io.File;

public class ListFilesInDirectoryTest extends TestCase {
    private File[] expectedFiles =
            new File[]{
                    new File("c:/unittest/tmp/file1.xml"),
                    new File("c:/unittest/tmp/file2.xml"),
                    new File("c:/unittest/tmp/file3.xml"),
                    new File("c:/unittest/tmp/file4.xml")};

    public void setUp() throws Exception {
        (new File("c:/unittest/tmp")).mkdirs();
        for (int i = 0; i < expectedFiles.length; i++) {
            File newFile = expectedFiles[i];
            newFile.createNewFile();
        }
    }

    public void tearDown() throws Exception {
        for (int i = 0; i < expectedFiles.length; i++) {
            File newFile = expectedFiles[i];
            newFile.delete();
        }
        (new File("c:/unittest/tmp")).delete();
        (new File("c:/unittest")).delete();
    }

    public void testFilesInCurrentDirectory() throws Exception {
        FileLister fileLister =
                new DirectoryFileLister(new File("c:/unittest/tmp"));
        ArrayAssert.assertEquals(expectedFiles, fileLister.listFiles());
    }
}

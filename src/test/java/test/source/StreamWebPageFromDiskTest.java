import com.diasparsoftware.gsbase.StreamUtil;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;

public class StreamWebPageFromDiskTest extends TestCase {
    private File webContentDirectory = new File("test/data");

    public void testLoginPageAsString() throws Exception {
        File loginPageFile =
                new File(webContentDirectory, "login.html");

        String content =
                StreamUtil.getContentAsString(
                        new FileInputStream(loginPageFile));

        fail(content);
    }
}

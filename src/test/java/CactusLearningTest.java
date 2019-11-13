import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.cactus.ServletTestSuite;

public class CactusLearningTest extends TestCase {
    private String hello;

    public static Test suite() {
        return new ServletTestSuite(CactusLearningTest.class);
    }

    protected void setUp() throws Exception {
        hello = "asdkfjh";
    }

    public void testFixtureSet() {
        assertNotNull("Fixture not set!", hello);
    }
}

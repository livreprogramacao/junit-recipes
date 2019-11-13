package junit.cookbook.oddsandends.test;

import junit.cookbook.oddsandends.Deployer;
import junit.cookbook.oddsandends.Deployment;
import junit.cookbook.oddsandends.FileNotFoundDeployer;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;

public class DeploymentTest extends TestCase {
    public void testTargetFileNotFound() throws Exception {
        Deployer fileNotFoundDeployer = new FileNotFoundDeployer();
        Deployment deployment = new Deployment(fileNotFoundDeployer);

        try {
            deployment.deploy(new File("hello"));
            fail("Found target file?!");
        } catch (FileNotFoundException expected) {
            assertEquals("hello", expected.getMessage());
        }
    }
}

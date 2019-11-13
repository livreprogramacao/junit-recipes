package junit.cookbook.oddsandends;

import java.io.File;
import java.io.FileNotFoundException;

public class Deployer {
    private static Deployer deployer = new Deployer();

    public static Deployer getInstance() {
        return deployer;
    }

    public void deploy(junit.cookbook.oddsandends.Deployment deployment, File targetFile)
            throws FileNotFoundException {
        // Does some scary deployment
    }
}

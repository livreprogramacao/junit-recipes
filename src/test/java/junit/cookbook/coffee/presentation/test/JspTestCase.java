package junit.cookbook.coffee.presentation.test;

import junit.cookbook.coffee.test.CoffeeShopFixture;

import java.io.File;
import java.io.FilenameFilter;

public abstract class JspTestCase extends CoffeeShopFixture {
    protected String getJspTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }

    protected void tearDown() throws Exception {
        File[] files = new File(getJspTempDirectory())
                .listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".java") || name
                                .endsWith(".class");
                    }
                });

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            file.delete();
        }
    }
}
package junit.cookbook.coffee.velocity.test;

import com.diasparsoftware.java.util.Money;
import com.diasparsoftware.util.junit.GoldMasterFile;
import junit.cookbook.coffee.display.*;
import junit.framework.TestCase;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.File;
import java.io.StringWriter;
import java.util.Properties;

public class RenderShopcartDisplayTemplateTest extends TestCase {
    private File contentDirectory =
            new File("../CoffeeShopWeb/Web Content/WEB-INF/template/velocity");
    private ShopcartBean shopcartBean;

    protected void setUp() throws Exception {
        contentDirectory.mkdirs();

        Properties properties = new Properties();
        properties.put(
                RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
                contentDirectory.getAbsolutePath());

        Velocity.init(properties);

        shopcartBean = new ShopcartBean();
    }

    public void testEmptyShopcart() throws Exception {
        File goldMasterFile =
                new File("test/gold/velocity", "emptyShopcart-master.txt");
        checkShopcartPageAgainst(goldMasterFile);
    }

    public void testOneItemInShopcart() throws Exception {
        shopcartBean.shopcartItems.add(
                new ShopcartItemBean("Sumatra", "762", 5, Money.dollars(7, 50)));

        checkShopcartPageAgainst(
                new File("test/gold/velocity", "oneItemInShopcart-master.txt"));
    }

    public void checkShopcartPageAgainst(File goldMasterFile)
            throws Exception {

        String responseText = getActualShopcartPageContent();
        new GoldMasterFile(goldMasterFile).check(responseText);
    }

    public String getActualShopcartPageContent() throws Exception {

        Context templateAttributes = new VelocityContext();
        templateAttributes.put("shopcartDisplay", shopcartBean);

        StringWriter webPageWriter = new StringWriter();

        Velocity.mergeTemplate(
                "shopcart.vm",
                "UTF-8",
                templateAttributes,
                webPageWriter);

        String responseText = webPageWriter.toString();
        return responseText;
    }

    public void generateGoldMaster(File goldMasterFile) throws Exception {

        String responseText = getActualShopcartPageContent();
        new GoldMasterFile(goldMasterFile).write(responseText);
        fail("Writing Gold Master file.");
    }
}

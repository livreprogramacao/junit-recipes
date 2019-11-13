package junit.cookbook.coffee.deployment.test;

import junit.cookbook.coffee.EnsureShopperHasShopcartFilter;
import org.custommonkey.xmlunit.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileReader;

public class FiltersTest extends XMLTestCase {
    public void testEnsureShopperHasShopcartFilterConfigured()
            throws Exception {

        String webDeploymentDescriptorFilename =
                "../CoffeeShopWeb/Web Content/WEB-INF/web.xml";

        Document webDeploymentDescriptorDocument =
                XMLUnit.buildTestDocument(
                        new InputSource(
                                new FileReader(
                                        new File(webDeploymentDescriptorFilename))));

        String filterNameMatch =
                "[filter-name='EnsureShopperHasShopcartFilter']";

        assertXpathExists(
                "/web-app/filter" + filterNameMatch,
                webDeploymentDescriptorDocument);

        assertXpathEvaluatesTo(
                EnsureShopperHasShopcartFilter.class.getName(),
                "/web-app/filter" + filterNameMatch + "/filter-class",
                webDeploymentDescriptorDocument);

        assertXpathExists(
                "/web-app/filter-mapping" + filterNameMatch,
                webDeploymentDescriptorDocument);

        assertXpathEvaluatesTo(
                "/coffee",
                "/web-app/filter-mapping"
                        + filterNameMatch
                        + "/url-pattern",
                webDeploymentDescriptorDocument);
    }
}

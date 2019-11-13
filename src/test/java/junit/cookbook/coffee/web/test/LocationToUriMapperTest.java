package junit.cookbook.coffee.web.test;

import junit.cookbook.coffee.LocationToUriMapper;
import junit.cookbook.coffee.NoSuchMappingException;
import junit.framework.TestCase;

public class LocationToUriMapperTest extends TestCase {
    private LocationToUriMapper locationMapper;

    protected void setUp() throws Exception {
        locationMapper = new LocationToUriMapper();
    }

    public void testCatalogPage() throws Exception {
        assertLocationMapsTo("/catalog.jsp", "Catalog");
    }

    public void testWelcomePage() throws Exception {
        assertLocationMapsTo("/index.html", "Welcome");
    }

    public void testShopcartPage() throws Exception {
        assertLocationMapsTo("/shopcart.jsp", "Shopcart");
    }

    public void testUnknownLocation() throws Exception {
        try {
            locationMapper.getUri("Unknown");
            fail("Found a mapping for unknown location?!");
        } catch (NoSuchMappingException expected) {
            assertEquals("Unknown", expected.getLocationName());
        }
    }

    public void assertLocationMapsTo(
            String expectedUri,
            String locationName) {

        assertEquals(expectedUri, locationMapper.getUri(locationName));
    }
}

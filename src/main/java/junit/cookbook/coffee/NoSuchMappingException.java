package junit.cookbook.coffee;

import java.util.NoSuchElementException;

public class NoSuchMappingException extends NoSuchElementException {
    private String locationName;

    public NoSuchMappingException(String locationName) {
        super("No mapping for location " + locationName);
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }
}

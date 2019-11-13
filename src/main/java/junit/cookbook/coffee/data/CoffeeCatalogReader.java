package junit.cookbook.coffee.data;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.model.CoffeeCatalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoffeeCatalogReader {
    private Pattern catalogLinePattern =
            Pattern.compile("(.+),(.+),(.+)");

    public CoffeeCatalog load(Reader catalogDataReader)
            throws IOException {

        CoffeeCatalog catalog = new CoffeeCatalog();

        BufferedReader reader = new BufferedReader(catalogDataReader);

        while (true) {
            String line = reader.readLine();

            if (line == null)
                break;

            Matcher matcher = catalogLinePattern.matcher(line);
            if (matcher.matches()) {
                String productId = matcher.group(1);
                String coffeeName = matcher.group(2);
                String unitPriceAsString = matcher.group(3);
                Money unitPrice = Money.parse(unitPriceAsString);

                catalog.addCoffee(productId, coffeeName, unitPrice);
            }
        }
        return catalog;
    }
}

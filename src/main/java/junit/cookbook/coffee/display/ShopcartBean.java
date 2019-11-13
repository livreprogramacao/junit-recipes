package junit.cookbook.coffee.display;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.model.CoffeeCatalog;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.ShopcartModel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ShopcartBean {
    public SortedSet shopcartItems = new TreeSet();

    public static ShopcartBean create(
            ShopcartModel shopcart,
            CoffeeCatalog catalog) {

        ShopcartBean shopcartBean = new ShopcartBean();

        for (Iterator i = shopcart.items(); i.hasNext(); ) {

            CoffeeQuantity each = (CoffeeQuantity) i.next();
            String eachCoffeeName = each.getCoffeeName();
            int eachQuantityInKilograms = each.getAmountInKilograms();

            ShopcartItemBean item =
                    new ShopcartItemBean(
                            eachCoffeeName,
                            catalog.getProductId(eachCoffeeName),
                            eachQuantityInKilograms,
                            catalog.getUnitPrice(eachCoffeeName));

            shopcartBean.shopcartItems.add(item);
        }

        return shopcartBean;
    }

    public static ShopcartBean create(
            Collection shopcartItems,
            CoffeeCatalog catalog) {

        ShopcartBean shopcartBean = new ShopcartBean();

        for (Iterator i = shopcartItems.iterator(); i.hasNext(); ) {

            CoffeeQuantity each = (CoffeeQuantity) i.next();
            String eachCoffeeName = each.getCoffeeName();
            int eachQuantityInKilograms = each.getAmountInKilograms();

            ShopcartItemBean item =
                    new ShopcartItemBean(
                            eachCoffeeName,
                            catalog.getProductId(eachCoffeeName),
                            eachQuantityInKilograms,
                            catalog.getUnitPrice(eachCoffeeName));

            shopcartBean.shopcartItems.add(item);
        }

        return shopcartBean;
    }

    public Money getSubtotal() {
        Money subtotal = Money.dollars(0);

        // TODO  Extract to DsCollections.sum()
        for (Iterator i = shopcartItems.iterator(); i.hasNext(); ) {
            ShopcartItemBean eachItem = (ShopcartItemBean) i.next();
            subtotal = subtotal.add(eachItem.getTotalPrice());
        }

        return subtotal;
    }

    public SortedSet getShopcartItems() {
        return shopcartItems;
    }

    public String asXml() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        printXmlOn(out);
        return stringWriter.toString();
    }

    public void printXmlOn(PrintWriter out) {
        out.println("<shopcart>");

        for (Iterator i = shopcartItems.iterator(); i.hasNext(); ) {

            ShopcartItemBean eachItem = (ShopcartItemBean) i.next();

            out.println("<item id=\"" + eachItem.productId + "\">");
            printSimpleTag(out, "name", eachItem.coffeeName);
            printSimpleTag(
                    out,
                    "quantity",
                    eachItem.quantityInKilograms);
            printSimpleTag(out, "unit-price", eachItem.unitPrice);
            printSimpleTag(
                    out,
                    "total-price",
                    eachItem.getTotalPrice());
            out.println("</item>");
        }

        printSimpleTag(out, "subtotal", getSubtotal());
        out.println("</shopcart>");
    }

    public void printSimpleTag(
            PrintWriter out,
            String tagName,
            Object tagValue) {

        out.println(
                "<" + tagName + ">" + tagValue + "</" + tagName + ">");
    }

    public void printSimpleTag(
            PrintWriter out,
            String tagName,
            int tagValue) {

        out.println(
                "<" + tagName + ">" + tagValue + "</" + tagName + ">");
    }

}

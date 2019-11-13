package junit.cookbook.coffee.display;

import junit.cookbook.coffee.model.CoffeeCatalog;

import javax.servlet.http.HttpServletRequest;

public class CatalogView {
    private HttpServletRequest request;

    public CatalogView(HttpServletRequest request) {
        this.request = request;
    }

    public void setCatalog(CoffeeCatalog catalog) {
        request.setAttribute(
                "catalog",
                CoffeeCatalogBean.create(catalog));
    }

    public String getUri() {
        return "catalog.jsp";
    }

    public String getLocationName() {
        return "Catalog";
    }
}

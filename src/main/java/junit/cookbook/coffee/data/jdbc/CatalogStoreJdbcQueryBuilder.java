package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.java.util.Money;
import com.diasparsoftware.jdbc.JdbcQueryBuilder;

import java.util.*;

public class CatalogStoreJdbcQueryBuilder extends JdbcQueryBuilder {
    // TODO Push up to superclass
    private Map sqlStrings = new HashMap();

    public CatalogStoreJdbcQueryBuilder() {
        sqlStrings.put(
                "addProduct",
                "insert into catalog.beans (productId, coffeeName, unitPrice) values (?, ?, ?)");

        sqlStrings.put(
                "findAllProducts",
                "select * from catalog.beans");

        sqlStrings.put(
                "findByName",
                "select * from catalog.beans where coffeeName = ?");
    }

    public List createPreparedStatementParameters(
            String statementName,
            List domainParameters) {

        List parameters = new ArrayList();

        if ("addProduct".equals(statementName)) {
            parameters.add(domainParameters.get(0)); // String
            parameters.add(domainParameters.get(1)); // String

            Money amount = (Money) domainParameters.get(2);
            int amountInCents = amount.inCents();
            parameters.add(new Integer(amountInCents));
        } else if ("findByName".equals(statementName)) {
            parameters.add(domainParameters.get(0)); // String
        }

        return parameters;
    }

    public String getSqlString(String statementName) {
        return (String) sqlStrings.get(statementName);
    }

    public Set getSupportedStatements() {
        return sqlStrings.keySet();
    }

}

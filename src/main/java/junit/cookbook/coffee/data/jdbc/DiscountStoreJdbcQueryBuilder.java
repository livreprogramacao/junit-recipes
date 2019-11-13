package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.jdbc.JdbcQueryBuilder;
import com.diasparsoftware.jdbc.JdbcUtil;

import java.util.*;

public class DiscountStoreJdbcQueryBuilder extends JdbcQueryBuilder {

    public List createPreparedStatementParameters(
            String statementName,
            List domainParameters) {

        if ("removeExpiredDiscountAsOf".equals(statementName)) {
            Date expiryDate = (Date) domainParameters.get(0);
            return Collections.singletonList(
                    JdbcUtil.makeTimestamp(expiryDate));
        } else {
            // TODO  Replace with answering null
            throw new NoSuchElementException(
                    "I do not support the statement '"
                            + statementName
                            + "'");
        }
    }

    public String getSqlString(String statementName) {
        if ("removeExpiredDiscountAsOf".equals(statementName)) {
            return "delete from "
                    + "catalog.discount "
                    + "where ("
                    + "catalog.discount.toDate <= ?"
                    + ")";
        } else {
            // TODO  Replace with answering null
            throw new NoSuchElementException(
                    "I do not support the statement '"
                            + statementName
                            + "'");
        }
    }

    public Set getSupportedStatements() {
        return new HashSet(
                Arrays.asList(
                        new Object[]{"removeExpiredDiscountAsOf"}));
    }
}

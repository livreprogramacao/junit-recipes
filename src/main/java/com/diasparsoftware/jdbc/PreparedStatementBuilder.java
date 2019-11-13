package com.diasparsoftware.jdbc;

import com.diasparsoftware.java.sql.PreparedStatementData;

import java.util.List;

public interface PreparedStatementBuilder {
    PreparedStatementData getPreparedStatementData(String statementName,
                                                   List domainParameters);
}
package de.zappler.ultimatebungeesystem.sql;


import de.zappler.ultimatebungeesystem.sql.database.builder.SQLBuilder;
import de.zappler.ultimatebungeesystem.sql.database.connection.DatabaseConnector;
import lombok.Getter;

public class SimpleSQL {

    @Getter
    private final DatabaseConnector connector;

    /**
     * @param connector a database connector
     */

    public SimpleSQL(DatabaseConnector connector) {
        this.connector = connector;
    }

    /**
     * @param sql The de.febanhd.sql statement
     */

    public SQLBuilder createBuilder(String sql) {
        return new SQLBuilder(sql, this);
    }

    /*
     * close the databaseconnector(connections)
     */

    public void closeConnections() {
        this.connector.close();
    }

}
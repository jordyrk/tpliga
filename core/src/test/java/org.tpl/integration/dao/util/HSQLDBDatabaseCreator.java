package org.tpl.integration.dao.util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 * User: Koren
 * Date: 08.jun.2010
 * Time: 22:27:37
 */
public class HSQLDBDatabaseCreator {
    private String databaseName;
    private InputStream sqlCreateScript;
    static int dbCounter = 0;

    public HSQLDBDatabaseCreator(String datebaseName, InputStream sqlCreateScript) {
        this.databaseName = datebaseName;
        this.sqlCreateScript = sqlCreateScript;
    }

    public DataSource createDatabase() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:aksess"+ databaseName + dbCounter++);

        if (sqlCreateScript == null) {
            System.out.println("sqlCreateScript == null!!");
        }

        String lastStatement = "";
        try {
            InputStreamReader in = new InputStreamReader(sqlCreateScript, "iso-8859-1");

            StringWriter sw = new StringWriter();

            char[] buffer = new char[4096];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                sw.write(buffer, 0, n);
            }
            String sql = sw.toString();
            String[] statements = sql.split(";");
            for (String statement : statements) {
                statement = statement.trim();
                if (statement.length() > 0) {
                    lastStatement = statement;
                    dataSource.getConnection().createStatement().execute(statement);
                }
            }

        } catch (IOException e) {
            System.err.println("Failing statement:" + lastStatement);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Failing statement:" + lastStatement);
            throw new RuntimeException(e);
        }

        return dataSource;
    }
}

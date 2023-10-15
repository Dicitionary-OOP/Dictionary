package dictionary.base.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    protected final Connection connection;
    protected final Statement statement;

    /**
     * Constructs a Database object and connects to the specified database file.
     *
     * @param databaseFile The path to the SQLite database file.
     * @throws SQLException If a database access error occurs.
     */
    public Database(final String databaseFile) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
        statement = connection.createStatement();
    }

    /**
     * Executes SQL statements from a file.
     *
     * @param sqlFile The path to the SQL file to execute.
     * @throws SQLException          If a database access error occurs.
     * @throws FileNotFoundException If the specified SQL file is not found.
     * @throws IOException           If an I/O error occurs while reading the SQL
     *                               file.
     */
    public void executeSQLFile(final String sqlFile) throws SQLException, FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
            final StringBuilder query = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().endsWith(";")) {
                    query.append(line);
                    statement.execute(query.toString());
                    query.setLength(0);
                } else {
                    query.append(line).append(" ");
                }
            }
        }
    }

    /**
     * Executes an SQL update statement.
     *
     * @param sql The SQL update statement to execute.
     * @return The number of rows affected by the update.
     * @throws SQLException If a database access error occurs.
     */
    public int executeUpdate(final String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }

    /**
     * Executes an SQL query and returns the result set.
     *
     * @param query The SQL query to execute.
     * @return The result set containing the query results.
     * @throws SQLException If a database access error occurs.
     */
    public ResultSet executeQuery(final String query) throws SQLException {
        return statement.executeQuery(query);
    }

    /**
     * Closes the database connection and associated resources.
     *
     * @throws SQLException If a database access error occurs while closing the
     *                      resources.
     */
    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}

package dictionary.base.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    protected final Connection connection;
    protected final Statement statement;

    public Database(final String databasePath) throws SQLException, URISyntaxException {
        connection = DriverManager.getConnection(new URI("jdbc:sqlite", null, databasePath, null).toString());
        statement = connection.createStatement();
    }

    public int executeUpdate(final String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }

    public ResultSet executeQuery(final String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}

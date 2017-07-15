//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DMLQueryExecutor is a class to check and execute DML queries
 */
public class DMLQueryExecutor {
    private Connection connection;

    /**
     * The constructor for the DMLQueryExecutor class
     *
     * @param connection the connection to be used when accessing the DB
     */
    public DMLQueryExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * The execute function will execute the query if needed
     *
     * @param query The query to be preformed
     * @return the result set or null
     * @throws DBException will be throne if the query is erroneous
     */
    public ResultSet execute(String query) throws DBException {
        ResultSet rs = null;
        Statement stmt;


        // Try to create the statement before executing the query
        try {
            stmt = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }

        // Try and execute the query
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {

            // If an exception has occurred, throw an exception that specifies the error
            if (e.getMessage().contains("syntax")) {
                // There was a syntax error i.e. a structure error
                throw (new StructureException(e.getMessage(), e.getStackTrace()));
            } else {
                // There was a logical error
                throw (new LogicalException(e.getMessage(), e.getStackTrace()));
            }
        }

        return rs;
    }
}

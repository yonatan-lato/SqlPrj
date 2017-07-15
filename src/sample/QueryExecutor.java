//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The QueryExecutor is an abstract class that will preform the query against the DB
 */
public abstract class QueryExecutor {
    private Connection connection;

    /**
     * The constructor for the QueryExecutor class
     * @param connection the connection to be used when accessing the DB
     */
    public QueryExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * The execute function will execute the query if needed
     * @param query The query to be preformed
     * @return the result set or null
     * @throws DBException will be throne if the query is erroneous
     */
    public ResultSet execute(String query) throws DBException {
        ResultSet rs = null;
        Statement stmt;

        // Check if the query is correct
        if (this.isCorrect(query)) {

            // Try to create the statement before executing the query
            try {
                stmt = this.connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                return rs;
            }

            // Try and execute the query
            try {
                //TODO: Need to make sure that the DDL used execute or executeUpdate, use a second abstract function for the execution!
                //TODO: These functions have different return values, can't use inheritence for that!
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
        else
        {
            // The query dosn't meet the criteria, throw a structure exception
            throw (new StructureException("Query is not in the required format, reserved words are in lower case",
                                          Thread.currentThread().getStackTrace()));
        }
    }

    /**
     * The isCorrect function will check if the query is correct according to the specified criteria
     * @param query the query to check
     * @return true if it is, false otherwise
     */
    abstract boolean isCorrect(String query);
}

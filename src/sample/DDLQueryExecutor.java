//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The DMLQueryExecutor is a class to check and execute DDL queries
 */
public class DDLQueryExecutor {
    private Connection connection;

    /**
     * The constructor for the DDLQueryExecutor class
     *
     * @param connection the connection to be used when accessing the DB
     */
    public DDLQueryExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * The execute function will execute the query if needed
     *
     * @param query The query to be preformed
     * @return true on success, false on failure
     * @throws DBException will be throne if the query is erroneous
     */
    public Boolean execute(String query) throws DBException {
        Boolean answer = false;
        Statement stmt;


        if (isCorrect(query)) {

            // Try to create the statement before executing the query
            try {
                stmt = this.connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                return answer;
            }

            // Try and execute the query
            try {
                stmt.executeUpdate(query);

                answer = true;

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

        } else {
            // There was a syntax error i.e. a structure error
            throw (new StructureException("The input is in the wrong format", new Throwable().getStackTrace()));
        }

        return answer;
    }

    /**
     * The isCorrect function will check if the query is correct according to the specified criteria
     * @param query the query to check
     * @return true if it is, false otherwise
     */
    private boolean isCorrect(String query)
    {
        Pattern pattern = Pattern.compile("(?i)SELECT");

        Matcher m = pattern.matcher(query);

        if (m.find())
        {
            return false;
        }

        return true;
    }
}

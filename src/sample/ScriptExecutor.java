package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptExecutor {
    private Connection connection;

    /**
     * The constructor for the DDLQueryExecutor class
     *
     * @param connection the connection to be used when accessing the DB
     */
    public ScriptExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * The execute function will execute the query if needed
     *
     * @param script The script to be executed
     * @return the result set or null
     * @throws DBException will be throne if the query is erroneous
     */
    public ResultSet execute(BufferedReader script) throws DBException, IOException {
        ResultSet answer;
        Boolean queryAns = false;
        Statement stmt;

        // Try to create the statement before executing the query
        try {
            stmt = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Try and execute the queries
        try {
            String tempQuery;
            String query = "";

            // Read the script file line by line
            while ((tempQuery = script.readLine()) != null) {

                // If the current line dosn't end a statement
                if (tempQuery.indexOf(';') == -1) {
                    query = query.concat(tempQuery);
                    query = query.concat("\n");
                }
                // The current line ends a statement
                else
                {
                    // Close the current statement
                    query = query.concat(tempQuery.substring(0,tempQuery.indexOf(';')));
                    query = query.concat(";");

                    // Execute the current statement
                    queryAns = stmt.execute(query);

                    // Get the new statement
                    query = tempQuery.substring(tempQuery.indexOf(';') + 1);
                }
            }

            // If the last statement returned a result set
            if (queryAns) {
                // Get the result set
                answer = stmt.getResultSet();
            }
            else {
                answer = null;
            }

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

        // Return the result from the last statement
        return answer;
    }
}

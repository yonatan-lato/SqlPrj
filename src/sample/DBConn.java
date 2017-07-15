//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Properties;

/**
 * The DBConn connection is a singleton holder for the DB connection
 */
public class DBConn {
    private static DBConn instance;
    private Connection conn;
    private static final Object lock = new Object();

    /**
     * A private constructor
     */
    private DBConn() {
        try (BufferedReader br = new BufferedReader(new FileReader("conf.txt"))) {
            //TODO: need to get the configuration from the conf.txt file
            Properties connectionProps = new Properties();
            String user, password, url;

            // Read the parameters from the configuration file
            url = br.readLine();
            user = br.readLine();
            password = br.readLine();

            // Save the data to the properties variable
            //TODO: CHECK WITH A REAL DB, WHAT TO DO WITH THE SSL PROPERTY?
            connectionProps.put("user", user);
            connectionProps.put("password", password);
            connectionProps.put("useSSL", "false");

            // Create the connection
            conn = DriverManager.getConnection(url, connectionProps);
            //conn = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The close function will close the connection and nullify the related data
     */
    public void close()
    {
        try {
            this.conn.close();
            this.conn = null;
            DBConn.instance = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The getInstance function will return the DBConn holding the connection
     * @return The instance holding the connection
     */
    public static DBConn getInstance() {
        // Verify that no instance was created
        if (DBConn.instance == null) {

            // Lock the creation process
            synchronized (lock) {

                // Re-verify that no instance was created
                if (DBConn.instance == null) {

                    // Create and initialize the object
                    DBConn.instance = new DBConn();
                }
            }
        }

        // Return the instance
        return DBConn.instance;
    }

    /**
     * The getConnection function will return the DB connection
     * @return The DB connection
     */
    public Connection getConnection() {
        return this.conn;
    }
}

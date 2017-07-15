//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * The ListResultViewer class is responsible for parsing the result from the DB query to a ListView
 */
public class ListResultViewer {
    private ListView list;

    /**
     * The constructor for the ListResultViewer class
     * @param list the ListView to be updated
     */
    public ListResultViewer(ListView list) {
        this.list = list;
    }

    /**
     * The DisplayData function will parse the result from the DB query and update the ListView.
     * The ListView will not change if the result data is null
     * @param rs the result set to be parsed
     */
    public void DisplayData(ResultSet rs) {
        try {
            // If the result set is not null
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();

                // Clear all current items
                this.list.getItems().clear();

                // populate the rows
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();

                    // Add the data for the current row
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        row.add(rs.getString(i + 1));
                    }

                    // Add the row to the rows collection
                    this.list.getItems().add(rs.getString(1));
                }

                // Refresh the display
                this.list.refresh();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

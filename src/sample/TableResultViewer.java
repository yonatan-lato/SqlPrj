//TODO: ADD THE NEEDED COMMENT, AND VERIFY NO UNUSED IMPORTS
package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javafx.beans.property.SimpleStringProperty;

/**
 * The TableResultViewer class is responsible for parsing the result from the DB query to a TableView
 */
public class TableResultViewer {
    private TableView<ObservableList<String>> table;
    private ObservableList<ObservableList<String>> rows;

    /**
     * The constructor for the ListResultViewer class
     * @param table the TableView to be updated
     */
    public TableResultViewer(TableView table) {
        this.table = table;
    }

    /**
     * The DisplayData function will parse the result from the DB query and update the TableView.
     * The TableView will not change if the result data is null
     * @param rs the result set to be parsed
     */
    public void DisplayData(ResultSet rs) {
        rows = FXCollections.observableArrayList();

        try {
            // If the result set is not null
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();

                // Clear the current data
                this.table.getItems().clear();
                this.table.getColumns().clear();

                // Get all the columns of the table
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    TableColumn col = new TableColumn();

                    //TODO: This was a copy paste code!
                    // Use Java future in order to populate the table correctly
                    final int j = i;
                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    // Set the text of the column
                    col.setText(rsmd.getColumnLabel(i + 1));

                    // Add the column to the table
                    this.table.getColumns().add(i, col);
                }

                // populate the rows
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();

                    // Add the data for the current row
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        row.add(rs.getString(i + 1));
                    }

                    // Add the row to the rows collection
                    rows.add(row);
                }

                // Add the rows to the table and refresh the display
                this.table.setItems(rows);
            }

            // Refresh the display
            this.table.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

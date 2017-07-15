package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

//    Stage  window;
//    Button button;

    public void start(Stage primaryStage) throws Exception{

//        window =primaryStage;
//        window.setTitle("menu");
//        button = new Button("dml");
//        button.setOnAction(e-> getClass().getResource("sample.fxml"));

//        StackPane layot =new StackPane();
//        layot.getChildren().add(button);
//        Scene scene = new Scene(layot,300,399);
//        window.setScene(scene);
//        window.show();
       Parent root = FXMLLoader.load(getClass().getResource("ddl.fxml"));
        primaryStage.setTitle("DML");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        DMLQueryExecutor executor = new DMLQueryExecutor(DBConn.getInstance().getConnection());

        ScriptExecutor ex = new ScriptExecutor(DBConn.getInstance().getConnection());

        ex.execute(new BufferedReader(new FileReader("D:\\temp\\qr.txt")));

        /*DDLQueryExecutor ex = new DDLQueryExecutor(sample.DBConn.getInstance().getConnection());

        ex.execute("CREATE TABLE hotels1(\n" +
                "hotel_code int(4) unsigned NOT NULL default '0',\n" +
                "hotel_name char(20) default NULL,\n" +
                "city char(20) default NULL,\n" +
                "address char(20) default NULL,\n" +
                "hotel_rank int(1) unsigned NOT NULL default '0',\n" +
                "phone char(10) default NULL,\n" +
                "number_of_rooms int(4) unsigned NOT NULL default '0',\n" +
                "PRIMARY KEY  (hotel_code));");*/

        //sample.DBConn connection;
        //Statement stmt;

        //connection = sample.DBConn.getInstance();

        //stmt = connection.conn.createStatement();

        final LinkedHashSet<String> desiredItems = new LinkedHashSet<>();
        ResultSet rs;// = executor.execute("use myfirstdb;");
        rs = executor.execute("SELECT * FROM reservation;");

        final TableView tv = (TableView) root.lookup("#tvTest");

        TableResultViewer trv = new TableResultViewer(tv);

//        trv.DisplayData(rs);

        rs = executor.execute("show tables;");

        final ListView lvt = (ListView) root.lookup("#lvTables");
        final ListView lvc = (ListView) root.lookup("#lvColumns");
        final Button btnExec = (Button) root.lookup("#btnExec");
        final TextField tfWhere = (TextField) root.lookup("#txbWhere");
        final TextArea tfSelect = (TextArea) root.lookup("#txbSelect");
        final Button btnRun = (Button) root.lookup("#btnRun");

        final TextArea tarDDL = (TextArea) root.lookup("#tarDDL");
        final Button btnDDL = (Button) root.lookup("#btnDDL");
        final TextField tfDone = (TextField) root.lookup("#txbDone");

        //TODO: need to add support for queries from a source file!

//        btnExec.setDisable(true);

//        btnExec.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void hand   le(MouseEvent event) {
//                if (desiredItems.size() > 0)
//                {
//                    DMLQueryExecutor executor = new DMLQueryExecutor(DBConn.getInstance().getConnection());
//                    ResultSet rs = null;
//                    String query = "SELECT ";
//                    String table = lvt.getSelectionModel().getSelectedItem().toString();
//
//                    for (String item : desiredItems) {
//                        query = query.concat(item);
//                        query = query.concat(", ");
//                    }
//
//                    query = query.substring(0, query.length() - 2);
//                    query = query.concat(" FROM " + table);
//
//                    if (tfWhere.getText().length() > 0) {
//                        String whereClause = tfWhere.getText().trim();
//
//                        if (whereClause.contains(";")) {
//                            whereClause = whereClause.substring(0, whereClause.indexOf(';'));
//                        }
//
//                        System.out.println("Where clause is: " + whereClause);
//
//                        if (whereClause.startsWith("WHERE"))
//                        {
//                            query = query.concat(" " + whereClause + ";");
//                        }
//                        else  if (whereClause.toUpperCase().startsWith("WHERE"))
//                        {
//                            //TODO: ERROR MESSAGE?
//                            //TODO: Don't preform the query
//                        }
//                        else
//                        {
//                            query = query.concat(" WHERE " + whereClause + ";");
//                        }
//                    }
//                    else {
//                        query = query.concat(";");
//                    }
//
//                    try {
//                        rs = executor.execute(query);
//                    } catch (DBException e) {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//
//                        alert.setTitle(e.getTitle());
//                        alert.setHeaderText(null);
//                        alert.setContentText(e.getMessage());
//                        alert.show();
//                    }
//
//                    TableResultViewer trv = new TableResultViewer(tv);
//
//                    trv.DisplayData(rs);
//                }
//            }
//        });
//
//
//      /**
//      ////////////////////////////////////////////////
//          the select on the dml
//        //////////////////////////////////////////
//        **/
//
//        btnRun.setDisable(false);
//
//        btnRun.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                DMLQueryExecutor executor = new DMLQueryExecutor(DBConn.getInstance().getConnection());
//                ResultSet rs = null;
//                    if (tfSelect.getText().length() > 0) {
//
//                        String selectQr = tfSelect.getText().trim();
//
//                        if (selectQr.contains(";")) {
//                            selectQr = selectQr.substring(0, selectQr.indexOf(';'));
//                        }
//
//
//                    try {
//                        rs = executor.execute(tfSelect.getText());
//                    } catch (DBException e) {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//
//                        alert.setTitle(e.getTitle());
//                        alert.setHeaderText(null);
//                        alert.setContentText(e.getMessage());
//                        alert.show();
//                    }
//
//                    TableResultViewer trv = new TableResultViewer(tv);
//
//                    trv.DisplayData(rs);
//                }
//            }
//        });
//
//
//
//        lvt.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                DMLQueryExecutor executor = new DMLQueryExecutor(DBConn.getInstance().getConnection());
//                ResultSet rs = null;
//
//                try {
//                    rs = executor.execute("describe " + newValue + ";");
//                } catch (DBException e) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//
//                    alert.setTitle(e.getTitle());
//                    alert.setHeaderText(null);
//                    alert.setContentText(e.getMessage());
//                    alert.show();
//                }
//                lvc.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//
//                ListResultViewer lrv = new ListResultViewer(lvc);
//
//                lvc.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
//                    @Override
//                    public void onChanged(Change c) {
//                        if (c.next())
//                        {
//                            if (c.getList().size() == 0)
//                            {
//                                btnExec.setDisable(true);
//                            }
//                            else
//                            {
//                                btnExec.setDisable(false);
//                            }
//                        }
//                    }
//                });
//
//                lvc.getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
//                    @Override
//                    public void onChanged(Change c) {
//                        if (c.next()) {
//
//                            if (c.wasAdded())
//                            {
//                                desiredItems.addAll(c.getAddedSubList());
//                            }
//                            else if (c.wasRemoved())
//                            {
//                                desiredItems.removeAll(c.getRemoved());
//                            }
//                        }
//                    }
//                });
//
//                lrv.DisplayData(rs);
//            }
//        });
//
//
//        ListResultViewer lrv = new ListResultViewer(lvt);
//
//        lrv.DisplayData(rs);


        /**
         * the ddl functionality
         **/

        btnDDL.setDisable(false);

        btnDDL.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DDLQueryExecutor executor = new DDLQueryExecutor(DBConn.getInstance().getConnection());
                boolean rs = false;
                    if (tarDDL.getText().length() > 0) {

                        String DDLQr = tarDDL.getText().trim();

                        if (DDLQr.contains(";")) {
                            DDLQr = DDLQr.substring(0, DDLQr.indexOf(';'));
                        }


                    try {
                            //time out to show that this excutes again
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        rs = executor.execute(tarDDL.getText());

                        tfDone.setText("Done!");

                        // TODO: 7/11/2017  print done in the end of every time seperetly

                    } catch (DBException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle(e.getTitle());
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.show();
                    }

                    //TableResultViewer trv = new TableResultViewer(tv);

                    //trv.DisplayData(rs);
                }
            }
        });









        DBConn.getInstance().close();
        //rv.DisplayData(null);

//        try {
//            //rs = stmt.executeQuery("show tables");
//            //rs = stmt.executeQuery("select id, name, state from guests;");
//            rs = stmt.executeQuery("use myfirstdb;");
//            rs = stmt.executeQuery("select1 * from reservation;");
//            String str;
//
//            ResultSetMetaData rsmd = rs.getMetaData();
//            String name = rsmd.getColumnLabel(1);
//            System.out.println(name);
//
//            /*while (rs.next())
//            {
//                str = rs.getString("Tables_in_myfirstdb");
//
//                System.out.println(str);
//            }*/
//
//            TableView tv = (TableView) root.lookup("#tvTest");
//
//            TableResultViewer rv = new TableResultViewer(tv);
//
//            rv.DisplayData(rs);
//
//            tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//                @Override
//                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                    System.out.println("Selected item is: ");
//                    System.out.println(newValue.toString());
//                }
//            });
//
//            System.out.println("Done");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        //connection.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

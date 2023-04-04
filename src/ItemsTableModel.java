import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsTableModel extends AbstractTableModel {
    private  Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private String query = "SELECT id, name, price, total_goods, total FROM items";
    private boolean connectedToDB = false; //keep track of db connection status

    /*public ItemsTableModel(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        // create Statement to query database
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDB = true;

        updateData();
    }*/

    public void updateData() throws SQLException, IllegalStateException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Goods", "root", "mysqltonny123!");
        // create Statement to query database
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        connectedToDB = true;

        resultSet = statement.executeQuery(query);
        metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        int rowCount = resultSet.getFetchSize();
        if (rowCount == 0) {
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.beforeFirst();
        }

        MyGlobals.ITEMS = new Object[rowCount][columnCount];

        int row = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                MyGlobals.ITEMS[row][i-1] = resultSet.getObject(i);
            }
            row++;
        }
    }



    public int getRowCount() {
        return Math.min(MyGlobals.ITEMS.length - (MyGlobals.CURRENT_PAGE - 1) * MyGlobals.ITEMS_PER_PAGE, MyGlobals.ITEMS_PER_PAGE);
    }

    public int getColumnCount() {
        return MyGlobals.COLUMN_NAMES.length;
    }

    public Object getValueAt(int row, int col) {
        return MyGlobals.ITEMS[(MyGlobals.CURRENT_PAGE - 1) * MyGlobals.ITEMS_PER_PAGE + row][col];
    }

    public String getColumnName(int col) {
        return MyGlobals.COLUMN_NAMES[col];
    }

    public void update() {
        // Enable/disable the previous/next buttons as needed



        // Fire a table data changed event to update the table display
        fireTableDataChanged();
    }




}
package utilities;

import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static Connection connection; //interface implemented by driver in our case mysql
    private static Statement statement;
    private static final String JDBC_URL = ConfigReader.getProperty("jdbc_url");
    // or: String url = "jdbc:mysql://3.129.60.236:3306/digitalbank?user=digitalbank&password=Demo123!";

    //Singleton - only one connection can be created in a time.
    // Opening connection to DB. If connection is not yet opened.
    public static void openDBConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(JDBC_URL , "digitaluser", "Demo123!");
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Can't establish connection to DB");
        }
    }

    public static void close() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            statement = null;
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Can't close connection to DB");
        }
    }
    public static ResultSet query(String query) {
        if (connection == null) openDBConnection();

        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("Not able to execute query");
        }
        return null;
    }

    public static List<String> getColumnNames(ResultSet rs) {
        List<String> columnNames = new ArrayList<>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i < columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getLocalizedMessage());
        }

        return columnNames;
    }

//key is column name, value is data from that column
    public static List<Map<String, Object>> convertResultSet(ResultSet rs) {

        List<Map<String, Object>> table = new ArrayList<>(); //table.size() return how many rows you have(each map is a row)
        List<String> columnNames = getColumnNames(rs);

        //Populate table from result set
        //Iterate through each row (result set)
        while (true) {
            //for each row we create a new Hashmap/ one row=one map(key is column name, value is data from that column)
            Map<String, Object> row = new HashMap<>();
            try {
                if (!rs.next()) break; //Moves the cursor forward one row from its current position, until rows exist

                //iterate through each column in order to populate the map
                for (String columnName : columnNames) {
                    row.put(columnName, rs.getObject(columnName));//value of that specific column.
                    // method getObject()Gets the value of the designated column in the current row
                }
                table.add(row); //adding to our List of maps
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//close result set after we are done
        try{
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return table;
    }

    //get column names by using result Set metadata
    public static void main(String[] args) {
//we dont need to open connection explicitly as method query() has it
        ResultSet rs = DBUtils.query("SELECT id FROM account WHERE name = 'Personal Savings';");
        List<String> columns = DBUtils.getColumnNames(rs);
        columns.forEach(System.out::println);

        List<Map<String, Object>> table = DBUtils.convertResultSet(rs);
        String s = String.valueOf(table.get(0).get("id")); //first get -> return map in position, second get -> return value of provided key
        System.out.println(s);
        table.forEach(System.out::println);


        // to find specific value you can loop through table of maps
        // but better just to create specific query:
        // ResultSet rs = query("SELECT payment_amount FROM account WHERE account_number ="46856765002");
//        double expectedPaymentAmount = 0.0;
//        for (Map<String, Object> map : table) {
//            if (map.get("account_number").toString().equals("46856765002")) {
//                double actualAmount = (double) map.get("payment_amount");
//                Assert.assertEquals(expectedPaymentAmount, actualAmount);
//            }
//        }

        DBUtils.close();


        //practice all the sequence of steps:

//        Connection connection = DriverManager.getConnection(JDBC_URL + "?useSSL=false", "digitaluser", "Demo123!")
//        Statement statement = connection.createStatement();
//
//        ResultSet rs = statement.executeQuery("Select * FROM account");
//        //specify number of column or string Name
//        while (rs.next()) {
//            System.out.println(rs.getString(1)); // printing all values from 1 column
//        }

//        ResultSetMetaData metaData = rs.getMetaData();
//        int columnCount = metaData.getColumnCount();
//        for (int i = 1; i < columnCount; i++) {
//            System.out.println(metaData.getColumnName(i));
//        }

//        rs.close(); //after you process your result set - close it
////        // in order to query the 2 time under the same statement
////        ResultSet rs2 = statement.executeQuery("Select * FROM users");
////        while (rs2.next()){
////            System.out.println(rs2.getString(2));
////        }
////        rs2.close();
//        statement.close();
//        //resource leak  when connection not close
//        connection.close(); //its automatically close statement and result set, but it's a good practice to do it
    }

}

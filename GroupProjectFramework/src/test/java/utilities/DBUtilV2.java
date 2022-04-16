package utilities;

import dbModels.UserProfile;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtilV2 {
    private static Connection connection; //interface implemented by driver in our case mysql
    private static Statement statement;
    private static final String JDBC_URL = ConfigReader.getProperty("jdbc_url");
    private static BeanProcessor beanProcessor;

    public static void openDBConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(JDBC_URL, "digitaluser", "Demo123!");
                statement = connection.createStatement();
                beanProcessor = new BeanProcessor();
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

    //Parametrized query

    //VarArgs.This argument that can accept variable number of values is called varargs. [array]You can provide here as many objects as you need or none.The second String will be considered as an array of 1 element.
    // if I provide 3 Strings: first will be considered as query, second and third as array of 2 elements
    //["String"]
    //["String1", "String2"]]

    //SELECT * FROM digitalbank.user_profile WHERE id= ?;
    public static ResultSet query(String query, Object... params) {
        if (connection == null) openDBConnection();
        try {
            if (params.length == 0) return statement.executeQuery(query);
            else {
                PreparedStatement preparedStatement = connection.prepareStatement(query); //tracking ? and helping insert parameters instead
                //Replace ??? with params:
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]); //in preparedStatement index starts from 1, in array from 0[i]
                }
                return preparedStatement.executeQuery();
            }
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

    // T declares generic data type
    // In a way it postpones the Data type declaration
    public static <T>List<T> convertResultSetToBeans(ResultSet rs, Class<T> beanClass){
        try {
            return beanProcessor.toBeanList(rs,beanClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //key is column name, value is data from that column
    public static List<Map<String, Object>> convertResultSetToListOfMaps(ResultSet rs) {

        List<Map<String, Object>> table = new ArrayList<>(); //table.size() return how many rows you have(each map is a row)
        List<String> columnNames = getColumnNames(rs);

        while (true) {
            Map<String, Object> row = new HashMap<>();
            try {
                if (!rs.next()) break; //Moves the cursor forward one row from its current position, until rows exist
                for (String columnName : columnNames) {
                    row.put(columnName, rs.getObject(columnName));//value of that specific column.
                }
                table.add(row);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }

    public static void main(String[] args) {
        String query = "SELECT * " +
                "FROM digitalbank.user_profile " +
                "WHERE country = ?" +
                "OR country = ?" +
                "OR country = ?" +
                "ORDER BY country;";
        ResultSet rs = DBUtilV2.query(query, "US", "USA", "United States");
        DBUtilV2.convertResultSetToListOfMaps(rs).forEach(System.out::println);
        System.out.println("-------------------------------");
        String query2 = "SELECT * " +
                "FROM digitalbank.user_profile " +
                "WHERE country = ?" +
                "AND dob >= ?;";
          rs = DBUtilV2.query(query2, "USA", "1994-05-12 00:00:00"); //positions matter respectively to query
        DBUtilV2.convertResultSetToListOfMaps(rs).forEach(System.out::println);
        System.out.println("-----------------------------");
        String query3 = "SELECT * " +
                "FROM digitalbank.user_profile " +
                "WHERE country = ?" +
                "AND dob >= ?;";
        rs = DBUtilV2.query(query3,"USA","1996-05-12 00:00:00" );
        //it will display in a form of objects (and it will assign only fields that i declare in a class, regardless the query
        DBUtilV2.convertResultSetToBeans(rs, UserProfile.class).forEach(System.out::println);
    }

}

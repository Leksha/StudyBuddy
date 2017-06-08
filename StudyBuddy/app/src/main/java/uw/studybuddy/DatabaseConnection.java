package uw.studybuddy;

/**
 * Created by apalissery on 2017-06-07.
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private Connection connection;
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;

    public DatabaseConnection() {
        super();
        this.host = "localhost";
        this.port = "5432";
        this.username = "apalissery";
        this.password = "123456";
        this.database = "studybuddy";

        try {
            connection = getConnection();
        } catch (Exception e) {
            System.out.println("Database Connection Failed: " + e.getMessage());
            connection = null;
        }
    }

    private Connection getConnection() throws Exception {

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = null;
            this.connection = DriverManager.getConnection("jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        } catch  (ClassNotFoundException e) {
            System.out.println("Postgres JDBC Driver not found");
            e.printStackTrace();
        }
        return connection;

    }

    private void disconnect() throws Exception {
        if(this.connection != null) {
            this.connection.close();
            this.connection = null;
        }
    }

    public String getUserInfo(String userName) {
        //query in db for username
        try {
            String query = "SELECT * FROM USER_INFO WHERE NAME IS " + userName;
            String result = "";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for(int i = 1; i < count; i++) {

            }
            result = rs.getString(1);
            return result;
        } catch (SQLException e) {
            System.out.println("bad query");
            return "";
        }
    }

}

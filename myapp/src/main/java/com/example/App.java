package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class App {
    private String url;
    private String username;
    private String password;

    // constructor loads the properties
    public App(){
        // load the properties from the application.properties file
        loadProperties();
        
    }

    // load properties from the properties file and use environment variables
    private void loadProperties(){
        Properties properties = new Properties();

        // Get properties from environment variables first
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        
        // If environment variables are not set, fall back to the properties file
        if (url == null || url.isEmpty()) {
            url = properties.getProperty("DB_URL");
        }
        if (username == null || username.isEmpty()) {
            username = properties.getProperty("DB_USERNAME");
        }
        if (password == null || password.isEmpty()) {
            password = properties.getProperty("DB_PASSWORD");
        }
        
        // Now set the values for database connection
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Note addNote(String content, String priority) throws SQLException {
        // open connection to db and close when done
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            // create statement
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Notes(content, priority) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            // assign any parameters their values
            ps.setString(1, content);
            ps.setString(2, priority);

            //execute statment
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            // process results
            // while there is another record in the results to pprocess
            while (rs.next()){
                // get the value of the first column in that resultset row
                long resultId = rs.getLong(1);

                //.. and return a Note with the generated id in its state, as well as the other value
                return new Note(resultId, content, priority);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // read
    public Note getNoteById(long id){
        // get connection to database and do whatever operation required then close it
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            // create your statement
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM notes WHERE id = ?");

            // assign any parameters their values
            ps.setLong(1, id);

            // execute statement
            ResultSet rs = ps.executeQuery();

            // process results
            // while there is another in the resultset to process
            while (rs.next()){
                // get values from respective columns
                long resultId = rs.getLong("id");
                String content = rs.getString("content");
                String priority = rs.getString("priority");

                // return note with those values as its state
                return new Note(resultId, content, priority);
            }

        } catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }
    
}
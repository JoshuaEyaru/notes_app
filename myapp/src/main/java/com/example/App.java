package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    // database url + credentials
    String url = "jdbc:mysql://localhost:3306/example";
    String username = "root";
    String password = "";

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
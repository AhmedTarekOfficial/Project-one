package main.java.com.SaftyHub.backend.Databasehandeling;

import java.sql.*;

public class Link {
     Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";
    



    public  void Connection(){
          try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            if (con != null) {
                System.out.println("Connection Successful");
            } else {
                System.out.println("Connection Failed please check your internet connection !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
}

package com.company;

import java.sql.*;

public class dbConnection
{

    public dbConnection()
    {

    }

    public void addMessadge(String message)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO testtable VALUES ('"+ message +"');");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}


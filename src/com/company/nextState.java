package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class nextState
{
    public nextState()
    {

    }
    public void changeState (long chatID , int newstate)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            stmt.executeUpdate("UPDATE INTO stateIDTable VALUES ("+ chatID +" , "+ newstate+" );");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

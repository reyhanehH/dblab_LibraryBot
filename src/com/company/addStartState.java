package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class addStartState
{
    addStartState(long id){
        this.newState(id);
    }

    private void newState (long chatID)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO stateIDTable VALUES ("+ chatID +" , 1 );");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
}


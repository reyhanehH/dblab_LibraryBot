package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class checkChatId
{
    long id;
    int state = -1;
    public checkChatId(Long chatId){
        id= chatId;
    }

    public int checkId ()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            ResultSet rs ;
            rs = stmt.executeQuery("SELECT * from stateIDTable where id = "+ id +";");

            if (rs.next())
            {
                state = rs.getInt("state");
            }

            rs.close();
            stmt.close();
            con.close();


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return state;
    }


}

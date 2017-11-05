package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHelper
{
    public DBHelper()
    {

    }

    public void changeState (long chatID , int newstate)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            stmt.executeUpdate("UPDATE stateidtable set state = \'"+ newstate + "\' where chatid = \'"+chatID +"\';");
            //update stateidtable set state = '1' where chatid= '1';
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void newState (long chatID)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

    public int checkId (long id)
    {
        int state = -1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            ResultSet rs ;
            rs = stmt.executeQuery("SELECT * from stateidtable where chatid = "+ id +";");

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
    public void addBook (String bookName ,String writerName ,String publisher, Integer price)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");
            stmt.executeUpdate("INSERT INTO book_test_table VALUES (\'" + bookName + "\', \'"+ writerName +"\' , "+price +",\'"+publisher +"\');");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

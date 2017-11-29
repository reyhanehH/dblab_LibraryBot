package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper
{
    private static DBHelper dbHelper = null;
    private DBHelper()
    {

    }

    public static synchronized DBHelper getDbHelper()
    {
        if (dbHelper == null)
            dbHelper = new DBHelper();
        return dbHelper;
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
    public void addBook (String bookName ,String writerName ,String publisher, Integer price, String photoID)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");
            stmt.executeUpdate("INSERT INTO book_test_table (book_name ,writer_name ,price , publisher ,image_id) VALUES " +
                    "(\'" + bookName + "\', \'"+ writerName +"\' , "+price +",\'"+publisher +"\' ,\'"+photoID+"\');");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public BookInfo getBook ( int id)
    {
        System.out.println("in getBook -- DBHelper");
        BookInfo book= null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");
            ResultSet resultSet = stmt.executeQuery("select * from book_test_table where id = " + id + ";");
            if (resultSet.next()) {

                System.out.println(resultSet.getString(2) + "/n" + resultSet.getString(3)
                        + "/n" + resultSet.getString(5) + "/n" + resultSet.getInt(4)+"/n" +
                        ""+ resultSet.getString(6));

                book = new BookInfo(resultSet.getString(2), resultSet.getString(3)
                        , resultSet.getString(5), resultSet.getInt(4) ,resultSet.getString(6));

            }
            else {
                System.out.println("not found in get book ");
            }

            resultSet.close();
            stmt.close();
            con.close();
            return book;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return book;
    }

    //stored procedure
    public String insertBook_stored (String bookName ,String writerName ,String publisher, Integer price, String photoID)
    {
        String result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb", "root", "123456");

            CallableStatement callableStatement = con.prepareCall("{call insertBook(?,?,?,?,?,?)}");
            callableStatement.setString(1, bookName);

            callableStatement.setString(2, writerName);
            callableStatement.setInt(3, price);
            callableStatement.setString(4, publisher);
            callableStatement.setString(5, photoID);
            callableStatement.registerOutParameter(6 , Types.VARCHAR);

            callableStatement.execute();

            result = callableStatement.getString(6);
            System.out.println("result : "+ result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<BookInfo> search_bookName (String bookName)
    {
        ArrayList<BookInfo> bookInfoList = new ArrayList<BookInfo>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");
            ResultSet resultSet = stmt.executeQuery("select * from book_test_table where book_test_table.book_name =  \'"+ bookName+"\' ;");

            while (resultSet.next())
            {
                BookInfo book = new BookInfo(resultSet.getString(2), resultSet.getString(3)
                        , resultSet.getString(5), resultSet.getInt(4) ,resultSet.getString(6));
                bookInfoList.add(book);
            }

            resultSet.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return bookInfoList;
    }
}

package com.company;



import java.sql.*;
import java.util.ArrayList;

public class DBHelper
{
    private static DBHelper dbHelper = null;


  private   ArrayList <BookInfo> idview  = new ArrayList<>();
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
            Statement stmt=con.createStatement();

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
            //CallableStatement callableStatement = con.prepareCall("{call transaction(?,?,?,?,?,?)}");
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

    public void add_userName (long chatID , String userName)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");
            stmt.executeUpdate("INSERT INTO usertable (chatid ,user_name ) VALUES " +
                    "(\'" + chatID + "\', \'"+ userName +"\' );");

            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


    public Profile get_userName (long chatID)
    {
        System.out.println("get_userName -- DBHelper");
        Profile  profile= null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");
            ResultSet resultSet = stmt.executeQuery("select * from usertable where chatid = " + chatID + ";");

            if (resultSet.next())
            {
                System.out.println(resultSet.getString(2));
                profile = new Profile(resultSet.getString(2));
              //  System.out.println("end)))))))))))))))))))))");
            }

            resultSet.close();
            stmt.close();
            con.close();
            return profile;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return profile;
    }

    public  ArrayList<BookInfo>   get_book_like (long chatID)
    {
        BookInfo book= null;
        //int i = 0 ;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            Statement stmt2=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");

           // ResultSet resultSet2 =  null;
            ResultSet resultSet = stmt.executeQuery("select  *  from book_test_table inner join bookliketable  on bookliketable.bookid = book_test_table.id   where bookliketable.chatid =  " + chatID + ";");
            while (resultSet.next()) {
                    // idview[i] = resultSet.getInt(1) ;
                    System.out.println("\n" + resultSet.getInt(1) + " \n " + resultSet.getString(2) + "\n" + resultSet.getString(3)
                            + "\n" + resultSet.getString(5) + "\n" + resultSet.getInt(4) + "\n" +
                            "" + resultSet.getString(6));

                    book = new BookInfo(resultSet.getString(2), resultSet.getString(3)
                            , resultSet.getString(5), resultSet.getInt(4), resultSet.getString(6));
                    idview.add(book);

            }
            resultSet.close();
            
            stmt.close();
            con.close();

        }
        catch(Exception e)
        {
            System.out.println(e+"\n" );
            e.printStackTrace();
        }

       return  idview  ;
    }
    public  void addLikeBook (long chatID , int bookID)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            //here sonoo is database name, root is username and password

            Statement stmt=con.createStatement();
            //stmt.executeUpdate("INSERT INTO stateidtable VALUES ("+ chatID +" , \'1\' );");

            //ResultSet userid = stmt.executeQuery("select useid from usertable where chatid = " + chatID + ";");
            stmt.executeUpdate("INSERT INTO bookliketable (  bookid  , chatid) VALUES " +
                    "(\'" + bookID + "\', \'"+ chatID +"\' );");


            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    public  boolean checkRegistering (long chatID)
    {
        boolean result = false;
        String userName = null ;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            Statement stmt=con.createStatement();

            ResultSet resultSet = stmt.executeQuery("select  * from usertable   where chatid =  " + chatID + ";");
            if (resultSet.next()) {
                System.out.println("\n" + resultSet.getString(2));
                userName = (resultSet.getString(2));
            }
                if (userName == null) {
                    result = false;
                } else {
                    result = true;
                }

            resultSet.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e+"\n" );
            e.printStackTrace();
        }


        return  result ;
    }
    public  boolean checkLikeBook (long chatID , int idViewBook)
    {
        System.out.println("idViewBook = " + idViewBook);
        boolean result = false;
        int book_id = 0 ;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb","root","123456");
            Statement stmt=con.createStatement();

            ResultSet resultSet = stmt.executeQuery("select * from  bookliketable    where chatid =  " + chatID + " and bookid= " + idViewBook + ";");
            while (resultSet.next()) {

                System.out.println("\n" + resultSet.getInt(1));
                book_id = (resultSet.getInt(1));
                System.out.println("bookid = " + book_id);

                if (book_id != 0) {
                    result = true;
                } else
                {
                    result = false;
                }
            }
            resultSet.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e+"\n" );
            e.printStackTrace();
        }
        return  result ;
    }

    public int getCount_book_test_table ()
    {
         int countStar = 0 ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb", "root", "123456");
            Statement stmt = con.createStatement();

            ResultSet resultSet = stmt.executeQuery("select count(*) from book_test_table ");
            if (resultSet.next()) {
                countStar = resultSet.getInt(1);
                System.out.println("countStar = " + countStar);
            }
            resultSet.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return  countStar ;
    }
    public int getCount_bookliketable (long chatID)
    {
        int countStar = 0 ;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybotdb", "root", "123456");
            Statement stmt = con.createStatement();

            ResultSet resultSet = stmt.executeQuery("select count(*) from bookliketable where chatid =" + chatID + ";");
            if (resultSet.next()) {
                countStar = resultSet.getInt(1);
                System.out.println("countStar = " + countStar);
            }
            resultSet.close();
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return  countStar ;
    }

}

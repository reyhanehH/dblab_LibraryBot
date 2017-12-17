package com.company;

public class Profile
{
    private String user_name ;
    private long chatid ;

    public Profile ( String user_name )
    {

        this.user_name = user_name ;

    }
    public  long getChatid ()
    {
        return chatid ;
    }

    public String get_user_name()
    {
        return  user_name;
    }

}

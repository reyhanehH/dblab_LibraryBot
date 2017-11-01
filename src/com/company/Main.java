package com.company;

import com.company.botTest;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args)
    {
        /*
        System.out.println("Helllllllllo");
        dbConnection connection = new dbConnection();
        connection.addMessadge("سلام");
	*/
        //DBHelper dbHelper = new DBHelper();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        //botTest bot = new botTest();
        stateLike statelike = new stateLike();

        try
        {
            telegramBotsApi.registerBot(statelike);
            //telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiRequestException e)
        {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}

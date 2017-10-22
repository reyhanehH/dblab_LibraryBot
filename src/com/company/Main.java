package com.company;

import com.company.botTest;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args)
    {
        /*
        System.out.println("Helllllllllo");
        dbConnection connection = new dbConnection();
        connection.addMessadge("سلام");
	*/
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        //botTest bot = new botTest();
        stateLike stateLike = new stateLike();

        try
        {
            telegramBotsApi.registerBot(stateLike);
        }
        catch (TelegramApiRequestException e)
        {
            e.printStackTrace();
        }
    }
}

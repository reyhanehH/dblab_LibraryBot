package com.company;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.*;

public class stateLike extends TelegramLongPollingBot
{
    @Override
    public void onUpdateReceived(Update update)
    {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        //create new DBHelper
        DBHelper dbHelper = new DBHelper();

        int state = dbHelper.checkId(chatId);

        if (state == -1)//create state
        {
            dbHelper.newState(chatId);
        }
        System.out.println("first ");
        replyMessage (state ,chatId ,message);
    }


    void replyMessage(int state, Long chatId, String message)
    {
        DBHelper dbHelper = new DBHelper();
        //welcome <- 1
        // show emkanat <- 2
        System.out.println("in reply method");
        switch (state)
        {
            case '1': //welcome
            {
                //welcome message
                SendMessage sendMessage = new SendMessage().setChatId(chatId);
                sendMessage.setText("به بات کتابخانه خوش آمدید \n برای دیدن امکانات این بات کلید امکانات را فشار دهید.");
                System.out.println("before show button");
                //show button
                KeyboardButton[] buttons = new KeyboardButton[1];
                buttons[0] = new KeyboardButton();
                buttons[0].setText("امکانات");
                //buttons[1] = new KeyboardButton();
                //buttons[1].setText("انصراف");
                System.out.println("before update");

                dbHelper.changeState(chatId, 2);
                System.out.println("after update");
                break;
            }
            case '2': //show emkanat
        }
    }


    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "428437656:AAFGCJlq2DBzzh6i2fVzwW06oZ-7-Z7C9TQ";
    }
}

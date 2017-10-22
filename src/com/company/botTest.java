package com.company;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class botTest extends TelegramLongPollingBot
{
    @Override
    public void onUpdateReceived(Update update)
    {

        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        if (message.equals("/start"))
        {
            System.out.println("meesage is : " + message);
            sendMessage.setText("welcome ... please enter something :");
        }
        else {
            System.out.println("meesage is : " + message);

            dbConnection connection = new dbConnection();
            connection.addMessadge(message);


            sendMessage.setText("your String is :" + update.getMessage().getText() + "\nYour message added in DB.");
        }
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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

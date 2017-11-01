package com.company;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.*;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class stateLike extends TelegramLongPollingBot
{
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        System.out.println("message is: " + message);
        Long chatId = update.getMessage().getChatId();
        //create new DBHelper
        DBHelper dbHelper = new DBHelper();

        //check the input:
        int state = dbHelper.checkId(chatId);


        if (state == -1)//create state for new user
        {
            dbHelper.newState(chatId);
            state = 1;
        }

        System.out.println("in statelike class ... after check state! state:"+ state);
        replyMessage (state ,chatId ,message ,update);
    }


    void replyMessage(int state, Long chatId, String message ,Update update)
    {
        DBHelper dbHelper = new DBHelper();
        //welcome <- 1
        // show emkanat <- 2
        System.out.println("in reply method");
        switch (state)
        {
            case 1: //welcome
            {
                //welcome message
                //SendMessage sendMessage = new SendMessage().setChatId(chatId);
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                sendMessage.setText("به بات کتابخانه خوش آمدید \n برای دیدن امکانات این بات کلید امکانات را فشار دهید.");
                System.out.println("before show button");
                /*
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                KeyboardButton button = new KeyboardButton();
                button.setText("Button1");
                button.setRequestContact(true);
                row.add(button);
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                return keyboardMarkup;
                 */
                //show button
                //----------------------------------------------------------------
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                //List<List<KeyboardRow>> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("امکانات");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);
                row.add(button1);

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("انصراف");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);

                row.add(button2);
                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                //----------------------------------------------------------------
                try {
                    sendMessage(sendMessage);

                }catch (TelegramApiException e){
                    e.printStackTrace();
                }

                System.out.println("before update");

                //dbHelper.changeState(chatId, 2);
                System.out.println("after update");
                break;
            }
            case 2: //show emkanat
                System.out.println("in state 2 . chatId : " + chatId);
                break;

                default: break; // if not found state
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

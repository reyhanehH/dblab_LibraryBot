package com.company;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class stateLike extends TelegramLongPollingBot
{

    @Override
    public void onUpdateReceived(Update update)
    {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        //checkChatId
        checkChatId checkChatId = new checkChatId(chatId);
        int state = checkChatId.checkId();

        if (state == -1)
        {
            //set chat id , start state
            addStartState startState = new addStartState(chatId);
        }
        replyMessage (state ,chatId ,message);
    }


    void replyMessage(int state, Long chatId, String message)
    {
        //start <- 1
        // show emkanat <- 2

        switch (state)
        {
            case 1: //start
            {
                //next state to state1
                //nextState nextState = new nextState();
                //nextState.changeState(chatId , 1);
                //welcome message
                SendMessage sendMessage = new SendMessage().setChatId(chatId);
                sendMessage.setText("به بات کتابخانه خوش آمدید \n برای دیدن امکانات این بات کلید امکانات را فشار دهید.");
                //show button


                break;
            }
            case 2: //
        }
    }


    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }
}

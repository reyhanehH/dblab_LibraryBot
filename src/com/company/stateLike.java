package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
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
    DBHelper dbHelper;
    public stateLike ()
    {
        dbHelper = new DBHelper();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        System.out.println("message is: " + message);

        Long chatId = update.getMessage().getChatId();
        //create new DBHelper


        //check the input:
        int state = dbHelper.checkId(chatId);
        System.out.println("state before if : " + state);

        if (state == -1)
        {
            System.out.println("add new chatid in DB");
            dbHelper.newState(chatId);
        }


        if (message.equals("/start")) {
            System.out.println("in if second .... state is :"+ state);
            dbHelper.changeState(chatId, 1);
        }else if (message.equals("امکانات")) {
            dbHelper.changeState(chatId, 2);
        } else if (message.equals("انصراف")) {
            dbHelper.changeState(chatId, 1);
        } else if (message.equals("اضافه کردن کتاب"))
        {
            dbHelper.changeState(chatId, 3);
        }else if (message.equals("مشاهده کتاب")) {
            dbHelper.changeState(chatId, 4);
        }

        state = dbHelper.checkId(chatId);
        System.out.println("in statelike class ... after check state! state:"+ state);
        replyMessage (state ,chatId ,message ,update);
    }


    void replyMessage(int state, Long chatId, String message ,Update update)
    {
        DBHelper dbHelper = new DBHelper();
        //welcome <- 1
        // show emkanat <- 2
        System.out.println("in reply method");
        switch (state) {
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

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                System.out.println("before update");

                //dbHelper.changeState(chatId, 2);
                System.out.println("after update");
                break;
            }
            case 2: //show emkanat
            {
                System.out.println("in state 2 . chatId : " + chatId);
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                sendMessage.setText("یکی از گزینه های زیر را انتخاب کنید.");
                //button --------------------------
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                //List<List<KeyboardRow>> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("اضافه کردن کتاب");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);
                row.add(button1);

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("مشاهده کتاب");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);
                row.add(button2);

                KeyboardButton button3 = new KeyboardButton();
                button3.setText("انصراف");
                button3.setRequestContact(false);
                button3.setRequestLocation(false);
                row.add(button3);

                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                //-----------------------------------
                try {
                    sendMessage(sendMessage);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: //add new book
            {
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                if (message.equals("اضافه کردن کتاب")) {

                    sendMessage.setText(" لطفا مشخصات زیر را در یک پیام وارد کنید و بین هر مشخصه یک اینتر بزنید:" +
                            " \n نام کتاب" +
                            "\n نام نویسنده" +
                            "\n نام ناشر" +
                            "\n قیمت" +
                            "\n یا کلید انصراف را برای خروج فشاردهید.");

                    //button

                    List<KeyboardRow> keyboardRows = new ArrayList<>();
                    //List<List<KeyboardRow>> keyboardRows = new ArrayList<>();
                    KeyboardRow row = new KeyboardRow();

                    KeyboardButton button2 = new KeyboardButton();
                    button2.setText("انصراف");
                    button2.setRequestContact(false);
                    button2.setRequestLocation(false);

                    row.add(button2);
                    keyboardRows.add(row);

                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setKeyboard(keyboardRows);

                    sendMessage.setReplyMarkup(replyKeyboardMarkup);

                }
                //------
                else {
                    System.out.println("message : " + message);
                    String bookName ,writerName,publisherName ;
                    Integer price;
                    String[] aarayInfo = message.split("\n");
                    bookName = aarayInfo[0];
                    writerName = aarayInfo[1];
                    publisherName = aarayInfo[2];
                    price = Integer.parseInt(aarayInfo[3]);

                    // check info

                    dbHelper.addBook(bookName,writerName,publisherName, price);
                    sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");

                    //else
                    //پیام بدهد که مجددا وارد کنید کتاب را
                }

                try {
                    sendMessage(sendMessage);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 4:
                break;

            case 5:
            {

                
                break;
            }

                default: break; // if not found state
        }
    }

    private boolean checkInfo_AddBook (String bookName ,String writerName ,String publisher ,Integer price)
    {
        //if ()
            return true;
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

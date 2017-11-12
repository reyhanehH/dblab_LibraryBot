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
    public static String bookName, writerName, publisher ,price;
    DBHelper dbHelper;
    public stateLike ()
    {
        //dbHelper = new DBHelper();
        dbHelper = DBHelper.getDbHelper();
    }
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        System.out.println("message is: " + message);
        Long chatId = update.getMessage().getChatId();
        //create new DBHelper

        //check the input:
        int state = dbHelper.checkId(chatId);

        if (state == -1)
        {
            dbHelper.newState(chatId);
            state = 1;
        }

        if (message.equals("/start")) {
            dbHelper.changeState(chatId ,1);
        } else if (message.equals("امکانات")) {
            dbHelper.changeState(chatId, 2);
        } else if (message.equals("انصراف")) {
            dbHelper.changeState(chatId, 1);
        } else if (message.equals("معرفی کتاب"))
        {
            dbHelper.changeState(chatId, 3);
        }else if (message.equals("مشاهده کتاب"))
        {
            //dbHelper.changeState(chatId, 4);
        }

        state = dbHelper.checkId(chatId);
        System.out.println("in statelike class ... after check state! state:"+ state);
        replyMessage (state ,chatId ,message ,update);
    }


    void replyMessage(int state, Long chatId, String message ,Update update)
    {
        DBHelper dbHelper = DBHelper.getDbHelper();



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
                //button —------------------------
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                //List<List<KeyboardRow» keyboardRows = new ArrayList<>());
                KeyboardRow row1 = new KeyboardRow();

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("معرفی کتاب");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);
                row1.add(button1);

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("مشاهده کتاب ها");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);
                row1.add(button2);

                KeyboardRow row2 = new KeyboardRow();

                KeyboardButton button3 = new KeyboardButton();
                button3.setText("سرچ کتاب");
                button3.setRequestContact(false);
                button3.setRequestLocation(false);
                row2.add(button3);

                KeyboardButton button4 = new KeyboardButton();
                button4.setText("پروفایل");
                button4.setRequestContact(false);
                button4.setRequestLocation(false);
                row2.add(button4);

                keyboardRows.add(row1);
                keyboardRows.add(row2);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                //---------------------------------—
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
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("انصراف");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);

                row.add(button1);
                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                try {
                    //sendMessage(addBook_getBookName(update));
                    sendMessage.setText(addBook_getBookName(update).getText());
                    sendMessage(sendMessage);
                    dbHelper.changeState(chatId, 4);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4:
            {
                bookName = update.getMessage().getText();
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                List<KeyboardRow> keyboardRows = new ArrayList<>();
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

                try {
                    //sendMessage(addBook_getWriterName(update));
                    sendMessage.setText(addBook_getWriterName(update).getText());
                    sendMessage(sendMessage);
                    dbHelper.changeState(chatId, 5);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5:
            {
                writerName = update.getMessage().getText();

                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                List<KeyboardRow> keyboardRows = new ArrayList<>();
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

                try {
                    //sendMessage(addBook_getPublisher(update));
                    sendMessage.setText(addBook_getPublisher(update).getText());
                    sendMessage(sendMessage);
                    dbHelper.changeState(chatId, 6);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 6:
            {
                publisher = update.getMessage().getText();

                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                List<KeyboardRow> keyboardRows = new ArrayList<>();
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

                try {
                    //sendMessage(addBook_getPrice(update));

                    sendMessage.setText(addBook_getPrice(update).getText());
                    sendMessage(sendMessage);

                    dbHelper.changeState(chatId, 7);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 7:
            {
                price = update.getMessage().getText();

                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                List<KeyboardRow> keyboardRows = new ArrayList<>();
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


                System.out.println(bookName + " --- "+ writerName + "---- "+ publisher + " ----- "+ price);

                dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price));
                sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");

                try {
                    sendMessage(sendMessage);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            default: break; // if not found state
        }


    }

    public SendMessage addBook_getBookName (Update update)
    {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("برای معرفی کتاب لطفا اطلاعات زیر را وارد کنید. \n" +
                "نام کتاب:");
        return sendMessage;
    }

    public SendMessage addBook_getWriterName (Update update)
    {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("نام نویسنده:");
        return sendMessage;
    }

    public SendMessage addBook_getPublisher (Update update)
    {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("نام ناشر:");
        return sendMessage;
    }
    public SendMessage addBook_getPrice (Update update)
    {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("قیمت:");
        return sendMessage;
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

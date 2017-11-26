package com.company;

import org.apache.commons.io.FileUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.*;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class stateLike extends TelegramLongPollingBot
{
    public static String bookName, writerName, publisher ,price;
    public int idViewBook ;
    DBHelper dbHelper;
    public stateLike ()
    {
        //dbHelper = new DBHelper();
        dbHelper = DBHelper.getDbHelper();
    }

    public void image (Update update) throws TelegramApiException, IOException
    {
        System.out.println("in image method ...");
        PhotoSize photo1 ;
        photo1 = update.getMessage().getPhoto().get(0);
        System.out.println("photo id: "+ photo1.getFileId());
        System.out.println("photo file path" +photo1.getFilePath());
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId());
        sendPhoto.setPhoto(photo1.getFileId());

        sendPhoto(sendPhoto);

        //GetFile getFile = new GetFile();
        //getFile.setFileId(photo1.getFileId());

    }


    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        System.out.println("message is: " + message);
        Long chatId = update.getMessage().getChatId();
        //create new DBHelper

        //check the input:
        int state = dbHelper.checkId(chatId);

        if (state == -1) {
            dbHelper.newState(chatId);
            state = 1;
        }


        if(update.getMessage().getPhoto() != null){
            dbHelper.changeState(chatId ,10);
        }
        else if (message.equals("/start")) {
            dbHelper.changeState(chatId, 1);
        } else if (message.equals("امکانات") || message.equals("بازگشت به صفحه اصلی")) {
            dbHelper.changeState(chatId, 2);
        } else if (message.equals("انصراف")) {
            dbHelper.changeState(chatId, 1);
        } else if (message.equals("معرفی کتاب")) {
            dbHelper.changeState(chatId, 3);
        } else if (message.equals("مشاهده همه کتابها")) {
            dbHelper.changeState(chatId, 8);
        } else if (message.equals("بله")){
            dbHelper.changeState(chatId, 9);
        } else if (message.equals("خیر")){
            dbHelper.changeState(chatId, 11);
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
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("امکانات");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);
                row.add(button1);

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("ثبت نام");
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
                button2.setText("مشاهده همه کتابها");
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
                button1.setText("بازگشت به صفحه اصلی");
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
                button2.setText("بازگشت به صفحه اصلی");
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
                button2.setText("بازگشت به صفحه اصلی");
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
                button2.setText("بازگشت به صفحه اصلی");
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

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("بله");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);
                row.add(button1);

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("خیر");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);
                row.add(button2);
                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                System.out.println(bookName + " --- "+ writerName + "---- "+ publisher + " ----- "+ price);

                //dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price));
                //sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");
                sendMessage.setText("آیا مایلید برای کتابی که معرفی کرده اید عکسی هم آپلود کنید؟");

                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }

            case 8: //view books
            {
                System.out.println("first of case 4");
                boolean hasImage = false;
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                SendPhoto sendPhoto = new SendPhoto().setChatId(update.getMessage().getChatId());
                if (message.equals("مشاهده همه کتابها")) {

                    //button
                    System.out.println( "before show buttons");
                    List<KeyboardRow> keyboardRows = new ArrayList<>();
                    //List<List<KeyboardRow» keyboardRows = new ArrayList<😠);
                    KeyboardRow row = new KeyboardRow();

                    KeyboardButton button1 = new KeyboardButton();
                    button1.setText("بعدی");
                    button1.setRequestContact(false);
                    button1.setRequestLocation(false);
                    row.add(button1);

                    KeyboardButton button2 = new KeyboardButton();
                    button2.setText("قبلی");
                    button2.setRequestContact(false);
                    button2.setRequestLocation(false);
                    row.add(button2);

                    KeyboardRow row2 = new KeyboardRow();
                    KeyboardButton button3 = new KeyboardButton();
                    button3.setText("بازگشت به صفحه اصلی");
                    button3.setRequestContact(false);
                    button3.setRequestLocation(false);
                    row2.add(button3);

                    keyboardRows.add(row);
                    keyboardRows.add(row2);

                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setKeyboard(keyboardRows);

                    sendMessage.setReplyMarkup(replyKeyboardMarkup);

                    idViewBook =1;
                    BookInfo bookInfo = dbHelper.getBook(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                    System.out.println("in state 8 -- view book -- id :" +idViewBook);

                    sendMessage.setText(

                             "شماره کتاب:"+idViewBook+
                                   "\n" +
                            "نام کتاب: " + bookInfo.getBookName()+
                            "\nنام نویسنده: " + bookInfo.getWriterName()+
                            "\nناشر: " + bookInfo.getPublisher()+
                            "\nقیمت: " + bookInfo.getPrice());
                    if (!bookInfo.getImageID().equals("0"))
                    {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    }
                    else {
                        // need to save a photo for books dont have photo !!!!
                    }

                    System.out.println("message in replymethod : " + message);
                    //idViewBook =1;
                    //BookInfo bookInfo = dbHelper.getBook(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                }

                else if (message.equals("قبلی"))

                {
                    idViewBook--;
                    BookInfo bookInfo = dbHelper.getBook(idViewBook);
                   // sendMessage.setText(bookInfo.getBookName() + "\n"+ bookInfo.getWriterName() +"\n"+ bookInfo.getPublisher() +"\n" + bookInfo.getPrice());
                    sendMessage.setText("شماره کتاب : "+ idViewBook +"\n"+ "نام کتاب: " +bookInfo.getBookName()+"\n" +"نام نویسنده: "+ bookInfo.getWriterName() +"\n"+ "نام ناشر: "+ bookInfo.getPublisher() +"\n" +"قیمت : "+ bookInfo.getPrice());
                    if (!bookInfo.getImageID().equals("0"))
                    {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    }
                    else {
                        // need to save a photo for books dont have photo !!!!
                    }
                }
                else if (message.equals("بعدی"))
                {
                    idViewBook = idViewBook +1;
                    System.out.println("in badi state .id:" + idViewBook);

                    BookInfo bookInfo = dbHelper.getBook( idViewBook);
                    sendMessage.setText("شماره کتاب : "+ idViewBook +"\n"+ "نام کتاب: " +bookInfo.getBookName()+"\n" +"نام نویسنده: "+ bookInfo.getWriterName() +"\n"+
                            "نام ناشر: "+ bookInfo.getPublisher() +"\n" +"قیمت : "+ bookInfo.getPrice());
                    //send photo of book
                    if (!bookInfo.getImageID().equals("0")) // has photo
                    {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    }
                    else {
                        // need to save a photo for books dont have photo !!!!
                    }

                }

                try {
                    sendMessage(sendMessage);
                    sendPhoto(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 9:
            {
                SendMessage sendMessage = new SendMessage().setChatId(chatId);
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button1 = new KeyboardButton();
                button1.setText("خیر");
                button1.setRequestContact(false);
                button1.setRequestLocation(false);
                row.add(button1);

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("بازگشت به صفحه اصلی");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);

                row.add(button2);
                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                sendMessage.setText("لطفا عکس مورد نظر خود را آپلود نمایید...");

                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                //dbHelper.changeState(update.getMessage().getChatId() , 10);
                break;
            }

            case 10: {
                System.out.println(" in cae 10.");
                SendMessage sendMessage = new SendMessage().setChatId(chatId);
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("بازگشت به صفحه اصلی");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);

                row.add(button2);
                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                System.out.println("get image from user");
                PhotoSize photo;
                photo = update.getMessage().getPhoto().get(0);
                System.out.println("image id : "+ photo.getFileId());
                String photoID = photo.getFileId();

                dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price), photoID);
                sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");

                try {
                    sendMessage(sendMessage);
                }catch (TelegramApiException e)
                {
                    e.printStackTrace();
                }

                break;
            }


            case 11:
            {
                SendMessage sendMessage = new SendMessage().setChatId(chatId);
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                KeyboardButton button2 = new KeyboardButton();
                button2.setText("بازگشت به صفحه اصلی");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);

                row.add(button2);
                keyboardRows.add(row);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(keyboardRows);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                String photoID = "0" ;
                System.out.println("user dont want to add image ... so photo id is 0");
                dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price) ,photoID);
                sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");
                //while (update.getMessage().getPhoto().size() == 0) ;

                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e)
                {
                    e.printStackTrace();
                }
                break;
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

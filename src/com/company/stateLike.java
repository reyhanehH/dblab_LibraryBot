package com.company;

import com.company.services.Emoji;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//import org.telegram.telegrambots.api.objects.File;

public class stateLike extends TelegramLongPollingBot
{
    public static String bookName, writerName, publisher ,price , userName;
    public int idViewBook ,priceInt;
    ArrayList <BookInfo> bookLikeArray = new ArrayList<>( ) ;
    DBHelper dbHelper;
    int arrayViewIndex = 0 ;
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

        if (update.getMessage().getPhoto() != null) {
            dbHelper.changeState(chatId, 10);
        } else if (message.equals("/start")) {
            dbHelper.changeState(chatId, 1);
        } else if (message.equals("امکانات") || message.equals("بازگشت به صفحه اصلی")) {
            dbHelper.changeState(chatId, 2);
        } else if (message.equals("انصراف")) {
            dbHelper.changeState(chatId, 1);
        } else if (message.equals("معرفی کتاب")) {
            dbHelper.changeState(chatId, 3);
        } else if (message.equals("مشاهده همه کتابها")) {
            dbHelper.changeState(chatId, 8);
        } else if (message.equals("بله")) {
            dbHelper.changeState(chatId, 9);
        } else if (message.equals("خیر")) {
            dbHelper.changeState(chatId, 11);
        } else if (message.equals("سرچ کتاب")) {
            dbHelper.changeState(chatId, 12);
        } else if (message.equals("پروفایل")) {
            dbHelper.changeState(chatId, 14);
        } else if (message.equals("مشاهده ی علاقه مندی ها")) {
            dbHelper.changeState(chatId, 15);
        } else if (message.equals("ثبت نام")) {
            dbHelper.changeState(chatId, 16);
        } //else if (message.equals("افزودن به لیست علاقه مندی")) {
           // dbHelper.changeState(chatId, 18);
       // }



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
                sendMessage.setText("به بات کتابخانه خوش آمدید \n برای دیدن امکانات این بات کلید امکانات را فشار دهید. " + Emoji.SMILING_FACE_WITH_OPEN_MOUTH);
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
            case 4: {
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
            case 5: {
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
            case 6: {
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

                    try {
                        priceInt = Integer.parseInt(price);

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

                        System.out.println(bookName + " --- " + writerName + "---- " + publisher + " ----- " + price);

                        //dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price));
                        //sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");
                        try {
                            sendMessage.setText("آیا مایلید برای کتابی که معرفی کرده اید عکسی هم آپلود کنید؟");
                            sendMessage(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    catch (NumberFormatException e)
                    {

                        dbHelper.changeState(chatId, 7);
                        e.printStackTrace();
                        sendMessage.setText("لطفا قیمت را فقط به عدد وارد کنید.");
                        try {
                            sendMessage(sendMessage);
                        }catch (TelegramApiException e1){
                            e1.printStackTrace();
                        }
                    }
                    break;
            }

            case 8: //view books
            {
                System.out.println("first of case 8");
                boolean hasImage = false;
                int Count_book_test_table = dbHelper.getCount_book_test_table() ;
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                SendPhoto sendPhoto = new SendPhoto().setChatId(update.getMessage().getChatId());
                if (message.equals("مشاهده همه کتابها")) {

                    //button
                    System.out.println("before show buttons");
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

                    KeyboardButton button4 = new KeyboardButton();
                    button4.setText("افزودن به لیست علاقه مندی");
                    button4.setRequestContact(false);
                    button4.setRequestLocation(false);
                    row2.add(button4);

                    keyboardRows.add(row);
                    keyboardRows.add(row2);

                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setKeyboard(keyboardRows);

                    sendMessage.setReplyMarkup(replyKeyboardMarkup);

                    idViewBook = 1;
                    BookInfo bookInfo = dbHelper.getBook(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                    System.out.println("in state 8 -- view book -- id :" + idViewBook);

                    sendMessage.setText(

                            "شماره کتاب:" + idViewBook +
                                    "\n" +
                                    "نام کتاب: " + bookInfo.getBookName() +
                                    "\nنام نویسنده: " + bookInfo.getWriterName() +
                                    "\nناشر: " + bookInfo.getPublisher() +
                                    "\nقیمت: " + bookInfo.getPrice());
                    if (!bookInfo.getImageID().equals("0")) {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    } else {
                        // need to save a photo for books dont have photo !!!!
                    }

                    System.out.println("message in replymethod : " + message);
                    //idViewBook =1;
                    //BookInfo bookInfo = dbHelper.getBook(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                } else if (message.equals("قبلی"))

                {
                    idViewBook--;
                    if( idViewBook > 0 )
                    {
                        BookInfo bookInfo = dbHelper.getBook(idViewBook);
                        // sendMessage.setText(bookInfo.getBookName() + "\n"+ bookInfo.getWriterName() +"\n"+ bookInfo.getPublisher() +"\n" + bookInfo.getPrice());
                        sendMessage.setText("شماره کتاب : " + idViewBook + "\n" + "نام کتاب: " + bookInfo.getBookName() + "\n" + "نام نویسنده: " + bookInfo.getWriterName() + "\n" + "نام ناشر: " + bookInfo.getPublisher() + "\n" + "قیمت : " + bookInfo.getPrice());
                        if (!bookInfo.getImageID().equals("0")) {
                            sendPhoto.setPhoto(bookInfo.getImageID());
                            hasImage = true;
                        } else {
                            // need to save a photo for books dont have photo !!!!
                        }
                    }
                    else
                    {
                        sendMessage.setText("به ابتدای لیست رسیدیم. برای مشاهده ی بقیه ی کتاب ها گزینه ی 'بعدی' رو بزنید.");
                    }

                } else if (message.equals("بعدی"))
                {
                    idViewBook = idViewBook + 1;

                    if (idViewBook > Count_book_test_table )
                    {
                      sendMessage.setText("لیست کتاب ها به انتها رسید. برای مشاهده ی بقیه کتاب ها لطفا گزینه ی 'قبلی 'رو بزنید .");
                    }
                    else {


                        System.out.println("in badi state .id:" + idViewBook);

                        BookInfo bookInfo = dbHelper.getBook(idViewBook);
                        sendMessage.setText("شماره کتاب : " + idViewBook + "\n" + "نام کتاب: " + bookInfo.getBookName() + "\n" + "نام نویسنده: " + bookInfo.getWriterName() + "\n" +
                                "نام ناشر: " + bookInfo.getPublisher() + "\n" + "قیمت : " + bookInfo.getPrice());
                        //send photo of book
                        if (!bookInfo.getImageID().equals("0")) // has photo
                        {
                            sendPhoto.setPhoto(bookInfo.getImageID());
                            hasImage = true;
                        } else {
                            // need to save a photo for books dont have photo !!!!
                        }
                    }
                }
                else if (message.equals("افزودن به لیست علاقه مندی"))
                {
                    System.out.println("befor dbHelper.checkLikeBook");
                    boolean result =  dbHelper.checkLikeBook (chatId , idViewBook);
                    System.out.println("after dbHelper.checkLikeBook" + " result = " + result );
                    if (result == false)
                    {
                        dbHelper.addLikeBook (chatId , idViewBook);
                        sendMessage.setText("این کتاب باموفقیت به لیست علاقه مندی ها افزوده شد.");
                    }
                    else
                    {
                        sendMessage.setText("این کتاب قبلا به لیست علاقه مندی ها افزوده شده بود.");
                    }

                }

                try {
                    sendMessage(sendMessage);
                    if (hasImage) {
                        sendPhoto(sendPhoto);
                        hasImage = false;
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 9: {
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
                System.out.println("image id : " + photo.getFileId());
                String photoID = photo.getFileId();

                //dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price), photoID);
                String result = dbHelper.insertBook_stored(bookName, writerName, publisher, priceInt, photoID);
                sendMessage.setText(result);
                //sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");

                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                break;
            }


            case 11: {
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

                String photoID = "0";
                System.out.println("user dont want to add image ... so photo id is 0");
                //dbHelper.addBook(bookName, writerName, publisher, Integer.parseInt(price) ,photoID);
                //sendMessage.setText("کتاب شما با موفقیت ذخیره شد. :)");
                String result = dbHelper.insertBook_stored(bookName, writerName, publisher, priceInt, photoID);
                sendMessage.setText(result);

                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 12: {
                System.out.println("in search book case");
                SendMessage sendMessage = new SendMessage().setChatId(chatId);

                if (message.equals("سرچ کتاب")) {
                    List<KeyboardRow> keyboardRows = new ArrayList<>();
                    KeyboardRow row1 = new KeyboardRow();

                    KeyboardButton button1 = new KeyboardButton();
                    button1.setText("نام کتاب");
                    button1.setRequestLocation(false);
                    button1.setRequestContact(false);

                    KeyboardButton button2 = new KeyboardButton();
                    button2.setText("نام نویسنده");
                    button2.setRequestLocation(false);
                    button2.setRequestContact(false);

                    KeyboardButton button3 = new KeyboardButton();
                    button3.setText("نام ناشر");
                    button3.setRequestLocation(false);
                    button3.setRequestContact(false);

                    KeyboardButton button4 = new KeyboardButton();
                    button4.setText("محدوده قیمت");
                    button4.setRequestLocation(false);
                    button4.setRequestContact(false);

                    row1.add(button1);
                    row1.add(button2);
                    row1.add(button3);
                    row1.add(button4);

                    KeyboardRow row3 = new KeyboardRow();
                    KeyboardButton button5 = new KeyboardButton();
                    button5.setText("بازگشت به صفحه اصلی");
                    button5.setRequestContact(false);
                    button5.setRequestLocation(false);

                    row3.add(button5);
                    keyboardRows.add(row1);
                    keyboardRows.add(row3);

                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setKeyboard(keyboardRows);

                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                    sendMessage.setText("مایلید براساس کدام یک از گزینه های زیر جستجو را انچام دهید: ");
                } else if (message.equals("نام کتاب")) {
                    sendMessage.setText("لطفا نام کتاب مورد نظر خود را وارد کنید:");
                    dbHelper.changeState(chatId, 13);
                } else if (message.equals("نام نویسنده")) {
                    sendMessage.setText("لطفا نام نویسنده مورد نظر خود را وارد کنید:");
                    dbHelper.changeState(chatId, 14);
                } else if (message.equals("نام ناشر")) {
                    sendMessage.setText("لطفا نام ناشر مورد نظر خود را وارد کنید:");
                    dbHelper.changeState(chatId, 15);
                } else if (message.equals("محدوده قیمت")) {
                    sendMessage.setText("لطفا محدوده قیمت مورد نظر خود را انتخاب کنید:");
                    List<KeyboardRow> keyboardRows = new ArrayList<>();
                    KeyboardRow row1 = new KeyboardRow();

                    KeyboardButton button1 = new KeyboardButton();
                    button1.setText("از 0 تا 10000 تومان");
                    button1.setRequestLocation(false);
                    button1.setRequestContact(false);

                    KeyboardButton button2 = new KeyboardButton();
                    button2.setText("از 10000 تا 20000 تومان");
                    button2.setRequestLocation(false);
                    button2.setRequestContact(false);

                    KeyboardButton button3 = new KeyboardButton();
                    button3.setText("از 20000 تا 50000 تومان");
                    button3.setRequestLocation(false);
                    button3.setRequestContact(false);

                    KeyboardButton button4 = new KeyboardButton();
                    button4.setText("بیشتر از 50000 تومان");
                    button4.setRequestLocation(false);
                    button4.setRequestContact(false);

                    row1.add(button1);
                    row1.add(button2);
                    row1.add(button3);
                    row1.add(button4);

                    KeyboardRow row3 = new KeyboardRow();
                    KeyboardButton button5 = new KeyboardButton();
                    button5.setText("بازگشت به صفحه اصلی");
                    button5.setRequestContact(false);
                    button5.setRequestLocation(false);

                    row3.add(button5);
                    keyboardRows.add(row1);
                    keyboardRows.add(row3);

                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setKeyboard(keyboardRows);

                    sendMessage.setReplyMarkup(replyKeyboardMarkup);

                }
                try {
                    sendMessage(sendMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 13: // search as book name
            {
                ArrayList<BookInfo> bookInfos = dbHelper.search_bookName(message);
                SendMessage sendMessage = new SendMessage().setChatId(chatId);
                //sendMessage.setText("نتایج به دست آمده:"+bookInfos.size());
                boolean hasImage = false;
                SendPhoto sendPhoto = new SendPhoto().setChatId(update.getMessage().getChatId());
                if (!message.equals("بعدی") || !message.equals("قبلی")) {

                    //button
                    System.out.println("before show buttons");
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

                    idViewBook = 0;
                    BookInfo bookInfo = bookInfos.get(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                    System.out.println("in state 8 -- view book -- id :" + idViewBook);

                    sendMessage.setText(

                            "شماره کتاب:" + idViewBook +
                                    "\n" +
                                    "نام کتاب: " + bookInfo.getBookName() +
                                    "\nنام نویسنده: " + bookInfo.getWriterName() +
                                    "\nناشر: " + bookInfo.getPublisher() +
                                    "\nقیمت: " + bookInfo.getPrice());
                    if (!bookInfo.getImageID().equals("0")) {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    } else {
                        // need to save a photo for books dont have photo !!!!
                    }

                    System.out.println("message in replymethod : " + message);
                    //idViewBook =1;
                    //BookInfo bookInfo = dbHelper.getBook(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                } else if (message.equals("قبلی")) {
                    idViewBook--;
                    BookInfo bookInfo = bookInfos.get(idViewBook);
                    // sendMessage.setText(bookInfo.getBookName() + "\n"+ bookInfo.getWriterName() +"\n"+ bookInfo.getPublisher() +"\n" + bookInfo.getPrice());
                    sendMessage.setText("شماره کتاب : " + idViewBook + "\n" + "نام کتاب: " + bookInfo.getBookName() + "\n" + "نام نویسنده: " + bookInfo.getWriterName() + "\n" + "نام ناشر: " + bookInfo.getPublisher() + "\n" + "قیمت : " + bookInfo.getPrice());
                    if (!bookInfo.getImageID().equals("0")) {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    } else {
                        // need to save a photo for books dont have photo !!!!
                    }
                } else if (message.equals("بعدی")) {
                    idViewBook = idViewBook + 1;
                    System.out.println("in badi state .id:" + idViewBook);

                    BookInfo bookInfo = bookInfos.get(idViewBook);
                    sendMessage.setText("شماره کتاب : " + idViewBook + "\n" + "نام کتاب: " + bookInfo.getBookName() + "\n" + "نام نویسنده: " + bookInfo.getWriterName() + "\n" +
                            "نام ناشر: " + bookInfo.getPublisher() + "\n" + "قیمت : " + bookInfo.getPrice());
                    //send photo of book
                    if (!bookInfo.getImageID().equals("0")) // has photo
                    {
                        sendPhoto.setPhoto(bookInfo.getImageID());
                        hasImage = true;
                    } else {
                        // need to save a photo for books dont have photo !!!!
                    }

                }
                try {
                    sendMessage(sendMessage);
                    if (hasImage)
                        sendPhoto(sendPhoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            case 14: //profile
            {
                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

                List<KeyboardRow> keyboardRows = new ArrayList<>();

                KeyboardRow row2 = new KeyboardRow();
                KeyboardButton button2 = new KeyboardButton();
                button2.setText("مشاهده ی علاقه مندی ها");
                button2.setRequestContact(false);
                button2.setRequestLocation(false);
                row2.add(button2);
                //keyboardRows.add(row2);

                KeyboardButton button3 = new KeyboardButton();
                button3.setText("ثبت نام");
                button3.setRequestContact(false);
                button3.setRequestLocation(false);
                row2.add(button3);
                keyboardRows.add(row2);


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

                boolean result = dbHelper.checkRegistering(chatId);
                if(result == true)
                {
                    Profile prof = dbHelper.get_userName(chatId);
                    System.out.println("user name is :");
                    sendMessage.setText(prof.get_user_name() + " عزیز ،" + " به پروفایل خودت خوش اومدی :)");
                }
                else
                {
                    sendMessage.setText(  "شما قبلا ثبت نام نکرده اید."
                            + "\n"
                            + "لطفا گزینه ی ثبت نام را انتخاب کنید."

                    );
                }
                try {
                    sendMessage(sendMessage);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 15: //view book_like
            {
                System.out.println("first of case 15");
                boolean hasImage = false;

                int Count_bookliketable = dbHelper.getCount_bookliketable(chatId);

                SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                SendPhoto sendPhoto = new SendPhoto().setChatId(update.getMessage().getChatId());
                if (message.equals("مشاهده ی علاقه مندی ها")) {

                    //button
                    System.out.println("before show buttons");


                    List<KeyboardRow> keyboardRows = new ArrayList<>();


                    KeyboardRow row1 = new KeyboardRow();

                    KeyboardButton button1 = new KeyboardButton();
                    button1.setText("بعدی");
                    button1.setRequestContact(false);
                    button1.setRequestLocation(false);
                    row1.add(button1);

                    KeyboardButton button2 = new KeyboardButton();
                    button2.setText("قبلی");
                    button2.setRequestContact(false);
                    button2.setRequestLocation(false);
                    row1.add(button2);
                    keyboardRows.add(row1);


                    KeyboardRow row2 = new KeyboardRow();
                    KeyboardButton button3 = new KeyboardButton();
                    button3.setText("بازگشت به صفحه اصلی");
                    button3.setRequestContact(false);
                    button3.setRequestLocation(false);
                    row2.add(button3);
                    keyboardRows.add(row2);


                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    replyKeyboardMarkup.setKeyboard(keyboardRows);
                    sendMessage.setReplyMarkup(replyKeyboardMarkup);


                     bookLikeArray  = dbHelper.get_book_like(chatId);

                    System.out.println("in state 8 -- view book -- id :" + arrayViewIndex );

                    sendMessage.setText(

                            "شماره کتاب:"  + arrayViewIndex +
                                    "\n" +
                                    "نام کتاب: " + bookLikeArray.get(arrayViewIndex).getBookName() +
                                    "\nنام نویسنده: " + bookLikeArray.get(arrayViewIndex).getWriterName() +
                                    "\nناشر: " + bookLikeArray.get(arrayViewIndex).getPublisher() +
                                    "\nقیمت: " + bookLikeArray.get(arrayViewIndex).getPrice());
                    if (!bookLikeArray.get(arrayViewIndex).getImageID().equals("0")) {
                        sendPhoto.setPhoto(bookLikeArray.get(arrayViewIndex).getImageID());
                        hasImage = true;



                    } else {
                        // need to save a photo for books dont have photo !!!!
                    }

                    System.out.println("message in replymethod : " + message);
                    //idViewBook =1;
                    //BookInfo bookInfo = dbHelper.getBook(idViewBook);
                    //sendMessage.setText(bookInfo.getBookName() + "/n"+ bookInfo.getWriterName() +"/n"+ bookInfo.getPublisher() +"/n" + bookInfo.getPrice());
                } else if (message.equals("قبلی"))
                {
                    arrayViewIndex = arrayViewIndex -1;
                    if (arrayViewIndex  >= 0)
                    {
                        sendMessage.setText("شماره کتاب : " + arrayViewIndex + "\n" + "نام کتاب: " + bookLikeArray.get(arrayViewIndex).getBookName() + "\n" + "نام نویسنده: " + bookLikeArray.get(arrayViewIndex).getWriterName() + "\n" + "نام ناشر: " + bookLikeArray.get(arrayViewIndex).getPublisher() + "\n" + "قیمت : " + bookLikeArray.get(arrayViewIndex).getPrice());
                        if (!bookLikeArray.get(arrayViewIndex).getImageID().equals("0")) {
                            sendPhoto.setPhoto(bookLikeArray.get(arrayViewIndex).getImageID());
                            hasImage = true;
                        } else {
                            // need to save a photo for books dont have photo !!!!
                        }
                    }
                    else
                    {
                        sendMessage.setText("به ابتدای لیست رسیدیم. برای مشاهده ی بقیه ی کتاب ها گزینه ی 'بعدی' رو بزنید.");
                    }


                } else if (message.equals("بعدی"))
                {
                        arrayViewIndex = arrayViewIndex + 1 ;
                        if (arrayViewIndex < Count_bookliketable)
                        {
                            System.out.println("in badi state .id:" + arrayViewIndex);

                            // BookInfo bookInfo = dbHelper.getBook(dbHelper.idview[arrayViewIndex]);
                            sendMessage.setText("شماره کتاب : " + arrayViewIndex + "\n" + "نام کتاب: " + bookLikeArray.get(arrayViewIndex).getBookName() + "\n" + "نام نویسنده: " + bookLikeArray.get(arrayViewIndex).getWriterName() + "\n" +
                                    "نام ناشر: " + bookLikeArray.get(arrayViewIndex).getPublisher() + "\n" + "قیمت : " + bookLikeArray.get(arrayViewIndex).getPrice());
                            //send photo of book
                            if (!bookLikeArray.get(arrayViewIndex).getImageID().equals("0")) // has photo
                            {
                                sendPhoto.setPhoto(bookLikeArray.get(arrayViewIndex).getImageID());
                                hasImage = true;
                            } else {
                                // need to save a photo for books dont have photo !!!!
                            }
                        }
                        else
                        {
                            sendMessage.setText("به انتهای لیست رسیدیم. برای مشاهده ی بقیه ی کتاب ها لطفا گزینه ی 'قبلی' رو بزنید.");
                        }

                }

                try {
                    sendMessage(sendMessage);
                    if (hasImage)
                        sendPhoto(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 16 : // register
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

                try {
                    sendMessage.setText(get_userName(update).getText() );
                    sendMessage(sendMessage);
                    dbHelper.changeState(chatId , 17);



                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;


            }
            case 17 : // send information to DB_Helper for registering
            {
                userName = update.getMessage().getText();
//                bookName = update.getMessage().getText();
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

                System.out.println("befor DB Herper");
                boolean result =  dbHelper.checkRegistering(chatId);
                if (result == false)
                {
                    dbHelper.add_userName(chatId , userName);
                    System.out.println("in if == false  ,  after DB Herper");
                    sendMessage.setText("ثبت نام شما با موفقیت انجام شد. :)") ;
                    System.out.println("in if == false  ,  after ثبت نام شما با موفقیت انجام شد. ");
                }
                else
                {
                    sendMessage.setText("شما قبلا ثبت نام کرده اید. :)") ;
                    System.out.println("in if == true  ,  after ثبت نام قبلا انجام شد.. ");
                }

                try {
                    sendMessage(sendMessage);


                } catch (TelegramApiException e) {
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
        sendMessage.setText("قیمت:\n" + Emoji.PENCIL + "لطفا قیمت را به تومان وارد نمایید\n"+ Emoji.PENCIL +"لطفا قیمت را به عدد وارد نمایید" );
        return sendMessage;
    }
    public SendMessage get_userName (Update update)
    {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("برای ثبت نام لطفا نام خود را وارد کنید: " );

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
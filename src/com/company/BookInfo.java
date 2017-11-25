package com.company;

public class BookInfo
{
    private String bookName ,writerName, publisher ,imageID;
    private int price;

    public BookInfo (String bookName ,String writerName ,String publisher , int price ,String imageID)
    {
        this.bookName = bookName;
        this.writerName = writerName;
        this.publisher = publisher;
        this.price = price;
        this.imageID = imageID;
    }

    public int getPrice() {
        return price;
    }

    public String getBookName() {
        return bookName;
    }

    public String getWriterName() {
        return writerName;
    }

    public String getPublisher() {
        return publisher;
    }
    public String getImageID() {
        return imageID;
    }
}

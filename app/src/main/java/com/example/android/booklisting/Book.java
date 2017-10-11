package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Noura on 9/12/17.
 */

public class Book implements Parcelable {

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    private String author;
    private String title;
    private String publisher;


    public Book(String author, String title, String publisher) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;

    }

    protected Book(Parcel in) {
        author = in.readString();
        title = in.readString();
        publisher = in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(publisher);
    }

}

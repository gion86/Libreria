package com.android.library.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;

import java.text.SimpleDateFormat;
import java.util.Date;

// If your app requires very quick access to database information through full-text search (FTS),
// have your entities backed by a virtual table that uses either the FTS3 or FTS4 SQLite extension
// module. To use this capability, available in Room 2.1.0 and higher, add the @Fts3 or @Fts4
// annotation to a given entity.

// The single primary key field in an FTS entity must either be named 'rowid' or must be annotated
// with @ColumnInfo(name = "rowid").

@Fts4
@Entity(tableName = "book_table")
public class Book {
    private static final SimpleDateFormat DATE_PRINT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "read_date")
    private long readDate;

    public Book(String title, String author, long readDate) {
        this.title = title;
        this.author = author;
        this.readDate = readDate;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public long getReadDate() {
        return readDate;
    }

    public String getHumanReadDate() {
        return DATE_PRINT_FORMAT.format(new Date(readDate));
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReadDate(long readDate) {
        this.readDate = readDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "BOOK = " + title + ", " + author + ", " + getHumanReadDate();
    }
}

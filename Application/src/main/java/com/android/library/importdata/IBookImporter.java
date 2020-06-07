package com.android.library.importdata;

import com.android.library.model.Book;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface IBookImporter {

    public void importBooks(File file);

    public void importBooks(InputStream inputStream);

    public  List<Book> getBookList();
}

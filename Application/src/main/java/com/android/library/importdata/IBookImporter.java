package com.android.library.importdata;

import com.android.library.model.Book;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface IBookImporter {

    void importBooks(File file);

    void importBooks(InputStream inputStream);

    List<Book> getBookList();
}

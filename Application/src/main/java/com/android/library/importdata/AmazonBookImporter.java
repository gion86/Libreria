package com.android.library.importdata;

import com.android.library.model.Book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AmazonBookImporter implements IBookImporter {

    private List<Book> m_books;

    private void importBooks(InputStreamReader fileStreamReader) {
        try {
            BufferedReader br = new BufferedReader(fileStreamReader);
            int lineCounter = 0;
            String line, title = "", author = "", date = "";
            while ((line = br.readLine()) != null) {
                if (lineCounter == 0) {
                    title = line.trim();
                } else if (lineCounter == 1) {
                    author = line.trim();
                } else if (lineCounter == 2) {
                    date = line.trim();
                } else {
                    Book book = new Book(title, author, date);
                    m_books.add(book);
                    System.out.println(book);
                    lineCounter = 0;
                    continue;
                }
                lineCounter++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO You'll need to add proper error handling here
        }
    }

    public AmazonBookImporter() {
        m_books = new ArrayList<>();
    }

    @Override
    public void importBooks(File file) {
        try {
            importBooks(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importBooks(InputStream inputStream) {
        importBooks(new InputStreamReader(Objects.requireNonNull(inputStream)));
    }

    @Override
    public List<Book> getBookList() {
        return m_books;
    }
}

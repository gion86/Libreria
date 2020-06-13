package com.android.library.importdata;

import com.android.library.model.Book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AmazonBookImporter implements IBookImporter {
    private static final SimpleDateFormat DATE_PARSE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private List<Book> m_books;

    private long parseDate(String date) {
        String[] dates = date.split(" +");

        if (dates.length == 3) {
            int month = -1;
            switch (dates[1].toLowerCase()) {
                case "gennaio":
                    month = 1;
                    break;
                case "febbraio":
                    month = 2;
                    break;
                case "marzo":
                    month = 3;
                    break;
                case "aprile":
                    month = 4;
                    break;
                case "maggio":
                    month = 5;
                    break;
                case "giugno":
                    month = 6;
                    break;
                case "luglio":
                    month = 7;
                    break;
                case "agosto":
                    month = 8;
                    break;
                case "settembre":
                    month = 9;
                    break;
                case "ottobre":
                    month = 10;
                    break;
                case "novembre":
                    month = 11;
                    break;
                case "dicembre":
                    month = 12;
                    break;
            }

            try {
                Date date2 = DATE_PARSE_FORMAT.parse(dates[2] + "-" + month + "-" + dates[0]);
                return date2.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    private void importBooks(InputStreamReader fileStreamReader) {
        try {
            BufferedReader br = new BufferedReader(fileStreamReader);
            int lineCounter = 0;
            String line, title = "", author = "";
            long date = -1;
            while ((line = br.readLine()) != null) {
                if (lineCounter == 0) {
                    title = line.trim();
                } else if (lineCounter == 1) {
                    author = line.trim();
                } else if (lineCounter == 2) {
                    date = parseDate(line.trim());
                } else {
                    Book book = new Book(title, author, date);
                    m_books.add(book);
                    lineCounter = 0;
                    continue;
                }
                lineCounter++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
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

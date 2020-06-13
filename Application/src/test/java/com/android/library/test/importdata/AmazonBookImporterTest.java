package com.android.library.test.importdata;

import com.android.library.importdata.AmazonBookImporter;
import com.android.library.importdata.IBookImporter;
import com.android.library.model.Book;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class AmazonBookImporterTest {

    private List<Book> m_bookList;

    @Before
    public void importBooks() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL fileUrl = classLoader.getResource("amazon_book_list.txt");
        assertNotNull(fileUrl);
        File bookFile = new File(fileUrl.getFile());
        assertTrue(bookFile.exists());

        IBookImporter bookImporter = new AmazonBookImporter();
        bookImporter.importBooks(bookFile);
        m_bookList = bookImporter.getBookList();
    }

    @Test
    public void testImportedList() {
        for (Book book: m_bookList) {
            System.out.println(book);
        }
        assertTrue(m_bookList.size() == 266);
    }

    @Test
    public void testDates() {
        long date0 = m_bookList.get(0).getReadDate();
        System.out.println("Date0 = " + date0);
        assertTrue(date0 == 1590271200000L);

        long date5 = m_bookList.get(5).getReadDate();
        System.out.println("Date5 = " + date5);
        assertTrue(date5 == 1587592800000L);
    }
}
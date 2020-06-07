package com.android.library.test.importdata;

import com.android.library.importdata.AmazonBookImporter;
import com.android.library.importdata.IBookImporter;

import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

public class AmazonBookImporterTest {

    @Test
    public void importBooks() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File bookFile = new File(classLoader.getResource("amazon_book_list.txt").getFile());
        assertTrue(bookFile.exists());

        IBookImporter bookImporter = new AmazonBookImporter();
        bookImporter.importBooks(bookFile);
        assertTrue(bookImporter.getBookList().size() == 266);
    }
}
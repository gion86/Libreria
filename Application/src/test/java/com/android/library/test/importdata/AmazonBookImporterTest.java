package com.android.library.test.importdata;

import com.android.library.importdata.AmazonBookImporter;
import com.android.library.importdata.IBookImporter;

import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

public class AmazonBookImporterTest {

    @Test
    public void importBooks() {
        IBookImporter bookImporter = new AmazonBookImporter();
        // TODO move test file in data folder?
        bookImporter.importBooks(new File("/home/gionata/Desktop/amazon_book_list.txt"));

        assertTrue(bookImporter.getBookList().size() == 266);
    }
}
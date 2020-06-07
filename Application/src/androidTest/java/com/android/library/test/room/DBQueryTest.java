package com.android.library.test.room;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.library.importdata.AmazonBookImporter;
import com.android.library.importdata.IBookImporter;
import com.android.library.model.Book;
import com.android.library.model.BookDao;
import com.android.library.model.BookRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * DB Query test cases
 */
@RunWith(AndroidJUnit4.class)
public class DBQueryTest {

    // To use LiveData in a test setup requires the core-testing library and InstantTaskExecutorRule
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BookDao mBookDao;
    private BookRoomDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(context, BookRoomDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mBookDao = mDb.bookDao();
    }

    @Before
    public void fillDb() {
        InputStream is = getClass().getResourceAsStream("/amazon_book_list.txt");

        IBookImporter bookImporter = new AmazonBookImporter();
        bookImporter.importBooks(is);
        assertTrue(bookImporter.getBookList().size() == 266);

        for (Book book : bookImporter.getBookList()) {
            mBookDao.insert(book);
        }
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void singleTitleQuery() {
        Book book = mBookDao.findByTitle("Il profumo delle rose");

        assertNotNull(book);
        assertTrue(book.getTitle().equals("Il profumo delle rose"));
        assertTrue(book.getAuthor().equals("Victoria Connelly"));
        assertTrue(book.getReadDate() == 1589148000000L);
    }

    /**
     * Full text search query
     */
    @Test
    public void ftsQuery() throws Exception {
        List<Book> allBooks = LiveDataTestUtil.getValue(mBookDao.findWithFTS("ragazza"));

        assertNotNull(allBooks);
        assertTrue(allBooks.size() == 9);

        for (Book book: allBooks) {
            System.out.println(book);
        }
    }
}

package com.android.library.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {

    private BookDao m_bookDao;
    private LiveData<List<Book>> m_allBooks;

    public BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        m_bookDao = db.bookDao();
        m_allBooks = m_bookDao.getAllReadDataSorted();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Book>> getAllBooks() {
        return m_allBooks;
    }

    public void insert(Book book) {
        BookRoomDatabase.s_databaseWriteExecutor.execute(() -> m_bookDao.insert(book));
    }

    public void insertIfNotExist(Book book) {
        BookRoomDatabase.s_databaseWriteExecutor.execute(() -> {
            Book foundBook = m_bookDao.findByTitle(book.getTitle());

            if (foundBook == null) {
                m_bookDao.insert(book);
            }
        });
    }

    public LiveData<List<Book>> find(String term) {
        String wildcardQuery = String.format("*%s*", term);
        return m_bookDao.findWithFTS(wildcardQuery);
    }
}
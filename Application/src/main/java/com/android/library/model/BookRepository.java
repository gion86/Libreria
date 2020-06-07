package com.android.library.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {

    private BookDao m_bookDao;
    private LiveData<List<Book>> m_allBooks;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
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

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    // TODO Davvero?? Lo fa l'Executor......
    public void insert(Book book) {
        BookRoomDatabase.s_databaseWriteExecutor.execute(() -> {
            m_bookDao.insert(book);
        });
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
        return m_bookDao.findWithFTS("%" + term + "%");
        //return m_bookDao.find(term);
//      TODO  BookRoomDatabase.s_databaseWriteExecutor.execute(() -> {
//        });
    }
}
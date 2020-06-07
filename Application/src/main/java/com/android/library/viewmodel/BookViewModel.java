package com.android.library.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.library.model.Book;
import com.android.library.model.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository m_repository;
    private LiveData<List<Book>> m_allBooks;

    public BookViewModel(Application application) {
        super(application);
        m_repository = new BookRepository(application);
        m_allBooks = m_repository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return m_allBooks;
    }

    public void insert(Book book) {
        m_repository.insert(book);
    }

    public void insertIfNotExist(Book book) {
        m_repository.insertIfNotExist(book);
    }

    public LiveData<List<Book>> find(String term) {
        return m_repository.find(term);
    }
}
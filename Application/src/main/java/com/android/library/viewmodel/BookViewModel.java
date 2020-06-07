package com.android.library.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.library.model.Book;
import com.android.library.model.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository m_repository;
    private LiveData<List<Book>> m_allBooks;
    private MutableLiveData<String> m_filterBooks;

    public BookViewModel(Application application) {
        super(application);
        m_repository = new BookRepository(application);

        // First time set an empty value to get all data
        m_filterBooks = new MutableLiveData<>("");

        m_allBooks = Transformations.switchMap(m_filterBooks, term -> {
            if (term == null || term.equals("") || term.equals("%%")) {
                // Check if the current value is empty load all data else search
                return  m_repository.getAllBooks();
            } else {
                System.out.println("CURRENT INPUT: " + term);
                return m_repository.find(term);
            }
        });
    }

    public LiveData<List<Book>> getBooks() {
        return m_allBooks;
    }

//    public LiveData<List<Book>> getAllBooks() {
//        return m_repository.getAllBooks();
//    }
//
//    public LiveData<List<Book>> getFilteredBooks(String term) {
//        return m_repository.find(term);
//    }

    public void setBookFilter(String term) {
        m_filterBooks.setValue(term);
    }

    public void insert(Book book) {
        m_repository.insert(book);
    }

    public void insertIfNotExist(Book book) {
        m_repository.insertIfNotExist(book);
    }
}
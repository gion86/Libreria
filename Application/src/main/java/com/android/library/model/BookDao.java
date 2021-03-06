package com.android.library.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Book... books);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> getAll();

    @Query("SELECT * FROM book_table WHERE rowid IN (:bookIds)")
    LiveData<List<Book>> getAllByIds(int[] bookIds);

    @Query("SELECT * FROM book_table ORDER BY read_date DESC")
    LiveData<List<Book>> getAllReadDataSorted();

    @Query("SELECT * FROM book_table WHERE title LIKE :title")
    Book findByTitle(String title);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM book_table")
    void deleteAll();

    /**
     * Full text search query
     *
     * @param term the FTS search key
     * @return the record set of books found
     */
    @Query("SELECT * FROM book_table WHERE book_table MATCH :term")
    LiveData<List<Book>> findWithFTS(String term);
}


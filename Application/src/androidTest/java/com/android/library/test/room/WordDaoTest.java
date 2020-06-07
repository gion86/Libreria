package com.android.library.test.room;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.library.model.Book;
import com.android.library.model.BookDao;
import com.android.library.model.BookRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * TODO This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4.class)
public class WordDaoTest {

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

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetWord() throws Exception {
        Book book = new Book("aaa", "authorA", "dddd");
        mBookDao.insert(book);
        List<Book> allBooks = LiveDataTestUtil.getValue(mBookDao.getAllReadDataSorted());
        assertEquals(allBooks.get(0).getTitle(), book.getTitle());
    }

    @Test
    public void getAllWords() throws Exception {
        Book book = new Book("aaa", "authorA", "dddd");
        mBookDao.insert(book);
        Book book1 = new Book("bbb", "authorB", "dddd2");
        mBookDao.insert(book1);
        List<Book> allBooks = LiveDataTestUtil.getValue(mBookDao.getAllReadDataSorted());
        assertEquals(allBooks.get(0).getTitle(), book.getTitle());
        assertEquals(allBooks.get(1).getTitle(), book1.getTitle());
    }

    @Test
    public void deleteAll() throws Exception {
        Book book = new Book("ccc", "authorC", "dddd3");
        mBookDao.insert(book);
        Book book1 = new Book("ddd", "authorD", "dddd4");
        mBookDao.insert(book1);
        mBookDao.deleteAll();
        List<Book> allBooks = LiveDataTestUtil.getValue(mBookDao.getAllReadDataSorted());
        assertTrue(allBooks.isEmpty());
    }
}

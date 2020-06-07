package com.android.library.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static volatile BookRoomDatabase INSTANCE;
    public static final ExecutorService s_databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback s_roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            s_databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
//                BookDao dao = INSTANCE.bookDao();
//                dao.deleteAll();

//                Book book = new Book("title", "author", "date");
//                dao.insert(book);
//                book = new Book("title2", "author2", "date2");
//                dao.insert(book);
            });
        }
    };

    static BookRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookRoomDatabase.class, "book_database")
                            .addCallback(s_roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract BookDao bookDao();
}
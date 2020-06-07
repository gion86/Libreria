/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.android.library.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.library.common.activities.SampleActivityBase;
import com.android.library.importdata.AmazonBookImporter;
import com.android.library.importdata.IBookImporter;
import com.android.library.model.Book;
import com.android.library.recyclerview.R;
import com.android.library.viewadapter.BookListViewAdapter;
import com.android.library.viewmodel.BookViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * TODO
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";
    public static final int ACTIVITY_FILE_REQUEST_CODE = 1;

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    private BookViewModel m_bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BookListViewAdapter adapter = new BookListViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        m_bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        m_bookViewModel.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable final List<Book> books) {
                // Update the cached copy of the words in the adapter.
                adapter.setBooks(books);
            }
        });

        TextInputEditText bookSearchText = (TextInputEditText) findViewById(R.id.text_search_field);
        bookSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strValue = bookSearchText.getText().toString();
                m_bookViewModel.setBookFilter(strValue);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem logToggle = menu.findItem(R.id.menuImportBook);
//        logToggle.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return true;
//            }
//        });
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_import_book:
                // TODO check read permission https://developer.android.com/training/permissions/requesting#java
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");

                startActivityForResult(Intent.createChooser(intent, "Seleziona un file"), ACTIVITY_FILE_REQUEST_CODE);
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                // The uri with the location of the file
                Uri selectedFile = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedFile);
                IBookImporter bookImporter = new AmazonBookImporter();
                bookImporter.importBooks(inputStream);

                for (Book book : bookImporter.getBookList()) {
                    m_bookViewModel.insertIfNotExist(book);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.android.library.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.library.model.Book;
import com.android.library.recyclerview.R;

import java.util.List;

public class BookListViewAdapter extends RecyclerView.Adapter<BookListViewAdapter.BookViewHolder> {

    class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookTitleView;
        private final TextView bookAuthorView;
        private final TextView bookDateView;

        private BookViewHolder(View itemView) {
            super(itemView);
            bookTitleView = (TextView) itemView.findViewById(R.id.bookTitleView);
            bookAuthorView = (TextView) itemView.findViewById(R.id.bookAuthorView);
            bookDateView = (TextView) itemView.findViewById(R.id.bookDateView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Book> m_books; // Cached copy of books

    public BookListViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if (m_books != null) {
            Book currentBook = m_books.get(position);
            holder.bookTitleView.setText(currentBook.getTitle());
            holder.bookAuthorView.setText(currentBook.getAuthor());
            holder.bookDateView.setText(currentBook.getHumanReadDate());
        } else {
            // Covers the case of data not being ready yet.
            holder.bookTitleView.setText("No book");
            holder.bookAuthorView.setText("");
            holder.bookDateView.setText("");
        }
    }

    public void setBooks(List<Book> books) {
        m_books = books;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (m_books != null)
            return m_books.size();
        else return 0;
    }
}
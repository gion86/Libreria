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

public class BookListViewAdapter extends RecyclerView.Adapter<BookListViewAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookTitleView;
        private final TextView bookAuthorView;
        private final TextView bookDateView;

        private WordViewHolder(View itemView) {
            super(itemView);
            bookTitleView = (TextView) itemView.findViewById(R.id.bookTitleView);
            bookAuthorView = (TextView) itemView.findViewById(R.id.bookAuthorView);
            bookDateView = (TextView) itemView.findViewById(R.id.bookDateView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Book> m_books; // Cached copy of words

    public BookListViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (m_books != null) {
            Book current = m_books.get(position);
            holder.bookTitleView.setText(current.getTitle());
            holder.bookAuthorView.setText(current.getAuthor());
            holder.bookDateView.setText(current.getReadDate());
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

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (m_books != null)
            return m_books.size();
        else return 0;
    }
}
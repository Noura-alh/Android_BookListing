package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView Title = (TextView) convertView.findViewById(R.id.Title);
        TextView Author = (TextView) convertView.findViewById(R.id.Author);
        TextView Publisher = (TextView) convertView.findViewById(R.id.publisher);


        Title.setText(currentBook.getTitle());
        Author.setText(currentBook.getAuthor());
        Publisher.setText(currentBook.getPublisher());


        return convertView;
    }
}

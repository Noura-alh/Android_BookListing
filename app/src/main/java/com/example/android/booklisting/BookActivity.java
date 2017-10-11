package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class BookActivity extends AppCompatActivity {

    static final String SEARCH_RESULTS = "Search Results";
    private static final int RESULTS = 10;
    final String myUrl = "https://www.googleapis.com/books/v1/volumes";
    private BookAdapter bAdapter;
    private ListView listView;
    private Book[] booksList;
    private TextView NoInfo;
    private ProgressBar progressBar;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NoInfo = (TextView) findViewById(R.id.noBooksFound);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        NoInfo.setVisibility(View.INVISIBLE);

        bAdapter = new BookAdapter(this, -1);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(bAdapter);


        SearchView search = (SearchView) findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                    progressBar.setVisibility(View.VISIBLE);
                    BookAsyncTask task = new BookAsyncTask(query, RESULTS);
                    task.execute();
                    return true;
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.noConnection, Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        if (savedInstanceState != null) {
            booksList = (Book[]) savedInstanceState.getParcelableArray(SEARCH_RESULTS);

            bAdapter.addAll(booksList);
        }


    }


    private void updateUser(List<Book> books) {
        if (books.isEmpty()) {
            // Update the user if no data was found
            NoInfo.setVisibility(View.VISIBLE);
        } else {
            NoInfo.setVisibility(View.GONE);
        }
        bAdapter.clear();
        bAdapter.addAll(books);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Book[] books = new Book[bAdapter.getCount()];
        for (int i = 0; i < books.length; i++) {
            books[i] = bAdapter.getItem(i);
        }
        outState.putParcelableArray(SEARCH_RESULTS, (Parcelable[]) books);
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {


        private String bSearchQuery;
        private int bCount;

        public BookAsyncTask(String searchQuery, int count) {
            bSearchQuery = searchQuery.replaceAll(" ", "%20");
            bCount = count;
        }

        @Override
        protected List<Book> doInBackground(String... urls) {


            String URL = myUrl + "?q=" + bSearchQuery + "&maxResults=" + bCount;

            List<Book> result = QueryUtils.fetchBookData(URL);
            return result;
        }


        @Override
        protected void onPostExecute(List<Book> data) {
            progressBar.setVisibility(View.INVISIBLE);
            bAdapter.clear();

            if (data == null) {
                return;
            }
            updateUser(data);

        }


    }
}

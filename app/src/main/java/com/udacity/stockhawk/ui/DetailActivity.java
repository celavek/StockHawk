package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.udacity.stockhawk.R;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final String STOCK_SHARE_HASHTAG = " #Stawk";

    private String mMovieSynopsis;

    private TextView mMovieYear;
    private TextView mMovieRating;
    private TextView mMovieOverview;

    //private StockData mCachedstockData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*//mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mMovieYear = (TextView) findViewById(R.id.tv_movie_year);
        //mMovieDuration = (TextView) findViewById(R.id.tv_movie_duration);
        mMovieRating = (TextView) findViewById(R.id.tv_movie_rating);
        mMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);

        mMovieSynopsis = getString(R.string.movie_synopsis);

        setSupportActionBar((Toolbar) findViewById(R.id.detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

    }

    /**
     * Uses the ShareCompat Intent builder to create our intent for sharing. We set the
     * type of content that we are sharing (just regular text), the text itself, and we return the
     * newly created Intent.
     *
     * @return The Intent to use to start our share.
     */
    private Intent createShareMovieIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mMovieSynopsis + STOCK_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareMovieIntent());*/
        return true;
    }
}

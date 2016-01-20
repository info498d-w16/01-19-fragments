package edu.uw.fragmentdemo;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity implements MovieFragment.OnMovieSelectionListener {

    private static final String TAG = "MovieActivity";
    boolean checkOrientation;

    FragmentTransaction ft;
    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detailFragment = new DetailFragment();
        ft = getFragmentManager().beginTransaction();
        ft.add(R.id.container, new MovieFragment());
        ft.add(R.id.container, detailFragment).addToBackStack(null);
        ft.commit();
        ft.hide(detailFragment);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // It doesn't work!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! WHY!!!!!!!!!!!
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ft.show(detailFragment);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ft.hide(detailFragment);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        DetailFragment detail = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", movie.toString());
        bundle.putString("imdb", movie.imdbId);

        detail.setArguments(bundle);

        //swap the fragments
        getFragmentManager().beginTransaction()
                .replace(R.id.container, detail)
                .addToBackStack(null)
                .commit();

    }

    //for support class Activity
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

package com.example.coyg.tmdb;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.coyg.tmdb.List.NowPlaying;
import com.example.coyg.tmdb.List.Search;
import com.example.coyg.tmdb.List.Popular;
import com.example.coyg.tmdb.List.TopRated;
import com.example.coyg.tmdb.List.UpComing;
import com.example.coyg.tmdb.favlist.FavList;

public class MainActivity extends AppCompatActivity
{
    private boolean doubleBackToExitPressedOnce = false;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Toolbar toolbar =findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter (getSupportFragmentManager ());

        mViewPager = findViewById (R.id.container);
        mViewPager.setAdapter (mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById (R.id.tabs);

        mViewPager.addOnPageChangeListener (new TabLayout.TabLayoutOnPageChangeListener (tabLayout));
        tabLayout.addOnTabSelectedListener (new TabLayout.ViewPagerOnTabSelectedListener (mViewPager));

        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent (getApplicationContext () , FavList.class);
                startActivity (i);
            }
        });

        if(getSupportActionBar ()!= null)
        {
            getSupportActionBar ().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId ();

        return super.onOptionsItemSelected (item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super (fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    Popular popular = new Popular ();
                    return popular;
                case 1:
                    TopRated topRated = new TopRated ();
                    return topRated;
                case 2:
                    NowPlaying nowPlaying = new NowPlaying ();
                    return nowPlaying;
                case 3:
                    UpComing upComing = new UpComing ();
                    return upComing;
                case 4:
                    Search search = new Search ();
                    return search;
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return 5;
        }
    }


    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            finishAffinity ();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler ().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

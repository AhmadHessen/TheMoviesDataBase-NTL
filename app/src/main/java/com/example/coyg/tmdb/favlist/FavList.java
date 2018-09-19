package com.example.coyg.tmdb.favlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.example.coyg.tmdb.Details.DetailsMain;
import com.example.coyg.tmdb.MainActivity;
import com.example.coyg.tmdb.R;
import com.example.coyg.tmdb.database.AppDatabase;
import com.example.coyg.tmdb.database.FavEntry;
import com.example.coyg.tmdb.database.MainViewModel;

import java.util.List;

public class FavList extends AppCompatActivity implements FavAdapter.ItemClickListener
{
    RecyclerView recyclerview;
    FavAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private AppDatabase db;
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_fav_list);

        recyclerview = findViewById (R.id.fav_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager (this));

        // Initialize the adapter and attach it to the RecyclerView
        adapter = new FavAdapter( this, this);
        recyclerview.setAdapter(adapter);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 90);
        gridLayoutManager = new GridLayoutManager (FavList.this ,noOfColumns) ;
        recyclerview.setLayoutManager(gridLayoutManager);

        db = AppDatabase.getsInstance ((getApplicationContext ()));
        setupViewModel();

        actionBar = getSupportActionBar ();
        if (actionBar != null)
        {
            //back arrow
            actionBar.setDisplayHomeAsUpEnabled (true);
        }
    }

    private void setupViewModel()
    {
        MainViewModel mainViewModel = ViewModelProviders.of (this).get(MainViewModel.class);
        mainViewModel.getFav ().observe (this, new Observer<List<FavEntry>> ()
        {
            @Override
            public void onChanged(@Nullable List<FavEntry> favEntries)
            {
                adapter.setFav (favEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(String itemId)
    {
        Intent intent = new Intent (FavList.this,DetailsMain.class);
        intent.putExtra ("id", itemId);
        startActivity (intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId ();

        if (id == android.R.id.home)
        {
            finish ();
        }
        return super.onOptionsItemSelected (item);
    }


    @Override
    public void onBackPressed()
    {
        finish ();
    }
}


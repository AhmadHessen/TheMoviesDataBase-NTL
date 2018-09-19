package com.example.coyg.tmdb.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;

public class MainViewModel extends AndroidViewModel
{
    private LiveData<List<FavEntry>> fav;



    public MainViewModel(@NonNull Application application)
    {
        super (application);
        AppDatabase appDatabase = AppDatabase.getsInstance (this.getApplication ());
        fav = appDatabase.FavDAO ().loadAllFavs ();
    }

    public LiveData<List<FavEntry>> getFav()
    {
        return fav;
    }
}

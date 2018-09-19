package com.example.coyg.tmdb.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavDAO
{
    @Query ("SELECT * FROM fav")
    LiveData<List<FavEntry>> loadAllFavs();

    @Insert
    void insertFav(FavEntry favEntry);

    @Delete
    void delete(FavEntry favEntry);

    @Query ("SELECT * FROM fav WHERE id = :id")
    LiveData<FavEntry> loadFavById(String id);

    @Query ("SELECT id FROM fav WHERE id = :id1 ")
    String load1FavById(String id1);
}

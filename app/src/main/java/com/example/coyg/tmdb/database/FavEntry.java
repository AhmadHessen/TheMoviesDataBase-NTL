package com.example.coyg.tmdb.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "fav")
public class FavEntry
{
    @PrimaryKey
    @NonNull
    private String id;

    //private String favid;
    private String title;
    private String favPosterURL;

    public FavEntry(@NonNull String id, String title, String favPosterURL)
    {
        this.id = id;
        this.title = title;
        this.favPosterURL = favPosterURL;
    }

    //    public FavEntry(int id, String favid, String title, String favPosterURL)
//    {
//        this.id = id;
//        this.favid = favid;
//        this.title = title;
//        this.favPosterURL = favPosterURL;
//    }
//
//    @Ignore
//    public FavEntry(String favid, String title, String favPosterURL)
//    {
//        this.favid = favid;
//        this.title = title;
//        this.favPosterURL = favPosterURL;
//    }

    @NonNull
    public String getId()
    {
        return id;
    }

    public void setId(@NonNull String id)
    {
        this.id = id;
    }


//    public String getFavid()
//    {
//        return favid;
//    }
//
//    public void setFavid(String favid)
//    {
//        this.favid = favid;
//    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getFavPosterURL()
    {
        return favPosterURL;
    }

    public void setFavPosterURL(String favPosterURL)
    {
        this.favPosterURL = favPosterURL;
    }
}

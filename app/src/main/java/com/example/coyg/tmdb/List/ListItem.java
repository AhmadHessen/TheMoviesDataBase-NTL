package com.example.coyg.tmdb.List;

public class ListItem
{
    private String id;
    private String head;
    private String desc;
    private String imageURL;
    private String imageURLPoster;

    public ListItem(String id, String head, String desc, String imageURL, String imageURLPoster)
    {
        this.id = id;
        this.head = head;
        this.desc = desc;
        this.imageURL = imageURL;
        this.imageURLPoster = imageURLPoster;
    }

    public String getId()
    {
        return id;
    }

    public String getHead()
    {
        return head;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public String getImageURLPoster()
    {
        return imageURLPoster;
    }

}

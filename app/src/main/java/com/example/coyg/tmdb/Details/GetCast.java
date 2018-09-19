package com.example.coyg.tmdb.Details;

public class GetCast
{
    private String castImage;
    private String castName;
    private String castChar;

    public GetCast(String castImage, String castName, String castChar)
    {
        this.castImage = castImage;
        this.castName = castName;
        this.castChar = castChar;
    }

    public String getCastImage()
    {
        return castImage;
    }

    public String getCastName()
    {
        return castName;
    }

    public String getCastChar()
    {
        return castChar;
    }
}

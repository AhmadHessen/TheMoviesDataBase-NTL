package com.example.coyg.tmdb.Details;

public class GetReviews
{
    private String commentAuthor;
    private String theComment;

    public GetReviews(String commentAuthor, String theComment)
    {
        this.commentAuthor = commentAuthor;
        this.theComment = theComment;
    }

    public String getCommentAuthor()
    {
        return commentAuthor;
    }

    public String getTheComment()
    {
        return theComment;
    }
}

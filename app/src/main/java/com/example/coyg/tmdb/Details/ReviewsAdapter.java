package com.example.coyg.tmdb.Details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coyg.tmdb.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>
{

    public List<GetReviews> listItemsReviews;
    private Context context;

    public ReviewsAdapter(List<GetReviews> listItemsReviews, Context context)
    {
        this.listItemsReviews = listItemsReviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_adapter, parent , false);

        return new ReviewsAdapter.ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position)
    {
        final GetReviews getReviews = listItemsReviews.get(position);

        holder.commentAuthor.setText (getReviews.getCommentAuthor ());
        holder.theComment.setText (getReviews.getTheComment ());

    }

    @Override
    public int getItemCount()
    {
        return listItemsReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView commentAuthor;
        public TextView theComment;

        public ViewHolder(View itemView)
        {
            super (itemView);

            commentAuthor = itemView.findViewById (R.id.commentAuthor);
            theComment = itemView.findViewById (R.id.theComment);
        }
    }
}

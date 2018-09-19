package com.example.coyg.tmdb.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coyg.tmdb.Details.DetailsMain;
import com.example.coyg.tmdb.R;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    public List<ListItem> listItems;
    private Context context;
    private int lastPosition = -1;

    public MyAdapter(List<ListItem> listItems, Context context)
    {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item , parent , false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position)
    {
        final ListItem listItem = listItems.get(position);

        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+listItem.getImageURLPoster ())
                .apply (new RequestOptions ().placeholder(R.drawable.defaultposter).error(R.drawable.defaultposter))
                .into(holder.poster);
        setAnimation(holder.itemView, position);


        holder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(v.getContext() , DetailsMain.class);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                        ((Activity) v.getContext (),holder.poster,"imageTrans");
                i.putExtra ("id" , listItem.getId ());
                i.putExtra ("activity" , "main" );
                context.startActivity(i,activityOptionsCompat.toBundle ());
            }
        });
    }

    public int getItemCount()
    {
        return listItems.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView poster;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView)
        {
            super (itemView);

            poster = itemView.findViewById (R.id.poster);
            relativeLayout = itemView.findViewById (R.id.relativeLayout);
        }
    }
}

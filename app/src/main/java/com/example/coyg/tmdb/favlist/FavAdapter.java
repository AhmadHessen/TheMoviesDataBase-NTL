package com.example.coyg.tmdb.favlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coyg.tmdb.R;
import com.example.coyg.tmdb.database.AppDatabase;
import com.example.coyg.tmdb.database.AppExecutors;
import com.example.coyg.tmdb.database.FavEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>
{
    final private ItemClickListener mItemClickListener;
    private List<FavEntry> mFavEntries;
    private Context mContext;

    public FavAdapter(Context context, ItemClickListener listener)
    {
        mContext = context;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fav_adapter, parent, false);

        return new FavAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        FavEntry favEntry = mFavEntries.get(position);

        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500"+favEntry.getFavPosterURL ())
                .apply (new RequestOptions ().placeholder(R.drawable.defaultposter).error(R.drawable.defaultposter))
                .into(holder.poster);
    }

    @Override
    public int getItemCount()
    {
        if (mFavEntries == null)
        {
            return 0;
        }
        return mFavEntries.size ();
    }

    public List<FavEntry> getmFavEntries()
    {

        return mFavEntries;
    }

    public void setFav(List<FavEntry> favEntries)
    {
        mFavEntries = favEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener
    {
        void onItemClickListener(String itemId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView poster;

        public ViewHolder(View itemView)
        {
            super (itemView);

            poster = itemView.findViewById (R.id.fav_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            String elementId = "";
            elementId = mFavEntries.get(getAdapterPosition()).getId ();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}

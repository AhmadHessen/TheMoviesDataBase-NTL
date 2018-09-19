package com.example.coyg.tmdb.Details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coyg.tmdb.R;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>
{

    public List<GetCast> listItemsCast;
    private Context context;

    public CastAdapter(List<GetCast> listItemsCast, Context context)
    {
        this.listItemsCast = listItemsCast;
        this.context = context;
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cast_adapter, parent , false);

        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, int position)
    {
        final GetCast getcast = listItemsCast.get(position);

        holder.name.setText (getcast.getCastName ());
        holder.character.setText (getcast.getCastChar ());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+getcast.getCastImage ())
                .apply (new RequestOptions ().placeholder(R.drawable.defaultpic).error(R.drawable.defaultpic))
                .into(holder.profile);

        holder.linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText (context,"Name: "+getcast.getCastName()+" | Character: "+getcast.getCastChar (),Toast.LENGTH_LONG).show ();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listItemsCast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout linearLayout;
        public ImageView profile;
        public TextView name;
        public TextView character;

        public ViewHolder(View itemView)
        {
            super (itemView);

            linearLayout = (LinearLayout)itemView.findViewById (R.id.linearCast);
            profile = (ImageView)itemView.findViewById (R.id.profile);
            name = (TextView)itemView.findViewById (R.id.name);
            character = (TextView)itemView.findViewById (R.id.character);
        }
    }
}

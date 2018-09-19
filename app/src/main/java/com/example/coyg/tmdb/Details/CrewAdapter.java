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

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder>
{

    public List<GetCrew> listItemsCrew;
    private Context context;

    public CrewAdapter(List<GetCrew> listItemsCrew, Context context)
    {
        this.listItemsCrew = listItemsCrew;
        this.context = context;
    }

    @Override
    public CrewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_crew_adapter, parent , false);

        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(CrewAdapter.ViewHolder holder, int position)
    {
        final GetCrew getcrew = listItemsCrew.get(position);

        holder.nameCrew.setText (getcrew.getCrewtName());
        holder.jobCrew.setText (getcrew.getJobCrew());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+getcrew.getCrewImage ())
                .apply (new RequestOptions ().placeholder(R.drawable.defaultpic).error(R.drawable.defaultpic))
                .into(holder.profileCrew);

        holder.linearCrew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText (context,"Name: "+getcrew.getCrewtName()+" | Job: "+getcrew.getJobCrew(),Toast.LENGTH_LONG).show ();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listItemsCrew.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout linearCrew;
        public ImageView profileCrew;
        public TextView nameCrew;
        public TextView jobCrew;

        public ViewHolder(View itemView)
        {
            super (itemView);

            linearCrew = itemView.findViewById (R.id.linearCrew);
            profileCrew = itemView.findViewById (R.id.profileCrew);
            nameCrew = itemView.findViewById (R.id.nameCrew);
            jobCrew = itemView.findViewById (R.id.jobCrew);
        }
    }
}

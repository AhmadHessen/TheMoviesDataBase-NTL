package com.example.coyg.tmdb.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coyg.tmdb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopRated extends Fragment
{
    static int i=1;

    private int dy1=0;
    private int  totalItemCount,visibleItemCount,pastVisiblesItems;
    private GridLayoutManager gridLayoutManager;

    private static final String URL_DATA = "https://api.themoviedb.org/3/movie/top_rated?api_key="+"your api key"+"&page=";

    private RecyclerView recyclerviewTop;
    public RecyclerView.Adapter adapterTop;
    public List<ListItem> listItemsTop = new ArrayList<> ();
    public SwipeRefreshLayout swipeRefreshLayout;
    public ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate (R.layout.top_rated, container , false);

        recyclerviewTop = v.findViewById(R.id.recyclerViewTop);
        swipeRefreshLayout = v.findViewById (R.id.swipeRefreshLayoutTop);
        loading = v.findViewById (R.id.loadingTop);

        adapterTop = new MyAdapter(listItemsTop , getActivity());
        recyclerviewTop.setAdapter (adapterTop);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 90);
        gridLayoutManager = new GridLayoutManager (getActivity (),noOfColumns) ;
        recyclerviewTop.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener ()
        {
            @Override
            public void onRefresh()
            {
                clear ();
                loadRecyclerViewData (1);
                swipeRefreshLayout.setRefreshing (false);
            }
        });

        loadRecyclerViewData(1);

        loadMore(recyclerviewTop);

        return v;
    }

    private void loadMore(RecyclerView recyclerviewTop)
    {
        recyclerviewTop.addOnScrollListener (new RecyclerView.OnScrollListener ()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (dy1 > 0) //check for scroll down
                {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) == totalItemCount && newState == RecyclerView.SCROLL_STATE_IDLE)
                    {
                        loadRecyclerViewData(++i);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                dy1=dy;
            }
        });
    }

    private void loadRecyclerViewData(int PageNum)
    {
        loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA+PageNum,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        loading.setVisibility(View.GONE);
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");

                            for (int i=0 ; i<jsonArray.length() ; i++)
                            {
                                JSONObject o = jsonArray.getJSONObject(i);
                                ListItem item = new ListItem
                                        (
                                                o.getString("id"),
                                                o.getString("title"),
                                                o.getString("overview"),
                                                o.getString("backdrop_path"),
                                                o.getString("poster_path")
                                        );
                                listItemsTop.add(item);
                            }
                            adapterTop.notifyDataSetChanged ();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getActivity() , error.getMessage() ,Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void clear()
    {
        int size = this.listItemsTop.size();
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                this.listItemsTop.remove(0);
            }

            adapterTop.notifyItemRangeRemoved(0, size);
        }
    }
}

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
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.coyg.tmdb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment
{
    private FloatingSearchView floatingSearch;

    static int i=1;

    private String URL_DATA;
    private String temp;

    private int dy1=0;
    private int  totalItemCount,visibleItemCount,pastVisiblesItems;
    private GridLayoutManager gridLayoutManager;

    private RecyclerView recyclerviewSearch;
    public RecyclerView.Adapter adapterS;
    public List<ListItem> listItemsS = new ArrayList<> ();
    public SwipeRefreshLayout swipeRefreshLayout;
    public ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate (R.layout.search, container , false);

        floatingSearch = v.findViewById (R.id.floatingSearch);
        recyclerviewSearch = v.findViewById (R.id.recyclerViewSearch);
        swipeRefreshLayout = v.findViewById (R.id.swipeRefreshLayoutSearch);
        loading = v.findViewById (R.id.loadingSearch);

        adapterS = new MyAdapter(listItemsS , getActivity());
        recyclerviewSearch.setAdapter (adapterS);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 90);
        gridLayoutManager = new GridLayoutManager (getActivity (),noOfColumns) ;
        recyclerviewSearch.setLayoutManager(gridLayoutManager);

        floatingSearch.setOnSearchListener(new FloatingSearchView.OnSearchListener()
        {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion)
            {

            }
            @Override
            public void onSearchAction(String currentQuery)
            {
                clear ();

                temp = "https://api.themoviedb.org/3/search/movie?api_key="+"your api key"+"&language=en-US&query="
                        +currentQuery+"&page=1&include_adult=false";

                temp = temp.replaceAll(" ", "%20");
                URL_DATA = new String(temp);

                loadRecyclerViewData(1);
            }
        });

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

        loadMore(recyclerviewSearch);


        return v;
    }


    private void loadMore(RecyclerView recyclerviewSearch)
    {
        recyclerviewSearch.addOnScrollListener (new RecyclerView.OnScrollListener ()
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


    public boolean clear()
    {
        int size = this.listItemsS.size();
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                this.listItemsS.remove(0);
            }

            adapterS.notifyItemRangeRemoved(0, size);
        }
        return true;
    }


    private void loadRecyclerViewData(int pageNum)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");

        if(this.listItemsS.size ()>0)
        {
            loading.setVisibility(View.VISIBLE);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA+pageNum,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
                        loading.setVisibility (View.GONE);

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
                                listItemsS.add(item);
                            }
                            adapterS.notifyDataSetChanged ();
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
                        progressDialog.dismiss();
                        loading.setVisibility (View.GONE);
                        if(!floatingSearch.getQuery ().isEmpty ())
                        {
                            Toast.makeText(getActivity() , error.getMessage() ,Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
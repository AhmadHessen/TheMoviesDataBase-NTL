package com.example.coyg.tmdb.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.coyg.tmdb.Config;
import com.example.coyg.tmdb.R;
import com.example.coyg.tmdb.database.AppDatabase;
import com.example.coyg.tmdb.database.AppExecutors;
import com.example.coyg.tmdb.database.FavEntry;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DetailsMain extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private TextView titleDetails;
    private TextView overViewDetails;
    private TextView rateDetails;
    private ImageView posterDetails;
    private ImageView coverDetails;
    private ImageView watcedListDetails;

    private String URL_deatailsDATA;
    private String URL_reviewsDATA;

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private  String key;


    private List<GetCast> listItemsCast;
    public RecyclerView recyclerViewCast;
    public RecyclerView.Adapter castAdapter;

    private List<GetCrew> listItemsCrew;
    public RecyclerView recyclerViewCrew;
    public RecyclerView.Adapter crewAdapter;

    private List<GetReviews> listItemsReviews;
    public RecyclerView recyclerViewReviews;
    public RecyclerView.Adapter reviewsAdapter;

    private AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main_details);

        youTubeView = findViewById(R.id.youtube_view);
        titleDetails = findViewById (R.id.titleDetails);
        overViewDetails = findViewById (R.id.descDeatails);
        rateDetails = findViewById (R.id.Rate);
        posterDetails = findViewById (R.id.posterDetails);
        coverDetails = findViewById (R.id.coverDetails);
        watcedListDetails = findViewById (R.id.watcedListDetails);

        mDb = AppDatabase.getsInstance (getApplicationContext ());
////////////////////////////////////////////////////////////////////////////////////////////
        //cast Section
        recyclerViewCast = findViewById (R.id.recyclerViewCast);
        recyclerViewCast.setHasFixedSize (true);
        LinearLayoutManager layoutManagerCast = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);
        layoutManagerCast.setReverseLayout(false);
        recyclerViewCast.setLayoutManager(layoutManagerCast);
        listItemsCast = new ArrayList<> ();
//////////////////////////////////////////////////////////////////////////////////////////////
        //cast Section
        recyclerViewCrew = findViewById (R.id.recyclerViewCrew);
        recyclerViewCrew.setHasFixedSize (true);
        LinearLayoutManager layoutManagerCrew = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);
        layoutManagerCrew.setReverseLayout(false);
        recyclerViewCrew.setLayoutManager(layoutManagerCrew);
        listItemsCrew = new ArrayList<> ();
////////////////////////////////////////////////////////////////////////////////////////////
        //review Section
        recyclerViewReviews = findViewById (R.id.recyclerViewReviews);
        recyclerViewReviews.setHasFixedSize (true);
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerViewReviews.getContext (), VERTICAL);
        recyclerViewReviews.addItemDecoration(decoration);
        LinearLayoutManager layoutManagerReviews = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, true);
        layoutManagerReviews.setReverseLayout(false);
        recyclerViewReviews.setLayoutManager(layoutManagerReviews);
        listItemsReviews = new ArrayList<> ();
////////////////////////////////////////////////////////////////////////////////////////////
        Bundle data = getIntent().getExtras();

        if(data==null)
            return;

        final String ID = data.getString ("id");
        URL_deatailsDATA= "https://api.themoviedb.org/3/movie/"+ID
                +"?api_key="+"your api key"+"&append_to_response=credits,videos";

        URL_reviewsDATA= "https://api.themoviedb.org/3/movie/"
                +ID+"/reviews?api_key="+"your api key"+"&language=en-US&page=1";


        loadData();
        loadReviewsData();
////////////////////////////////////////////////////////////////////////////////////////////

        String favID =
                mDb.FavDAO ().load1FavById (ID);

        if(favID==null)
        {
            Glide.with(getApplicationContext ()).load(R.drawable.ic_action_unwatched).into(watcedListDetails);
        }
        else
        {
            Glide.with(getApplicationContext ()).load(R.drawable.ic_action_watched).into(watcedListDetails);
        }
    }

    public void loadData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_deatailsDATA,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            final JSONObject jsonObject = new JSONObject(response);

////////////////////////////////////////////////////////////////////////////////////////////////
                            //details section

                            titleDetails.setText (jsonObject.getString("title"));
                            overViewDetails.setText (jsonObject.getString ("overview"));
                            rateDetails.setText ("Rate: "+jsonObject.getString("vote_average")
                                    +"\t\t\t Release Date: "
                                    +jsonObject.getString("release_date"));

                            Glide.with(getApplicationContext ()).load("https://image.tmdb.org/t/p/w500"
                                    + jsonObject.getString("poster_path"))
                                    .apply (new RequestOptions ()
                                            .placeholder(R.drawable.defaultposter).error(R.drawable.defaultposter))
                                    .into(posterDetails);

                            Glide.with(getApplicationContext ()).load("https://image.tmdb.org/t/p/w500"
                                    + jsonObject.getString("backdrop_path"))
                                    .apply (new RequestOptions()
                                            .placeholder(R.drawable.defaultposter).error(R.drawable.defaultposter))
                                    .into(coverDetails);

                            JSONObject jsonObjectVideo = jsonObject.getJSONObject ("videos");
                            JSONArray jsonArrayVideo = jsonObjectVideo.getJSONArray ("results");
                            JSONObject oV = jsonArrayVideo.getJSONObject(0);

                            key=(oV.getString ("key"));
                            youTubeView.initialize(Config.YOUTUBE_API_KEY, DetailsMain.this);

////////////////////////////////////////////////////////////////////////////////////////////////
                            //to fav list
                            watcedListDetails.setOnClickListener (new View.OnClickListener ()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    try
                                    {
                                        String favID =
                                                mDb.FavDAO ().load1FavById (jsonObject.getString("id"));
                                        //Toast.makeText (DetailsMain.this," "+favID,Toast.LENGTH_LONG).show ();

                                        if (favID==null)
                                        {
                                            final FavEntry favEntry = new FavEntry
                                                    (jsonObject.getString("id"),
                                                            jsonObject.getString("title"),
                                                            jsonObject.getString("poster_path"));
                                            AppExecutors.getInstance ().getDiskIO ().execute
                                                    (new Runnable ()
                                                    {
                                                        @Override
                                                        public void run()
                                                        {
                                                            mDb.FavDAO ().insertFav (favEntry);
                                                        }
                                                    });
                                            Toast.makeText (DetailsMain.this
                                                    , "Added to Watched list" , Toast.LENGTH_LONG).show ();

                                            Glide.with(getApplicationContext ())
                                                    .load(R.drawable.ic_action_watched).into(watcedListDetails);
                                        }
                                        else
                                        {
                                            final FavEntry favEntry = new FavEntry
                                                    (jsonObject.getString("id"),
                                                            jsonObject.getString("title"),
                                                            jsonObject.getString("poster_path"));
                                            AppExecutors.getInstance ().getDiskIO ().execute
                                                    (new Runnable ()
                                                    {
                                                        @Override
                                                        public void run()
                                                        {
                                                            mDb.FavDAO ().delete (favEntry);
                                                        }
                                                    });
                                            Toast.makeText (DetailsMain.this
                                                    , "Removed from Watched list" , Toast.LENGTH_LONG).show ();
                                            Glide.with(getApplicationContext ())
                                                    .load(R.drawable.ic_action_unwatched).into(watcedListDetails);

                                        }

                                    } catch (JSONException e)
                                    {
                                        e.printStackTrace ();
                                    }
                                }
                            });
////////////////////////////////////////////////////////////////////////////////////////////////
                            //get cast

                            JSONObject jsonObjectCredits = jsonObject.getJSONObject ("credits");

                            JSONArray jsonArrayCast = jsonObjectCredits.getJSONArray ("cast");

                            for (int i=0 ; i<jsonArrayCast.length() ; i++)
                            {
                                JSONObject oC = jsonArrayCast.getJSONObject(i);
                                GetCast getcast = new GetCast
                                        (
                                                oC.getString ("profile_path"),
                                                oC.getString ("name"),
                                                oC.getString ("character")
                                        );
                                listItemsCast.add(getcast);
                            }
                            castAdapter = new CastAdapter (listItemsCast , getApplicationContext ());
                            recyclerViewCast.setAdapter (castAdapter);
////////////////////////////////////////////////////////////////////////////////////////////////
///                         //get crew
                            JSONArray jsonArrayCrew = jsonObjectCredits.getJSONArray ("crew");

                            for (int i=0 ; i<jsonArrayCrew.length() ; i++)
                            {
                                JSONObject oC = jsonArrayCrew.getJSONObject(i);
                                GetCrew getcrew = new GetCrew
                                        (
                                                oC.getString ("profile_path"),
                                                oC.getString ("name"),
                                                oC.getString ("job")
                                        );
                                listItemsCrew.add(getcrew);
                            }
                            crewAdapter = new CrewAdapter (listItemsCrew , getApplicationContext ());
                            recyclerViewCrew.setAdapter (crewAdapter);

////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getApplicationContext () , e.getMessage () ,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext () , error.getMessage() ,Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadReviewsData()
    {
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET,
                URL_reviewsDATA,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObjectReviews = new JSONObject(response);
                            JSONArray jsonArrayReviews = jsonObjectReviews.getJSONArray ("results");

                            for (int i=0 ; i<jsonArrayReviews.length() ; i++)
                            {
                                JSONObject oR = jsonArrayReviews.getJSONObject(i);

                                GetReviews getReviews = new GetReviews
                                        (
                                                oR.getString ("author"),
                                                oR.getString ("content")
                                        );
                                listItemsReviews.add(getReviews);
                            }
                            reviewsAdapter = new ReviewsAdapter (listItemsReviews , getApplicationContext ());
                            recyclerViewReviews.setAdapter (reviewsAdapter);
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
                        Toast.makeText(getApplicationContext () , error.getMessage() ,Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext () );
        requestQueue1.add(stringRequest1);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored)
    {
        if (!wasRestored)
        {
            player.cueVideo(key);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason)
    {
        if (errorReason.isUserRecoverableError())
        {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        }
        else
        {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RECOVERY_REQUEST)
        {
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


    public void back(View view)
    {
        finish ();
    }
}

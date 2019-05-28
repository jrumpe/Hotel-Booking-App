package com.example.guzman.app1;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.guzman.app1.Intrerface.ItemHotelClickListener;
import com.example.guzman.app1.adapters.HotelsViewHolder;
import com.example.guzman.app1.models.Hotels;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    String hotelsName;

    MaterialSearchBar searchBars;

    List<String> suggestList = new ArrayList<>();
    FirebaseRecyclerAdapter<Hotels, HotelsViewHolder> adapter;
    FirebaseRecyclerAdapter<Hotels, HotelsViewHolder> searchAdapter;

    FirebaseDatabase dbase;
    DatabaseReference mHotels;

    RecyclerView recyclerHotels;
    RecyclerView.LayoutManager laytmanager;
    SwipeRefreshLayout swipeSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        swipeSearch = (SwipeRefreshLayout) findViewById(R.id.swipeSearch);

        dbase = FirebaseDatabase.getInstance();
        mHotels = dbase.getReference("Hotels");

        searchBars = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBars.setCardViewElevation(10);

        recyclerHotels = (RecyclerView) findViewById(R.id.recycler_search);
        recyclerHotels.setHasFixedSize(true);
        laytmanager = new LinearLayoutManager(this);
        recyclerHotels.setLayoutManager(laytmanager);


        swipeSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadFireBaseHotels();
            }
        });
//        if (getIntent() !=null)
//            hotelsName = getIntent().getStringExtra("hotelname");
//
//        if (!hotelsName.isEmpty() && hotelsName !=null){
//
//        }

        loadSuggestions();

//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
    }

    private void loadSuggestions() {
        swipeSearch.setRefreshing(false);

        mHotels.orderByChild("HotelsId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Hotels hotels = postSnapshot.getValue(Hotels.class);
                    suggestList.add(hotels.getHotelName()); //add name of hotel to suggest list
                    searchBars.setLastSuggestions(suggestList);
                    searchBars.setCardViewElevation(10);
                    searchBars.addTextChangeListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            //when user type something we will change suggestion list
                            List<String> suggest = new ArrayList<String>();
                            for (String search : suggestList) { //loop in suggest list
                                if (search.toLowerCase().contains(searchBars.getText().toLowerCase())) {
                                    suggest.add(search);
                                }
                                searchBars.setLastSuggestions(suggest);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    searchBars.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                        @Override
                        public void onSearchStateChanged(boolean enabled) {
                            //When search bar is closed restore original search adapter
                            if (!enabled)
                                recyclerHotels.setAdapter(adapter);

                        }

                        @Override
                        public void onSearchConfirmed(CharSequence text) {
                            //When search is finished show results of the search adapter
                            startSearch(text);

                        }

                        @Override
                        public void onButtonClicked(int buttonCode) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        swipeSearch.setRefreshing(false);

        searchAdapter = new FirebaseRecyclerAdapter<Hotels, HotelsViewHolder>(
                Hotels.class,
                R.layout.hotel_tonight_sample,
                HotelsViewHolder.class,
                mHotels.orderByChild("HotelsId")
        ) {
            @Override
            protected void populateViewHolder(HotelsViewHolder viewHolder, final Hotels model, int position) {

                viewHolder.txtName.setText(model.getHotelName());
                viewHolder.txtDesc.setText(model.getHotelDesc());
                viewHolder.txtLikes.setText(model.getHotelLikes());
                viewHolder.txtLocation.setText(model.getHotelLocation());
                viewHolder.txtStars.setText(model.getHotelStars());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgHotel);


                final Hotels clickItem = model;

                viewHolder.setItemHotelClickListener(new ItemHotelClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        TastyToast.makeText(SearchActivity.this, "" + clickItem.getHotelName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                        Intent exploreHotel = new Intent(SearchActivity.this, ExploreHotel.class);
                        //get id and sent to new activity
                        exploreHotel.putExtra("hotelname", model.getHotelName()); // send hotel name to the next activity
                        startActivity(exploreHotel);

                        // exploreHotel.putExtra("HotelsId", adapter.getRef(position).getKey());
                    }
                });
            }

        };

        recyclerHotels.setAdapter(searchAdapter);

    }

    private void loadFireBaseHotels() {
        swipeSearch.setRefreshing(false);

        adapter = new FirebaseRecyclerAdapter<Hotels, HotelsViewHolder>(Hotels.class, R.layout.hotel_tonight_sample, HotelsViewHolder.class, mHotels) {

            @Override
            protected void populateViewHolder(HotelsViewHolder viewHolder, final Hotels model, int position) {

                viewHolder.txtName.setText(model.getHotelName());
                viewHolder.txtDesc.setText(model.getHotelDesc());
                viewHolder.txtLikes.setText(model.getHotelLikes());
                viewHolder.txtLocation.setText(model.getHotelLocation());
                viewHolder.txtStars.setText(model.getHotelStars());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgHotel);


                final Hotels clickItem = model;

                viewHolder.setItemHotelClickListener(new ItemHotelClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        TastyToast.makeText(SearchActivity.this, "" + clickItem.getHotelName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                        Intent exploreHotel = new Intent(SearchActivity.this, ExploreHotel.class);
                        //get id and sent to new activity

                        exploreHotel.putExtra("hotelname", model.getHotelName()); // send hotel name to the next activity

                        // exploreHotel.putExtra("HotelsId", adapter.getRef(position).getKey());
                        startActivity(exploreHotel);
                    }
                });


            }
        };
        recyclerHotels.setAdapter(adapter);
    }

}
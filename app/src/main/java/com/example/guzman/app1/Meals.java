package com.example.guzman.app1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.guzman.app1.Intrerface.ItemCategoryClickListener;
import com.example.guzman.app1.adapters.MealsViewHolder;
import com.example.guzman.app1.models.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;


@SuppressLint("ValidFragment")
public class Meals extends Fragment {

    FirebaseDatabase database;
    DatabaseReference category;

    FirebaseRecyclerAdapter<Category, MealsViewHolder> adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    ProgressBar progressBar;
    RecyclerView.LayoutManager laytManager;



    @SuppressLint("ValidFragment")
    public Meals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        recyclerView = view.findViewById(R.id.recycler_meals);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        laytManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(laytManager);

       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        progressBar = view.findViewById(R.id.progressBar);

        LoadMealsCategory();

//        swipe = view.findViewById(R.id.swipe);
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                LoadMealsCategory();
//
//                //LoadMeal();
//            }
//        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        LoadMealsCategory();
        //  LoadMeal();
    }

    @Override
    public String toString() {
        return ("Meals");
    }

    private void LoadMealsCategory() {

        adapter = new FirebaseRecyclerAdapter<Category, MealsViewHolder>(
                Category.class,
                R.layout.food,
                MealsViewHolder.class,
                category) {


            @Override
            protected void populateViewHolder(MealsViewHolder viewHolder, Category model, int position) {

                viewHolder.Name.setText(model.getName());
                Picasso.with(getContext()).load(model.getImage()).into(viewHolder.imgImage);

                final Category clickItem = model;

                viewHolder.setItemCategoryClickListener(new ItemCategoryClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        TastyToast.makeText(getContext(), "" + clickItem.getName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                        Intent foodList = new Intent(getContext(), FoodList.class);
                        //get id and sent to new activity
                        foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }


//    private void LoadMeal() {
//
//        progressBar.setVisibility(View.VISIBLE);
//        RequestQueue requestQueue = VolleySingleton.getRequestQueue(getContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.BASE_HOTEL_MEALS, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                if (s != null) {
//                    ParseData(s);
//                    return;
//                }
//                TastyToast.makeText(getContext(), "Error Encountered", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                progressBar.setVisibility(View.GONE);
//                swipe.setRefreshing(false);
//                TastyToast.makeText(getContext(), "Connection Error" + volleyError, TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//
//                params.put("hotelname", ExploreHotel.hotelsname);
//
//                // params.put("getmeals", "getmeals");
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//        requestQueue.start();
//    }
//
//    private void ParseData(String s) {
//        try {
//            JSONArray jsonArray = new JSONArray(s);
//            ArrayList<MealModel> mealModels = new ArrayList<>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                MealModel mealModel = new MealModel();
//
//
//                mealModel.setFoodName(jsonObject.getString("foodname"));
//                mealModel.setFoodLikes(jsonObject.getString("foodlikes"));
//                mealModel.setFoodPrice(jsonObject.getString("foodprice"));
//
//                mealModel.setImage(jsonObject.getString("foodimage"));
//
//                mealModels.add(mealModel);
//
//                recyclerView.setAdapter(new MealsAdapter(getContext(), mealModels));
//                progressBar.setVisibility(View.GONE);
//                swipe.setRefreshing(false);
//            }
//        } catch (JSONException e) {
//            progressBar.setVisibility(View.GONE);
//            swipe.setRefreshing(false);
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                TastyToast.makeText(getContext(), jsonObject.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//
//            } catch (JSONException e1) {
//                TastyToast.makeText(getContext(), "Connection Error" + e1, TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//
//                Log.e("Error", e1.toString());
//            }
//        }
//    }
}

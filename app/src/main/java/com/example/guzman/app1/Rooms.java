package com.example.guzman.app1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guzman.app1.Intrerface.ItemCategoryClickListener;
import com.example.guzman.app1.adapters.RoomsViewHolderF;
import com.example.guzman.app1.models.Room;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;


@SuppressLint("ValidFragment")
public class Rooms extends Fragment implements View.OnClickListener {
    String pricelux, imglux;
    String pricedel, imgdel;
    String priceava, imgava;

    String luxury, deluxe, avarage;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    ProgressBar progressBar;

    Context context;

    //   Luxury Room
    TextView roomPrice;
    TextView roomLikes;
    ImageView roomImage;
    //   Deluxe Room
    TextView roomPrice2;
    TextView roomLikes2;
    ImageView roomImage2;
    //   Avarage  Room
    TextView roomPrice3;
    TextView roomLikes3;
    ImageView roomImage3;


    FirebaseRecyclerAdapter<Room, RoomsViewHolderF> adapter;

    FirebaseDatabase db;
    DatabaseReference rooms_tb;

    RecyclerView recyclerRooms;
    SwipeRefreshLayout swipeRooms;
    ProgressBar progBar;

    String hotelsName;
    public Room model;

    @SuppressLint("ValidFragment")
    public Rooms(String hotelsId) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);

        db = FirebaseDatabase.getInstance();
        rooms_tb = db.getReference("Rooms");

        recyclerRooms = view.findViewById(R.id.recyclerRooms);
        recyclerRooms.setItemAnimator(new DefaultItemAnimator());
        recyclerRooms.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // pbar = findViewById(R.id.pbar);

        swipeRooms = view.findViewById(R.id.swipeRooms);

        //    hotelsName = getActivity().getIntent().getExtras().getString("HotelsId");

        // hotelsId =getI().getExtras().getString("name");

        if (getActivity() != null)
            hotelsName = getActivity().getIntent().getStringExtra("hotelname");

        if (!hotelsName.isEmpty() && hotelsName != null) {
            LoadListRooms(hotelsName);
        }


//        luxury = "Luxury Room";
//        deluxe = "Deluxe Room";
//        avarage = "Avarage Room";
//
//        roomLikes = view.findViewById(R.id.txtLikes);
//        roomPrice = view.findViewById(R.id.txtPrice);
//        roomImage = view.findViewById(R.id.imgRoom);
//
//        roomLikes2 = view.findViewById(R.id.txtLikes2);
//        roomPrice2 = view.findViewById(R.id.txtPrice2);
//        roomImage2 = view.findViewById(R.id.imgRoom2);
//
//        roomLikes3 = view.findViewById(R.id.txtLikes3);
//        roomPrice3 = view.findViewById(R.id.txtPrice3);
//        roomImage3 = view.findViewById(R.id.imgRoom3);
//
//        roomImage.setOnClickListener(this);
//        roomImage2.setOnClickListener(this);
//        roomImage3.setOnClickListener(this);
//
//
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//
//        progressBar = view.findViewById(R.id.progressBar);
//
//        swipe = view.findViewById(R.id.swipe);

//        swipeRooms.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                LoadListRooms(hotelsName);
//            }
//        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        LoadListRooms(hotelsName);
    }

    private void LoadListRooms(String hotelsId) {
        adapter = new FirebaseRecyclerAdapter<Room, RoomsViewHolderF>(
                Room.class,
                R.layout.hotel,
                RoomsViewHolderF.class,
                rooms_tb.orderByChild("hotelName").equalTo(hotelsName)) {
            @Override
            protected void populateViewHolder(final RoomsViewHolderF viewHolder, Room model, int position) {
                viewHolder.Name.setText(model.getName());
                viewHolder.Price.setText(model.getPrice());
                Picasso.with(getContext()).load(model.getImage()).into(viewHolder.Image);

                final Room local = model;

                viewHolder.setItemCategoryClickListener(new ItemCategoryClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        TastyToast.makeText(getContext(), "" + local.getName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                        Intent bookRoom = new Intent(getContext(), BookRoom.class);
                        bookRoom.putExtra("Name", local.getName());
                        bookRoom.putExtra("Price", local.getPrice());
                        bookRoom.putExtra("Image", local.getImage());
                        startActivity(bookRoom);
                    }
                });
            }
        };
        recyclerRooms.setAdapter(adapter);
    }

//    private void LoadRooms() {
//
//        progressBar.setVisibility(View.VISIBLE);
//        RequestQueue requestQueue = VolleySingleton.getRequestQueue(getContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.BASE_HOTEL_ROOMS, new Response.Listener<String>() {
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
//
//                progressBar.setVisibility(View.GONE);
//                swipe.setRefreshing(false);
//                TastyToast.makeText(getContext(), "Volley Connection Error" + volleyError.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("hotelname", ExploreHotel.hotelsname);
//
//                //params.put("getrooms", "getrooms");
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//        requestQueue.start();
//    }

//    private void ParseData(String s) {
//        try {
//            JSONArray jsonArray = new JSONArray(s);
//
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//            roomPrice.setText(jsonObject.getString("luxuryprice"));
//            roomPrice2.setText(jsonObject.getString("deluxeprice"));
//            roomPrice3.setText(jsonObject.getString("avarageprice"));
//
//            pricelux = jsonObject.getString("luxuryprice");
//            imglux = jsonObject.getString("luxuryimage");
//
//            imgdel = jsonObject.getString("deluxeimage");
//            pricedel = jsonObject.getString("deluxeprice");
//
//            imgava = jsonObject.getString("avarageimage");
//            priceava = jsonObject.getString("avarageprice");
//
//            roomLikes.setText(jsonObject.getString("luxurylikes"));
//            roomLikes2.setText(jsonObject.getString("deluxelikes"));
//            roomLikes3.setText(jsonObject.getString("avaragelikes"));
//
//
//            String url = Urls.BASE_IMG_URL + jsonObject.getString("luxuryimage");
//            Picasso.with(getContext())
//                    .load(Uri.parse(url))
//                    .into(roomImage);
//
//            String url2 = Urls.BASE_IMG_URL + jsonObject.getString("deluxeimage");
//            Picasso.with(getContext())
//                    .load(Uri.parse(url2))
//                    .into(roomImage2);
//
//            String url3 = Urls.BASE_IMG_URL + jsonObject.getString("avarageimage");
//            Picasso.with(getContext())
//                    .load(Uri.parse(url3))
//                    .into(roomImage3);
//
//            //ArrayList <RoomModel> roomModels = new ArrayList<>();
////            for (int i=0; i<jsonArray.length(); i++){
////                JSONObject jsonObject =jsonArray.getJSONObject(i);
////                RoomModel roomModel = new RoomModel();
////
////                roomModel.setRoomName(jsonObject.getString("roomname"));
////                roomModel.setRoomLikes(jsonObject.getString("roomlikes"));
////                roomModel.setRoomPrice(jsonObject.getString("roomprice"));
////
////                roomModel.setImage(jsonObject.getString("roomimage"));
////
////                roomModels.add(roomModel);
////
////            }
//
////            recyclerView.setAdapter(new RoomsAdapter(getContext(),roomModels));
//            progressBar.setVisibility(View.GONE);
//
//            //     swipe.setRefreshing(false);
//
//
//        } catch (JSONException e) {
//            progressBar.setVisibility(View.GONE);
//            swipe.setRefreshing(false);
//
//            Log.e("Error", e.toString());
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                TastyToast.makeText(getContext(), jsonObject.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//
//            } catch (JSONException e1) {
//
//                TastyToast.makeText(getContext(), "Connection Error " + e1.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//
//                Log.e("Error", e1.toString());
//
//            }
//        }
//    }

    @Override
    public void onClick(View v) {


//        if (v.getId() == R.id.imgRoom) {
//            Intent intent = new Intent(getContext(), BookRoom.class);
//            intent.putExtra("room", luxury);
//            intent.putExtra("price", pricelux);
//            intent.putExtra("image", imglux);
//            startActivity(intent);
//            return;
//        }
//        if (v.getId() == R.id.imgRoom2) {
//            Intent intent = new Intent(getContext(), BookRoom.class);
//            intent.putExtra("room", deluxe);
//            intent.putExtra("price", pricedel);
//            intent.putExtra("image", imgdel);
//            startActivity(intent);
//            return;
//        }
//        if (v.getId() == R.id.imgRoom3) {
//            Intent intent = new Intent(getContext(), BookRoom.class);
//            intent.putExtra("room", avarage);
//            intent.putExtra("price", priceava);
//            intent.putExtra("image", imgava);
//            startActivity(intent);
//            return;
//        }

    }
}


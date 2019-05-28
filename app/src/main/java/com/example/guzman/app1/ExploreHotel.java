package com.example.guzman.app1;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ExploreHotel extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static String hotelsname;
    String hotelsId;

    RecyclerView recyclerView;

    ProgressBar progressBar;

    private TextView mTextMessage;

    String hotelSelected;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_hotel);

        if (getIntent() != null)
            hotelSelected = getIntent().getStringExtra("hotelname");

        if (!hotelSelected.isEmpty() && hotelSelected != null) {
            new Rooms(hotelSelected);
        }

        //        if (getIntent().hasExtra("hotelname")){
//
//            hotelsname = getIntent().getStringExtra("hotelname");
//
//        }

        /* LOADING THE DEFAULT FRGAMENTS*/

        loadFragment(new Rooms(hotelSelected));

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {

                case R.id.navigation_rooms:

                    fragment = new Rooms(hotelSelected);

                    break;
                case R.id.navigation_meals:

                    fragment = new Meals();
                    break;
//                case R.id.navigation_favorites:
//                    fragment = new Favorites();
//                    break;
            }
            return loadFragment(fragment);
        }
    };


    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_rooms:
                fragment = new Rooms(hotelSelected);
                break;

            case R.id.navigation_meals:
                fragment = new Meals();
                break;
//           case R.id.navigation_favorites:
//               fragment = new Favorites();
//               break;
        }
        return loadFragment(fragment);
    }

    @Override

    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

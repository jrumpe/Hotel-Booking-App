package com.example.guzman.app1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.guzman.app1.Common.currentUser;
import com.example.guzman.app1.Intrerface.ItemHotelClickListener;
import com.example.guzman.app1.adapters.HotelsViewHolder;
import com.example.guzman.app1.models.Hotels;
import com.example.guzman.app1.net.Urls;
import com.example.guzman.app1.net.VolleySingleton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseRecyclerAdapter<Hotels, HotelsViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference mHotels;
    FirebaseAuth mAuth;
    RecyclerView.LayoutManager laytmanager;
    private static final int TIME_LIMIT = 1500;
    private static long backPressed;
    RecyclerView recyclerHotels;
    ProgressBar progressBar;
    ViewPager viewPager;
    MaterialSearchBar materialSearchBar;
    SwipeRefreshLayout swipehotels;
    TextView txtFullName, txtUserEmail;
    RelativeLayout flContent;

    AdapterViewFlipper adapterViewFlipper;

    int[] IMAGES = {
            R.mipmap.hotel1,
            R.mipmap.hotel2,
            R.mipmap.hotel4,
            R.mipmap.hotel5,
            R.mipmap.hotel6,
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/")
        .setFontAttrId(R.attr.fontPath)
        .build());

        setContentView(R.layout.activity_main);


        adapterViewFlipper =(AdapterViewFlipper) findViewById(R.id.adapterViewFlipper);   CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), IMAGES);
        adapterViewFlipper.setAdapter(customAdapter);

        flContent = (RelativeLayout) findViewById(R.id.flContent);

        if (!isDeviceOnline())
            Snackbar.make(flContent, "No network connection available.", Snackbar.LENGTH_LONG)
                    .setAction("Retry", null).show();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mHotels = database.getReference("Hotels");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hotels");
        setSupportActionBar(toolbar);

        materialSearchBar = (findViewById(R.id.searchBar));
        recyclerHotels = (RecyclerView) findViewById(R.id.recycler_hotels);
        recyclerHotels.setHasFixedSize(true);

        // recyclerHotels.setLayoutManager(new GridLayoutManager(this,2));

        laytmanager = new LinearLayoutManager(this);
        recyclerHotels.setLayoutManager(laytmanager);


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching For Hotels", Snackbar.LENGTH_LONG)
                        .setAction("Retry", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        View headerView = navigationView.getHeaderView(0);
//        txtFullName = headerView.findViewById(R.id.txtName);
////        txtFullName = (TextView) headerView.findViewById(R.id.txtName);
////        txtUserEmail = (TextView) headerView.findViewById(R.id.txtEmail);
////        txtFullName.setText(currentUser.currentUser.getName());
////        txtUserEmail.setText(currentUser.currentUser.getEmail());

        viewPager = (ViewPager) findViewById(R.id.viewPager);


        ///////////////////////////////////////////////////////////////////////////

        recyclerHotels = findViewById(R.id.recycler_hotels);
        recyclerHotels.setItemAnimator(new DefaultItemAnimator());
        recyclerHotels.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        progressBar = findViewById(R.id.progressBar);
        swipehotels = findViewById(R.id.swipeRefresh);

        /////////////////////////////////////////////////////////////////////////


        loadFireBaseHotels();

//        Intent service = new Intent(MainActivity.this, ListenBookings.class);
//        startService(service);

        swipehotels.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadFireBaseHotels();

            }
        });

    }
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onStart() {

        super.onStart();

        loadFireBaseHotels();

        //LoadDbData();
    }

    private void loadFireBaseHotels() {
        swipehotels.setRefreshing(false);
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
                        TastyToast.makeText(MainActivity.this, "" + clickItem.getHotelName(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                        Intent exploreHotel = new Intent(MainActivity.this, ExploreHotel.class);
                        //get id and sent to new activity
                        exploreHotel.putExtra("hotelname", model.getHotelName());
                        // exploreHotel.putExtra("HotelsId", adapter.getRef(position).getKey());
                        startActivity(exploreHotel);
                    }
                });
            }
        };
        recyclerHotels.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        }
        if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {

            super.onBackPressed();

        }
        if (!(TIME_LIMIT + backPressed > System.currentTimeMillis())) {

            super.onBackPressed();

        } else {
            super.onBackPressed();
        }
    }

    public void onBackPress() {

        if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            TastyToast.makeText(MainActivity.this, "Press Back Again, To Exit", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
            finish();
        }
        backPressed = System.currentTimeMillis();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search)

            startActivity(new Intent(MainActivity.this, SearchActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_home:

                startActivity(new Intent(this, MainActivity.class));

                break;

            case R.id.nav_reservations:

                startActivity(new Intent(MainActivity.this, Reservation.class));

                break;
            case R.id.nav_orders:

                startActivity(new Intent(MainActivity.this, Orders.class));

                break;
            case R.id.nav_account:

                startActivity(new Intent(MainActivity.this, UserInfor.class));

                break;

            case R.id.nav_exit:

                logOutDialog();

                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void logOutDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.layout_logout, null);

        dialog.setTitle("Log Out");
        dialog.setMessage("Do you really want to log Out?");

        dialog.setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                finish();

            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setView(view);
        dialog.show();

    }


    private void home() {

        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

}
class CustomAdapter extends BaseAdapter {
    Context context;
    int[] images;
    LayoutInflater layoutInflater;

    public CustomAdapter(Context appContext, int[] images) {
        this.context = appContext;
        this.images = images;
        layoutInflater = (LayoutInflater.from(appContext));
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.layout_view_flipper_item, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageFlip);
        image.setImageResource(images[position]);
        return convertView;
    }
}
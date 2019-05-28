package com.example.guzman.app1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guzman.app1.Common.Config;
import com.example.guzman.app1.models.Request;
import com.example.guzman.app1.models.UserModel;
import com.example.guzman.app1.net.Urls;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.GregorianCalendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookRoom extends AppCompatActivity implements View.OnClickListener {
    String paymentstate;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference table_requests;

    private static final int PAYPAL_REQUEST_CODE = 9999;
    TextView edtArrival, edtDeparture;
    Button btnArrival, btnDeparture;

    String arrive, depart;

    Button btnBook;


    ImageView imgBook;
    String roomprice, roomimage, room;
    TextView roomname, price;

    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAY_PAL_CLIENT);

    String comment;

    String roomclass;


    String username, usermail, userphone;


    String uid;

    String priceyaroom;

    FirebaseUser user;

    TextView btnPricenew;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);

        btnPricenew = (TextView) findViewById(R.id.txtPriceBtn);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            username = user.getDisplayName();
            usermail = user.getEmail();
            userphone = user.getPhoneNumber();

            uid = user.getUid();


        } else {
            Toast.makeText(this, "Please login to book", Toast.LENGTH_SHORT).show();
        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        table_requests = database.getReference().child("Bookings");

        btnBook = findViewById(R.id.btnBook);

        if (getIntent().hasExtra("Price")) {
            roomprice = getIntent().getStringExtra("Price");
        }
        if (getIntent().hasExtra("Image")) {
            roomimage = getIntent().getStringExtra("Image");
        }
        if (getIntent().hasExtra("Name")) {
            room = getIntent().getStringExtra("Name");
        } else {
            TastyToast.makeText(BookRoom.this, "No Room Selected", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            finish();
        }

        btnBook.setOnClickListener(this);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        imgBook = findViewById(R.id.imgBook);

        edtArrival = findViewById(R.id.txtArrival);
        edtDeparture = findViewById(R.id.txtDeparture);

        btnArrival = findViewById(R.id.btnArrival);
        btnDeparture = findViewById(R.id.btnDeparture);

        roomname = findViewById(R.id.tvRoomName);
        price = findViewById(R.id.tvRoomPrice);

        btnDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Departure();

            }
        });
        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrivalDate();

            }
        });

        Picasso.with(getApplicationContext()).load(roomimage).into(imgBook);
//        String url3 = Urls.BASE_IMG_URL + roomimage;
//        Picasso.with(BookRoom.this)
//                .load(Uri.parse(url3))
//                .into(imgBook);

        roomname.setText(room);
        price.setText(roomprice);
        btnPricenew.setText(roomprice);

    }

    private void Departure() {

        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        final int day, month, year;

        day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        month = gregorianCalendar.get(Calendar.MONTH);
        year = gregorianCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookRoom.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtDeparture.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();

    }

    private void ArrivalDate() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        final int day, month, year;
        day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        month = gregorianCalendar.get(Calendar.MONTH);
        year = gregorianCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookRoom.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtArrival.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();

//        arrive = edtArrival.getText().toString();
//        depart = edtDeparture.getText().toString();


    }

    @Override
    public void onClick(View v) {
        arrive = edtArrival.getText().toString();
        depart = edtDeparture.getText().toString();
        if (TextUtils.isEmpty(arrive)) {
            TastyToast.makeText(BookRoom.this, "Select arrival Date", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            edtArrival.setError("Select arrival Date");
            edtArrival.requestFocus();
            return;
        } else if (TextUtils.isEmpty(depart)) {
            TastyToast.makeText(BookRoom.this, "Select Departure Date", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            edtDeparture.setError("Select arrival Date");
            edtDeparture.requestFocus();
            return;
        } else if (depart.equalsIgnoreCase(arrive)) {
            TastyToast.makeText(BookRoom.this, "Cannot arrive and depart on same day", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            edtDeparture.setError("Cannot arrive and depart on same day");
            edtDeparture.requestFocus();
            return;
        }

        priceyaroom = price.getText().toString();

        roomclass = roomname.getText().toString();

        String formatAmount = priceyaroom
                .replace("$.", "")
                .replace(",", "");
        float amount = Float.parseFloat(formatAmount);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount),
                "USD",
                "Kisii Hotels Booking",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paydetail = confirmation.toJSONObject().toString(4);
                        JSONObject jsonObject = new JSONObject(paydetail);
                        paymentstate = jsonObject.getJSONObject("response").getString("state");
                        Request request = new Request( username, userphone, usermail,  room, priceyaroom, arrive, depart, "0" , comment,
                                jsonObject.getJSONObject("response").getString("state")
                        );

//                        String order_number = String.valueOf(System.currentTimeMillis());
//                        request.child(order_number);
//                        sendnotification(order_number);

                        table_requests.child(uid).setValue(request);
                        TastyToast.makeText(BookRoom.this, "Thank You, You have successfully Reserved A room !!",
                                TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                        finish();
                        Intent print = new Intent(BookRoom.this, Pdf.class);
                        print.putExtra("email", usermail);
                        print.putExtra("room", room);
                        print.putExtra("arrival", arrive);
                        print.putExtra("departure", depart);
                        print.putExtra("price", priceyaroom);
                        startActivity(print);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    TastyToast.makeText(BookRoom.this, "Payment Cancelled", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                    TastyToast.makeText(BookRoom.this, "Invalid payment", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }
            }
        }
    }
}


//We will use system.currentmilli to key in
//String order_number = String.valueof

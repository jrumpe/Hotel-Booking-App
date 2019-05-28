package com.example.guzman.app1;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guzman.app1.Common.Config;
import com.example.guzman.app1.models.FoodOrder;
import com.example.guzman.app1.models.Request;
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
import com.rengwuxian.materialedittext.MaterialEditText;
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

public class OrderFood extends AppCompatActivity {

    String paymentstate;

    private String street;
    private String road;
    private String house;
    private String comment;
    private String status;
    private String paymentState;

    ImageView imgOrder;
    TextView tvFoodName, tvFoodPrice;

    String img, foodname, price;

    String priceyafood, nameyafood;

    Button btnOrder;

    EditText edtRoad, edtStreet, edtHouse;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference table_Orders;

    private static final int PAYPAL_REQUEST_CODE = 9999;
    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAY_PAL_CLIENT);

    String username, usermail, userphone, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        table_Orders = database.getReference().child("Food Orders");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            username = user.getDisplayName();
            usermail = user.getEmail();
            userphone = user.getPhoneNumber();

            uid = user.getUid();


        } else {
            Toast.makeText(this, "Please login to Order Food", Toast.LENGTH_SHORT).show();
        }

//        edtHouse = findViewById(R.id.edtHouse);
//        edtRoad = findViewById(R.id.edtRoad);
//        edtStreet = findViewById(R.id.edtStreet);

        imgOrder = findViewById(R.id.imgOrder);
        tvFoodName = findViewById(R.id.tvFoodName);
        tvFoodPrice = findViewById(R.id.tvFoodPrice);

        btnOrder = (Button) findViewById(R.id.btnOrderFood);

        if (getIntent().hasExtra("foodname")) {

            foodname = getIntent().getStringExtra("foodname");
        }
        if (getIntent().hasExtra("foodprice")) {

            price = getIntent().getStringExtra("foodprice");
        }
        if (getIntent().hasExtra("foodimage")) {

            img = getIntent().getStringExtra("foodimage");


        } else {
            TastyToast.makeText(OrderFood.this, "No Food Selected", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            finish();
        }

        tvFoodPrice.setText(price);
        tvFoodName.setText(foodname);

        String url = Urls.BASE_FOOD_IMG_URL + img;

        Picasso.with(this)
                .load(Uri.parse(url))
                .into(imgOrder);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressDialog();
            }
        });

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


    }

//
//    @Override
//    public void onClick(View v) {
//
////        priceyafood = tvFoodPrice.getText().toString();
////        nameyafood = tvFoodName.getText().toString();
//
//        addressDialog();
//
//    }

    private void addressDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(OrderFood.this);
        View view = getLayoutInflater().inflate(R.layout.layout_address, null);

        dialog.setTitle("Enter Your Address");

        final MaterialEditText edtStreet = (MaterialEditText) view.findViewById(R.id.edtStreet);
        final MaterialEditText edtRoad = (MaterialEditText) view.findViewById(R.id.edtRoad);
        final MaterialEditText edtHouseno = (MaterialEditText) view.findViewById(R.id.edtHouse);
        final MaterialEditText edtComment = (MaterialEditText) view.findViewById(R.id.edtComment);


        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                priceyafood = tvFoodPrice.getText().toString();
                nameyafood = tvFoodName.getText().toString();
                street = edtStreet.getText().toString();
                road = edtRoad.getText().toString();
                house = edtHouseno.getText().toString();
                comment = edtComment.getText().toString();

                String formatAmount = priceyafood
                        .replace("$.", "")
                        .replace(",", "");

                float amount = Float.parseFloat(formatAmount);

                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount),

                        "USD",
                        "Kisii Hotels Food Order",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);

//                if (TextUtils.isEmpty(road)) {
//                    TastyToast.makeText(OrderFood.this, "Enter Road", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//                    return;
//                } else if (TextUtils.isEmpty(street)) {
//                    TastyToast.makeText(OrderFood.this, "Enter Street", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//                    return;
//                } else if (TextUtils.isEmpty(house)) {
//                    TastyToast.makeText(OrderFood.this, "Enter house", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//                    return;
//                } else if (TextUtils.isEmpty(comment)) {
//                    TastyToast.makeText(OrderFood.this, "Enter comment", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
//                    return;
//                } else {
//
//
//                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        dialog.setView(view);
        dialog.show();

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


                        FoodOrder foodOrder = new FoodOrder(
                                username,
                                usermail,
                                userphone,
                                priceyafood,
                                nameyafood,
                                street,
                                road,
                                house,
                                comment,
                                "0", // status
                                jsonObject.getJSONObject("response").getString("state")
                        );

                        table_Orders.child(uid).setValue(foodOrder);

                        TastyToast.makeText(OrderFood.this, "Thank you, you have placed your Order", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                       // Toast.makeText(this, "Thank you, you have placed your booking", Toast.LENGTH_SHORT).show();
                        finish();

//                        myLocalCopy();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {

                    TastyToast.makeText(OrderFood.this, "Payment Cancelled", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                    //Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                    TastyToast.makeText(OrderFood.this, "Invalid payment", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                 //   Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}

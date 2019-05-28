package com.example.guzman.app1;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.guzman.app1.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sdsmdg.tastytoast.TastyToast;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtEmail, edtPass, edtPass2, edtPhone, edtName;
    Button register;
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    DatabaseReference table_users;
    ProgressDialog progressDialog;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_Sign_Up);
        if (!isDeviceConnected())
            Snackbar.make(coordinatorLayout, "No network connection available.", Snackbar.LENGTH_LONG)
                    .setAction("Retry", null).show();

        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        table_users = db.getReference("users");


        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtPass = (MaterialEditText) findViewById(R.id.edtPass);
        edtPass2 = (MaterialEditText) findViewById(R.id.edtPass2);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void registerUser() {

        progressDialog = new ProgressDialog(SignUp.this);

        progressDialog.setMessage("Registering ... Please wait");
        progressDialog.show();

        final String email, pass, pass2, name, phone;

        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        pass2 = edtPass2.getText().toString();
        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        progressDialog.dismiss();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Enter Email");
            edtEmail.requestFocus();
            TastyToast.makeText(SignUp.this, "Enter Email", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (TextUtils.isEmpty(pass)) {
            edtPass.setError("Enter Password");
            edtPass.requestFocus();
            TastyToast.makeText(SignUp.this, "Enter Password", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (TextUtils.isEmpty(pass2)) {
            edtPass2.setError("Confirm Password");
            edtPass2.requestFocus();
            TastyToast.makeText(SignUp.this, "Confirm Password", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Enter Name");
            edtName.requestFocus();
            TastyToast.makeText(SignUp.this, "Enter Name", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Enter Phone");
            edtPhone.requestFocus();
            TastyToast.makeText(SignUp.this, "Enter Phone", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (pass.length() < 5) {
            edtPass.setError("Password too short");
            edtPass.requestFocus();
            TastyToast.makeText(SignUp.this, "Password too short", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (pass2.length() < 5) {
            edtPass2.setError("Password too short");
            edtPass2.requestFocus();
            TastyToast.makeText(SignUp.this, "Password too short", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (!edtPass.getText().toString().equalsIgnoreCase(edtPass2.getText().toString())) {
            edtPass2.setError("Password Don't match");
            TastyToast.makeText(SignUp.this, "Password Don't match", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            edtPass2.requestFocus();
            return;
        }
        // Create user
        if (!email.isEmpty() && !pass.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {

            progressDialog.dismiss();
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            UserModel user = new UserModel(name, phone, email);
                            table_users.child(mAuth.getUid()).setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();

                                            TastyToast.makeText(SignUp.this, "Registration is Successfully !",
                                                    TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            TastyToast.makeText(SignUp.this, "Registration Failed !",
                                                    TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            TastyToast.makeText(SignUp.this, "Registration Failed !", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                        }
                    });

        } else {
            TastyToast.makeText(SignUp.this, "Could not complete Registration", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

        }

    }

    private boolean isDeviceConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

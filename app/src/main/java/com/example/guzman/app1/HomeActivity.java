package com.example.guzman.app1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guzman.app1.models.User;
import com.example.guzman.app1.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sdsmdg.tastytoast.TastyToast;

import com.example.guzman.app1.Common.currentUser;


public class HomeActivity extends AppCompatActivity {

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////55555555555555555555555555555
    FirebaseAuth mAuth;
    FirebaseDatabase db;

    private Button btnSignin, btnRegister;

    TextView txtMotto;

    RelativeLayout rootLayout;
    DatabaseReference table_users;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        if (!isDeviceOnline())
            Snackbar.make(rootLayout, "No network connection available.", Snackbar.LENGTH_LONG)
                    .setAction("Retry", null).show();

        txtMotto = (TextView) findViewById(R.id.txtMotto);
        txtMotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(HomeActivity.this, MainActivity.class));
              //  finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        btnSignin = (Button) findViewById(R.id.button_signin);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(HomeActivity.this, SignIn.class));
                signInUser();
                //loginUser();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, SignUp.class));
              //  registerUser();
            }
        });
    }

    private void signInUser() {
        Intent login = new Intent(HomeActivity.this, SignIn.class);
        startActivity(login);

    }

    private void registerUser() {
//        final FirebaseDatabase db = FirebaseDatabase.getInstance();
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        table_users = db.getReference("users");

        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.layout_register, null);

        dialog.setTitle("REGISTER");
        dialog.setMessage("Use your Email to Register");

        final MaterialEditText edtEmail = (MaterialEditText) mView.findViewById(R.id.edtEmail);
        final MaterialEditText edtPass = (MaterialEditText) mView.findViewById(R.id.edtPass);
        final MaterialEditText edtPass2 = (MaterialEditText) mView.findViewById(R.id.edtPass2);
        final MaterialEditText edtName = (MaterialEditText) mView.findViewById(R.id.edtName);
        final MaterialEditText edtPhone = (MaterialEditText) mView.findViewById(R.id.edtPhone);


        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setMessage("Registering ... Please wait");
                progressDialog.show();

                final String email, pass, pass2, name, phone;

                email = edtEmail.getText().toString();
                pass = edtPass.getText().toString();
                pass2 = edtPass2.getText().toString();
                name = edtName.getText().toString();
                phone = edtPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {

                    edtEmail.setError("Enter Email");
                    edtEmail.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(pass)) {
                    edtPass.setError("Enter Password");
                    edtPass.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(pass2)) {
                    edtPass2.setError("Confirm Password");
                    edtPass2.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(name)) {
                    edtName.setError("Enter Name");
                    edtName.requestFocus();
                    return;

                }
                if (TextUtils.isEmpty(phone)) {
                    edtPhone.setError("Enter Phone");
                    edtPhone.requestFocus();
                    return;

                }
                if (pass.length() < 5) {
                    edtPass.setError("Password too short");
                    edtPass.requestFocus();
                    return;

                }
                if (pass2.length() < 5) {
                    edtPass2.setError("Password too short");
                    edtPass2.requestFocus();
                    return;

                }
                if (!edtPass.getText().toString().equalsIgnoreCase(edtPass2.getText().toString())) {
                    edtPass2.setError("Password Dont match");
                    edtPass2.requestFocus();
                    return;
                } else {
                    // Create user

                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    final FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                    final DatabaseReference table_users = db.getReference("users");

                                    UserModel user = new UserModel(name, phone, email);
                                    table_users.child(mAuth.getUid()).setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressDialog.dismiss();

                                                    TastyToast.makeText(HomeActivity.this, "Registration is Successfully !" , TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                    loginUser();

                                                   // Toast.makeText(HomeActivity.this, "User Successfully registered", Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(HomeActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(HomeActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                }

            }
        });


        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.setView(mView);
        dialog.show();

    }

    public void loginUser() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.layout_signin, null);

        dialog.setTitle("SIGN IN");
        dialog.setMessage("Use your Email to Sign In");


        final MaterialEditText edtEmail = (MaterialEditText) mView.findViewById(R.id.edtEmail);
        final MaterialEditText edtPass = (MaterialEditText) mView.findViewById(R.id.edtPass);

        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setMessage("Signing in ...Please wait");


                final String email, pass;

                email = edtEmail.getText().toString();
                pass = edtPass.getText().toString();

                progressDialog.show();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(HomeActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {

                    TastyToast.makeText(HomeActivity.this, "Enter Password" , TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                  //  Toast.makeText(HomeActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    progressDialog.dismiss();

                    //login user
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {


                                    Intent main = new Intent(HomeActivity.this, MainActivity.class);

                                    currentUser.currentUser = user;

                                    startActivity(main);

                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();

                                    TastyToast.makeText(HomeActivity.this, "Failed" + e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                                  //  Toast.makeText(HomeActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });


        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.setView(mView);
        dialog.show();
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

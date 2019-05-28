package com.example.guzman.app1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guzman.app1.Common.currentUser;
import com.example.guzman.app1.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase db;

    private Button btnSignin;

    TextView  forgot;

    RelativeLayout rootLayout;
    DatabaseReference table_users;

    User user;
    MaterialEditText edtEmail, edtPass;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.rootSignIn);
        if (!isDeviceOnline())
            Snackbar.make(linearLayout, "No network connection available", Snackbar.LENGTH_LONG)
                    .setAction("Retry", null).show();

        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtPass = (MaterialEditText) findViewById(R.id.edtPass);
        forgot = (TextView) findViewById(R.id.txt_forgot);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ProgressDialog dialog = new ProgressDialog(this);
        //  dialog.setTitle("SIGN IN");
        dialog.setMessage("Logging in...Please wait");

        btnSignin = (Button) findViewById(R.id.btnLogIn);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.layout_reset_password, null);

        dialog.setTitle("Reset Password");
        dialog.setMessage("Enter your email address");
        dialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MaterialEditText email = (MaterialEditText) view.findViewById(R.id.edtEmail);
                String mail = email.getText().toString();

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

    private void LoginUser() {
        final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setMessage("Signing in ...Please wait");
        progressDialog.show();

        final String email, pass;

        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();

        progressDialog.dismiss();
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Enter Email");
            edtEmail.requestFocus();
            TastyToast.makeText(SignIn.this, "Enter Email", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            edtPass.setError("Enter Password");
            edtPass.requestFocus();
            TastyToast.makeText(SignIn.this, "Enter Password", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (pass.length() < 5) {
            edtPass.setError("Password too short");
            edtPass.requestFocus();
            TastyToast.makeText(SignIn.this, "Password must be at least 5 characters", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }if (!emailValidator(email)){
            edtEmail.setError("Enter Valid Email");
            edtEmail.setError("Enter a valid Email Address");
            edtEmail.requestFocus();
            TastyToast.makeText(SignIn.this, "Enter a valid Email Address", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;
        }if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && pass.length() >5 && emailValidator(email)){
            progressDialog.dismiss();

            //login user
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            Intent main = new Intent(SignIn.this, MainActivity.class);

                            currentUser.currentUser = user;

                            startActivity(main);

                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            TastyToast.makeText(SignIn.this, "Failed" + e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                            //  Toast.makeText(HomeActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            TastyToast.makeText(SignIn.this, "Failed", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

        }

    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    //Email Validator using Regex
    public boolean emailValidator(String email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN ="^[_A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(email);
        return matcher.matches();

    }
}

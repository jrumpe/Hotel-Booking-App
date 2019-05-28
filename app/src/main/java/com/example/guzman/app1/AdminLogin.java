package com.example.guzman.app1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guzman.app1.Common.currentUser;
import com.example.guzman.app1.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminLogin extends AppCompatActivity {
    MaterialEditText Email, Pass;
    private Button btnSignin;
    TextView forgot;

    FirebaseAuth mAuth;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();

        Email = (MaterialEditText) findViewById(R.id.edtEmail);
        Pass = (MaterialEditText) findViewById(R.id.edtPass);
        forgot = (TextView) findViewById(R.id.txt_forgot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void LoginUser() {
        final ProgressDialog progressDialog = new ProgressDialog(AdminLogin.this);
        progressDialog.setMessage("Signing in ...Please wait");
        progressDialog.show();

        final String email, pass;

        email = Email.getText().toString();
        pass = Pass.getText().toString();

        progressDialog.dismiss();
        if (TextUtils.isEmpty(email)) {
            Email.setError("Enter Email");
            Email.requestFocus();
            TastyToast.makeText(AdminLogin.this, "Enter Email", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Pass.setError("Enter Password");
            Pass.requestFocus();
            TastyToast.makeText(AdminLogin.this, "Enter Password", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }
        if (pass.length() < 5) {
            Pass.setError("Password too short");
            Pass.requestFocus();
            TastyToast.makeText(AdminLogin.this, "Password must be at least 5 characters", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;

        }if (!emailValidator(email)){
            Email.setError("Enter Valid Email");
            Email.setError("Enter a valid Email Address");
            Email.requestFocus();
            TastyToast.makeText(AdminLogin.this, "Enter a valid Email Address", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            return;
        }if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && pass.length() >5 && emailValidator(email)){
            progressDialog.dismiss();

            //login user
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            Intent main = new Intent(AdminLogin.this, AdminHome.class);
                            currentUser.currentUser = user;
                            startActivity(main);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            TastyToast.makeText(AdminLogin.this, "Failed" + e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

                            //  Toast.makeText(HomeActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            TastyToast.makeText(AdminLogin.this, "Failed", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();

        }
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

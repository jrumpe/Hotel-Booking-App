package com.example.guzman.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.guzman.app1.Common.currentUser;
import com.example.guzman.app1.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfor extends AppCompatActivity {

    public TextView txtEmail;
    public TextView txtPhone;
    public TextView txtNameUser;

    DatabaseReference mUsers;
    FirebaseAuth mAuth;

    String uid0;

    User user;
    private static final String TAG = "UserInfor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        mAuth = FirebaseAuth.getInstance();
        uid0 = mAuth.getUid();
        mUsers = FirebaseDatabase.getInstance().getReference().child("users").child(uid0);

        txtEmail = (TextView) findViewById(R.id.txtUserEmail);
        txtPhone = (TextView) findViewById(R.id.txtUserPhone);
        txtNameUser = (TextView) findViewById(R.id.name);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                user = dataSnapshot.getValue(User.class);
                txtEmail.setText(user.getEmail());
                txtNameUser.setText(user.getName());
                txtPhone.setText(user.getPhone());
                // ...
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mUsers.addValueEventListener(userListener);

    }
}

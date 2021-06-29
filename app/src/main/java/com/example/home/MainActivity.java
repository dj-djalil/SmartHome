package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText userName;
    EditText userPassword;
    final List<User> usersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference().child("Users");
         // invoced any time data changes
         myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(  DataSnapshot snapshot) {
                 // get all children in this level
                 Iterable<DataSnapshot> children = snapshot.getChildren();
                 // shake hans with each of them
                 for (DataSnapshot child: children) {
                     User user = child.getValue(User.class);
                     usersList.add(user);

                 }
             }

             @Override
             public void onCancelled(  DatabaseError error) {

             }
         });
    }


    public void SignIn(View view){

        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.userPassword);
        if(userName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter a user name ", Toast.LENGTH_SHORT).show();
        }else  if(userPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter a password  ", Toast.LENGTH_SHORT).show();
        }else {
            boolean existe = false;
            for (User user : usersList) {
                if (user.getUname().equals(userName.getText().toString()) && user.getPassword().equals(userPassword.getText().toString())) {
                    existe = true;
                }
            }

            if (existe) {
                Intent intent = new Intent(this, PrincipalActivity.class);
                startActivity(intent);

            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_baseline_security_24)
                        .setTitle("SIGN IN FAILED")
                        .setMessage("Invalid user name or password ")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        }

    }




}
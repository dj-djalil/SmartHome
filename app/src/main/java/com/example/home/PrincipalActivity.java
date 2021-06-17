package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalActivity extends AppCompatActivity {

    TextView temperature;
    TextView humidity ;
    Button openGarage;
    Button closeGarage;
    FirebaseDatabase database;
    DatabaseReference myRef_Temperature;
    DatabaseReference myRef_Humidity;
    DatabaseReference myRef_Garage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
          // Instance of data base
        database = FirebaseDatabase.getInstance();

        // get temperature and humidity from firebase
        myRef_Temperature = database.getReference("Temperature/temp");
        myRef_Humidity = database.getReference("Temperature/humidity");
        myRef_Garage= database.getReference("Garage");
        // get text views temp and humidity
        temperature =(TextView)findViewById(R.id.temperature);
        humidity =(TextView)findViewById(R.id.Humidity);

        myRef_Temperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Float value = dataSnapshot.getValue(Float.class);
                temperature.setText(value+ "");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                }
        });

        myRef_Humidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Float value = dataSnapshot.getValue(Float.class);
                humidity.setText(value+ "");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }


    // open garage
    public void openG (View view){
        openGarage =(Button)findViewById(R.id.G_open);
        myRef_Garage.setValue("1");
    }

    // close garage
    public void closeG (View view){
        openGarage =(Button)findViewById(R.id.G_close);
        myRef_Garage.setValue("0");
    }
}
package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LedControlActivity extends AppCompatActivity {

    Button bedRoomButton;
    Button kitchenButton;
    Button corridorButton;
    Button garageButton;
    FirebaseDatabase database;
    DatabaseReference myRef_bedRoom_led;
    DatabaseReference myRef_kitchen_led;
    DatabaseReference myRef_garage_led;
    DatabaseReference myRef_Corridor_led;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);

        database = FirebaseDatabase.getInstance();
        myRef_bedRoom_led  = database.getReference().child("Leds/led1");
        myRef_kitchen_led = database.getReference().child("Leds/led2");
        myRef_Corridor_led = database.getReference().child("Leds/led3");
        myRef_garage_led = database.getReference().child("Leds/led4");


        bedRoomButton = (Button)findViewById(R.id.bedRoomButton);
        kitchenButton = (Button)findViewById(R.id.kitchenButton);
        corridorButton = (Button)findViewById(R.id.corridorButton);
        garageButton = (Button)findViewById(R.id.garageButton);



        // Read from the database
        myRef_bedRoom_led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    bedRoomButton.setText("ON");
                    bedRoomButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    bedRoomButton.setText("OFF");
                    bedRoomButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        // Read from the database
        myRef_kitchen_led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    kitchenButton.setText("ON");
                    kitchenButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    kitchenButton.setText("OFF");
                    kitchenButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        // Read from the database
        myRef_Corridor_led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    corridorButton.setText("ON");
                    corridorButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    corridorButton.setText("OFF");
                    corridorButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        // Read from the database
        myRef_garage_led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    garageButton.setText("ON");
                    garageButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    garageButton.setText("OFF");
                    garageButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


    }

    public void setBedRoomLight(View view) {
        String str=bedRoomButton.getText().toString();
        if(str.equals("ON")){
            bedRoomButton.setText("OFF");
            bedRoomButton.setTextColor(getResources().getColor(R.color.close));
            myRef_bedRoom_led.setValue("0");

        }else if(str.equals("OFF")){
            bedRoomButton.setText("ON");
            bedRoomButton.setTextColor(getResources().getColor(R.color.white));
            myRef_bedRoom_led.setValue("1");
        }
    }

    public void setKitchenLight(View view) {
        String str=kitchenButton.getText().toString();
        if(str.equals("ON")){
            kitchenButton.setText("OFF");
            kitchenButton.setTextColor(getResources().getColor(R.color.close));
            myRef_kitchen_led.setValue("0");

        }else if(str.equals("OFF")){
            kitchenButton.setText("ON");
            kitchenButton.setTextColor(getResources().getColor(R.color.white));
            myRef_kitchen_led.setValue("1");
        }
    }

    public void setCorridorLight(View view) {
        String str=corridorButton.getText().toString();
        if(str.equals("ON")){
            corridorButton.setText("OFF");
            corridorButton.setTextColor(getResources().getColor(R.color.close));
            myRef_Corridor_led.setValue("0");

        }else if(str.equals("OFF")){
            corridorButton.setText("ON");
            corridorButton.setTextColor(getResources().getColor(R.color.white));
            myRef_Corridor_led.setValue("1");
        }
    }

    public void setGarageLight(View view) {
        String str=garageButton.getText().toString();
        if(str.equals("ON")){
            garageButton.setText("OFF");
            garageButton.setTextColor(getResources().getColor(R.color.close));
            myRef_garage_led.setValue("0");

        }else if(str.equals("OFF")){
            garageButton.setText("ON");
            garageButton.setTextColor(getResources().getColor(R.color.white));
            myRef_garage_led.setValue("1");
        }
    }
}
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

public class controlePortesActivity extends AppCompatActivity {

    Button mainDoorButton;
    Button kitchenWindowButton;
    FirebaseDatabase database;
    DatabaseReference myRef_door;
    DatabaseReference myRef_kitchen_window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle_portes);
        database = FirebaseDatabase.getInstance();
        myRef_door = database.getReference("Portes_Fenetres/porte");
        myRef_kitchen_window = database.getReference("Portes_Fenetres/fenetre");

        mainDoorButton = (Button)findViewById(R.id.mainDoorButton);
        kitchenWindowButton = (Button)findViewById(R.id.kitchenWindowButton);

        myRef_door.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    mainDoorButton.setText("OPEN");
                    mainDoorButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    mainDoorButton.setText("CLOSE");
                    mainDoorButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        myRef_kitchen_window.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    kitchenWindowButton.setText("OPEN");
                    kitchenWindowButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    kitchenWindowButton.setText("CLOSE");
                    kitchenWindowButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public void setKitchenWindow(View view) {
        String str=kitchenWindowButton.getText().toString();
        if(str.equals("OPEN")){
            kitchenWindowButton.setText("CLOSE");
            kitchenWindowButton.setTextColor(getResources().getColor(R.color.close));
            myRef_kitchen_window.setValue("0");

        }else if(str.equals("CLOSE")){
            kitchenWindowButton.setText("OPEN");
            kitchenWindowButton.setTextColor(getResources().getColor(R.color.white));
            myRef_kitchen_window.setValue("1");
        }

    }

    public void setMainDoor(View view) {
        String str= mainDoorButton.getText().toString();
        if(str.equals("OPEN")){
            mainDoorButton.setText("CLOSE");
            mainDoorButton.setTextColor(getResources().getColor(R.color.close));
            myRef_door.setValue("0");

        }else if(str.equals("CLOSE")){
            mainDoorButton.setText("OPEN");
            mainDoorButton.setTextColor(getResources().getColor(R.color.white));
            myRef_door.setValue("1");
        }

    }
}
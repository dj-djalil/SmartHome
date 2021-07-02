package com.example.home;

import androidx.annotation.NonNull;
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

public class FanActivity extends AppCompatActivity {

    Button fanAutoButton;
    Button fanStateButton;
    Button increaseButton;
    Button decreaseButton;
    TextView idealTemperatureTextView;
    final Integer a=new Integer(0);

    FirebaseDatabase database;
    DatabaseReference myRef_Ventilateur;
    DatabaseReference myRef_Ventilateur_Auto;
    DatabaseReference myRef_IdealTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        // Instance of data base
        database = FirebaseDatabase.getInstance();
        myRef_Ventilateur= database.getReference("Ventilateur/etat");
        myRef_Ventilateur_Auto= database.getReference("Ventilateur/auto");
        myRef_IdealTemp=database.getReference("Ventilateur/idealValue");

        // get views
        idealTemperatureTextView =(TextView)findViewById(R.id.idealTempValue);
        increaseButton =(Button) findViewById(R.id.increaseButton);
        decreaseButton =(Button) findViewById(R.id.decreaseButton);
        fanStateButton =(Button) findViewById(R.id.fanButton);
        fanAutoButton =(Button) findViewById(R.id.fanAutoButton);


        myRef_Ventilateur_Auto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    fanAutoButton.setText("ON");
                    fanAutoButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    fanAutoButton.setText("OFF");
                    fanAutoButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        myRef_Ventilateur.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    fanStateButton.setText("ON");
                    fanStateButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    fanStateButton.setText("OFF");
                    fanStateButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



        myRef_IdealTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                Integer value = snapshot.getValue(Integer.class);
                idealTemperatureTextView.setText(value+"");
                //idealTemperatureTextView.setText(value+" Cº");
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }

    public void increase(View view) {
       int a=  Integer.parseInt(idealTemperatureTextView.getText().toString());
        a++;
        myRef_IdealTemp.setValue(a);
    }

    public void decrease(View view) {
        int a=  Integer.parseInt(idealTemperatureTextView.getText().toString());
        a--;
        myRef_IdealTemp.setValue(a);
    }

    public void setFanAuto(View view) {
        String str=fanAutoButton.getText().toString();
        if(str.equals("ON")){
            fanAutoButton.setText("OFF");
            fanAutoButton.setTextColor(getResources().getColor(R.color.close));
            myRef_Ventilateur_Auto.setValue("0");

        }else if(str.equals("OFF")){
            fanAutoButton.setText("ON");
            fanAutoButton.setTextColor(getResources().getColor(R.color.white));
            myRef_Ventilateur_Auto.setValue("1");
        }
    }

    public void setFanState(View view) {
        String str=fanStateButton.getText().toString();
        if(str.equals("ON")){
            fanStateButton.setText("OFF");
            fanStateButton.setTextColor(getResources().getColor(R.color.close));
            myRef_Ventilateur.setValue("0");

        }else if(str.equals("OFF")){
            fanStateButton.setText("ON");
            fanStateButton.setTextColor(getResources().getColor(R.color.white));
            myRef_Ventilateur.setValue("1");
        }
    }
}
package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
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
    Button fanAutoButton;
    Button ledAutoButton;
    FirebaseDatabase database;
    DatabaseReference myRef_Temperature;
    DatabaseReference myRef_Humidity;
    DatabaseReference myRef_Garage;
    DatabaseReference myRef_Ventilateur;
    DatabaseReference myRef_Ventilateur_Auto;
    DatabaseReference myRef_Led_Auto;
    DatabaseReference myRef_gaz_leak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
          // Instance of data base
        database = FirebaseDatabase.getInstance();

        // get data from firebase
        myRef_Temperature = database.getReference("Temperature/temp");
        myRef_Humidity = database.getReference("Temperature/humidity");
        myRef_Garage= database.getReference("Garage");
        myRef_Ventilateur= database.getReference("Ventilateur/etat");
        myRef_Ventilateur_Auto= database.getReference("Ventilateur/auto");
        myRef_Led_Auto= database.getReference("Leds/led3_auto");
        myRef_gaz_leak= database.getReference("Gaz/fuite");



        // get text views temp and humidity
        temperature =(TextView)findViewById(R.id.temperature);
        humidity =(TextView)findViewById(R.id.Humidity);

        // get Buttons
        ledAutoButton = (Button)findViewById(R.id.ledAutoButton);
        fanAutoButton = (Button)findViewById(R.id.fanAutoButton);

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



        myRef_Led_Auto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    ledAutoButton.setText("ON");
                    ledAutoButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                     ledAutoButton.setText("OFF");
                    ledAutoButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        // Notification
        myRef_gaz_leak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    notification();
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
              // nothing
            }
        });

    }


    // open garage
    public void openG (View view){
        myRef_Garage.setValue("1");
    }

    // close garage
    public void closeG (View view){
        myRef_Garage.setValue("0");
    }
    // Ventilateur
    public void fanOff(View view) {
   myRef_Ventilateur.setValue("0");
    }

    public void fanOn(View view) {
        myRef_Ventilateur.setValue("1");

    }

    public void fanSetAuto(View view) {
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

   // led auto Button
    public void ledSetAuto(View view) {
        String str=ledAutoButton.getText().toString();
        if(str.equals("ON")){
            ledAutoButton.setText("OFF");
            ledAutoButton.setTextColor(getResources().getColor(R.color.close));
            myRef_Led_Auto.setValue("0");

        }else if(str.equals("OFF")){
            ledAutoButton.setText("ON");
            ledAutoButton.setTextColor(getResources().getColor(R.color.white));
            myRef_Led_Auto.setValue("1");
        }
    }

    public void ledsControl(View view) {
        Intent intent = new Intent(this,LedControlActivity.class);
        startActivity(intent);
    }

    public void portesButton(View view) {
        Intent intent = new Intent(this,controlePortesActivity.class);
        startActivity(intent);
    }


    private void notification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentTitle(" Smart House Warning ")
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentText(" There is a danger of a  Gaz Leak ")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }
}
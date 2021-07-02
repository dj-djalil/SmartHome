package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GasAlarmActivity extends AppCompatActivity {
    Button setAlarmButton;
    TextView gasValue;
    FirebaseDatabase database;
    DatabaseReference myRef_alarm;
    DatabaseReference myRef_gasValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_alarm);

        database = FirebaseDatabase.getInstance();
        myRef_alarm  = database.getReference().child("Gaz/alarme");
        myRef_gasValue = database.getReference().child("Gaz/gaz");

        setAlarmButton = (Button)findViewById(R.id.alarmButton);
        gasValue = (TextView) findViewById(R.id.GasValue);

        myRef_alarm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    setAlarmButton.setText("Enable");
                    setAlarmButton.setTextColor(getResources().getColor(R.color.white));
                }else if(value.equals("0")){
                    setAlarmButton.setText("Disable");
                    setAlarmButton.setTextColor(getResources().getColor(R.color.close));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        myRef_gasValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Integer value = dataSnapshot.getValue(Integer.class);
                gasValue.setText(value+ "");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void setAlarmButton(View view) {
        String str=setAlarmButton.getText().toString();
        if(str.equals("Enable")){
            setAlarmButton.setText("Disable");
            setAlarmButton.setTextColor(getResources().getColor(R.color.close));
            myRef_alarm.setValue("0");

        }else if(str.equals("Disable")){
            setAlarmButton.setText("Enable");
            setAlarmButton.setTextColor(getResources().getColor(R.color.white));
            myRef_alarm.setValue("1");
        }
    }
}
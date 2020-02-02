package com.example.styleomege69;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.styleomege69.Prevalent.Prevalent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    private EditText NameEditText, NumberEditText, AddressEditText;
    private TextView CloseTextBtn,SaveTextBtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        NameEditText= (EditText) findViewById(R.id.settings_full_name);
        NumberEditText= (EditText) findViewById(R.id.settings_phone_number);
        AddressEditText= (EditText) findViewById(R.id.settings_phone_address);
        CloseTextBtn= (TextView) findViewById(R.id.close_settings_btn);
        SaveTextBtn= (TextView) findViewById(R.id.update_account_settings);


        CloseTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SaveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInfoSaved();
            }

        });


    }

    private void userInfoSaved() {
        DatabaseReference refe = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name",NameEditText.getText().toString());
        userMap.put("number",NumberEditText.getText().toString());
        userMap.put("address",AddressEditText.getText().toString());

        refe.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        Toast.makeText(this, "Information updated Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

}

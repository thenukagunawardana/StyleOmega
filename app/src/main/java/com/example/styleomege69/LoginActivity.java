package com.example.styleomege69;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.styleomege69.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
{
    private Button LoginAccountbtn;
    private EditText inputName,inputPassword;
    private ProgressDialog loadingBar;

    private String parentDBname="Users";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginAccountbtn=(Button)findViewById(R.id.login_button);
        inputName=(EditText)findViewById(R.id.username_login);
        inputPassword=(EditText)findViewById(R.id.password_login);
        loadingBar= new ProgressDialog(this);

        LoginAccountbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginUser();
            }
        });
    }

    private void LoginUser()
    {
        String name=inputName.getText().toString();
        String password=inputPassword.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait While Verifying Details");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessUser(name,password);
        }
    }

    private void AllowAccessUser(final String name, final String password)
    {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDBname).child(name).exists())
                {
                    Users usersData=dataSnapshot.child(parentDBname).child(name).getValue(Users.class);

                    if (usersData.getName().equals(name))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent= new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);


                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this username Does not Exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Create An Account Please", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

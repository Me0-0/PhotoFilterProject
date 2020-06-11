package com.example.photofilterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {

    EditText usernameET, passwordET;
    Button registerBtn,backBtn;

    String username, password;
    Dal d = new Dal(this);
    Toast toast;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = (EditText)findViewById(R.id.register_et_username);

        passwordET = (EditText)findViewById(R.id.register_et_password);


        registerBtn = (Button)findViewById(R.id.register_btn_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                if(!(username.compareTo("") == 0 || password.compareTo("") == 0))
                {
                    if(username.compareTo("Guest") != 0)
                    {
                        if(!d.User_CheckUsernameExists(username))
                        {
                            d.User_Add(username,password);
                            toast = Toast.makeText(getApplicationContext(), "User Added, You Can Login Now", Toast.LENGTH_SHORT);

                        }
                        else
                        {
                            toast = Toast.makeText(getApplicationContext(), "Error: The Username '"+ username + "' Is Already Taken, Try Using Different One", Toast.LENGTH_SHORT);
                        }
                    }
                    else
                    {
                        toast = Toast.makeText(getApplicationContext(), "Error: 'Guest' Is A Saved Name, You Cannot Use It As A Username, Use Different Name", Toast.LENGTH_SHORT);
                    }
                }
                else
                {
                    toast = Toast.makeText(getApplicationContext(), "Error: All Fields Must Be Filled", Toast.LENGTH_SHORT);
                }
            }
        });


        backBtn = (Button)findViewById(R.id.register_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }


}
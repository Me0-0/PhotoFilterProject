package com.example.photofilterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {

    EditText usernameET,passwordET;
    Button loginBtn, backBtn;

    String username, password;
    Dal d = new Dal(this);
    Toast toast;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText)findViewById(R.id.login_et_username);
        passwordET = (EditText)findViewById(R.id.login_et_password);

        loginBtn = (Button)findViewById(R.id.login_btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();
                if(!(username.compareTo("") == 0 || password.compareTo("") == 0))
                {
                    if(d.User_CheckUsernameAndPasswordMatches(username,password))
                    {
                        intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                    else
                    {
                        toast = Toast.makeText(getApplicationContext(), "Error: Username Or Password Are Incorrect", Toast.LENGTH_SHORT);
                    }
                }
                else
                {
                    toast = Toast.makeText(getApplicationContext(), "Error: All Fields Must Be Filled", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });

        backBtn = (Button)findViewById(R.id.login_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
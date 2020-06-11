package com.example.photofilterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    Button filterBtn, galleryBtn, settingsBtn, exitBtn;
    TextView userTv;
    Toast toast;
    Intent intent;
    String username;
    boolean isUser;
    Dal d = new Dal(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        intent = this.getIntent();
        username = intent.getStringExtra("username");
        isUser = username.compareTo("Guest") != 0;

        userTv = (TextView)findViewById(R.id.menu_tv_username);
        userTv.setText("Welcome, "+ username +"!");

        filterBtn = (Button)findViewById(R.id.menu_btn_filter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, FilterActivity.class);
                if(isUser)
                {
                    intent.putExtra("_id", d.User_GetId_ByUsername(username));
                }
                startActivity(intent);
            }
        });

        galleryBtn = (Button)findViewById(R.id.menu_btn_gallery);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, GalleryActivity.class);
                intent.putExtra("isUser", isUser);
                if(isUser)
                {
                    intent.putExtra("_id", d.User_GetId_ByUsername(username));
                }
                startActivity(intent);
            }
        });

        settingsBtn = (Button)findViewById((R.id.menu_btn_settings));
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUser)
                {
                    if(isUser)
                    {
                        intent = new Intent(MenuActivity.this, SettingsActivity.class);
                        DB_User u = d.User_GetDBUser_ByUsername(username);
                        intent.putExtra("_id", u.getId());
                        intent.putExtra("share", u.isShare());
                        intent.putExtra("save",u.isSave());
                        intent.putExtra("code",u.getCode());
                        startActivity(intent);
                    }
                    else
                    {
                        toast = Toast.makeText(getApplicationContext(), "Error: Only Logged Users Have Access To The Settings Screen", Toast.LENGTH_SHORT);
                    }

                }
            }
        });

        exitBtn = (Button)findViewById(R.id.menu_btn_exit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }
}
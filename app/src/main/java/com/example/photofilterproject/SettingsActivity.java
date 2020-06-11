package com.example.photofilterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    Switch saveSw, shareSw;
    EditText codeEt;
    Button saveBtn, cancelBtn;
    String code;
    boolean save, share;
    long _id;
    Intent intent;
    Toast toast;
    Dal d = new Dal(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        intent = this.getIntent();
        share = intent.getBooleanExtra("share",true);
        save = intent.getBooleanExtra("save",true);
        code = intent.getStringExtra("code");
        _id = intent.getLongExtra("_id", -1);

        toast = Toast.makeText(getApplicationContext(), "Setting Are Up To Date", Toast.LENGTH_SHORT);

        saveSw = (Switch)findViewById(R.id.settings_switch_save);
        saveSw.setChecked(save);

        shareSw = (Switch)findViewById(R.id.settings_switch_share);
        shareSw.setChecked(share);

        codeEt = (EditText)findViewById(R.id.settings_et_code);
        codeEt.setText(code);

        saveBtn = (Button)findViewById(R.id.settings_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.User_UpdateSettings_ById(_id,shareSw.isChecked()?1:0, saveSw.isChecked()?1:0,codeEt.getText().toString());
                toast.show();
            }
        });

        cancelBtn = (Button)findViewById(R.id.settings_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
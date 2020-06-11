package com.example.photofilterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import java.io.InputStream;
import java.util.Date;

public class FilterActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    boolean user;
    long _id;
    Dal d = new Dal(this);

    int filterIndex = 0;
    boolean isRegular = true, example1 = true, cameraOn = false;
    Toast toast = toast = Toast.makeText(getApplicationContext(), "Error: Cannot Change Photo While Camera Is On, Press Cancel To Enable New Photos", Toast.LENGTH_SHORT), alert = toast = Toast.makeText(getApplicationContext(), "Error: Only Logged Users Can Use Premium Filters", Toast.LENGTH_SHORT);;
    private static final int REQUEST_IMAGE = 100;
    Photo photo;
    Bitmap original, tmp;
    String[] regularFilters = new String[]{"None", "UpsideDown", "Right2Left", "BlackNWhite", "B/W but Red", "B/W but Green", "B/W but Blue"};
    String[] premiumFilters = new String[]{"None", "Shades of Red", "Shades of Green", "Shades of Blue", "Red2Black", "Red2White", "Red2Gray", "Green2Black", "Green2White", "Green2Gray", "blue2Black", "Blue2White", "Blue2Gray", "Negative"};

    Button saveBtn, cancelBtn, cameraBtn, example1Btn, example2Btn,  backBtn;
    ImageButton rightBtn, leftBtn;
    Switch filtersSw;
    TextView filtersTv;
    ImageView imageView;
    Date currentTime;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        intent = this.getIntent();
        user = intent.getBooleanExtra("isUser", false);
        if(user)
        {
            _id = intent.getLongExtra("_id",-1);
        }
        tmpSetExample();
        
        imageView = (ImageView)findViewById(R.id.filter_iv_view);
        imageView.setImageBitmap(tmp);
        filtersTv = (TextView)findViewById(R.id.filter_tv_filter);
        cameraBtn = (Button)findViewById(R.id.filter_btn_camera);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cameraOn) {
                    dispatchTakePictureIntent();
                }
                else
                {
                    toast.show();
                }
            }
        });
        
        example1Btn = (Button)findViewById(R.id.filter_btn_example1);
        example1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example1 = true;
                if(!cameraOn)
                {
                    tmpSetExample();
                }
                else
                {
                    toast.show();
                }
            }
        });
        
        example2Btn = (Button)findViewById(R.id.filter_btn_example2);
        example2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example1 = false;
                if(!cameraOn)
                {
                    tmpSetExample();
                }
                else
                {
                    toast.show();
                }
            }
        });
        
        rightBtn = (ImageButton)findViewById(R.id.filter_btn_right);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterIndex++;
                if(!(filterIndex < (isRegular?regularFilters.length:premiumFilters.length)))
                {
                    filterIndex = 0;
                }
                filtersTv.setText(isRegular?regularFilters[filterIndex]:premiumFilters[filterIndex]);
                if(cameraOn)
                {
                    BitmapProcess();
                    imageView.setImageBitmap(tmp);
                }
                else
                {
                    tmpSetExample();
                }
            }
        });
        
        leftBtn = (ImageButton)findViewById(R.id.filter_btn_left);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterIndex--;
                if(!(filterIndex > 0 ))
                {
                    filterIndex = (isRegular?regularFilters.length-1:premiumFilters.length-1);
                }
                filtersTv.setText(isRegular?regularFilters[filterIndex]:premiumFilters[filterIndex]);
                if(cameraOn)
                {
                    BitmapProcess();
                    imageView.setImageBitmap(tmp);
                }
                else
                {
                    tmpSetExample();
                }
            }
        });
        
        cancelBtn = (Button)findViewById(R.id.filter_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraOn = false;
                tmpSetExample();
            }
        });
        
        filtersSw = (Switch)findViewById(R.id.filter_switch_filters);
        filtersSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
                if(isChecked && !user)
                {
                    alert.show();
                    filtersSw.setChecked(false);
                }
                else
                {
                    isRegular = !isChecked;
                    filterIndex = 0;
                    BitmapProcess();
                }
            
            }
        });
        
        backBtn = (Button)findViewById(R.id.filter_btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        saveBtn = (Button)findViewById(R.id.filter_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime = Calendar.getInstance().getTime();
                MediaStore.Images.Media.insertImage(getContentResolver(), tmp,  currentTime.toString(), "PhotoFilterProject");
            }
        });

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
             original = (Bitmap) extras.get("data");
             tmp = Bitmap.createBitmap(original);
             BitmapProcess();
            imageView.setImageBitmap(tmp);
        }
    }



    private void tmpSetExample()
    {
        AssetManager assets = getAssets();

        InputStream stream;
        try {
            stream = assets.open("example"+(example1?1:2)+".png");
            tmp = BitmapFactory.decodeStream(stream);
        }catch (Exception e)
        {
            return;
        }
    }

    //photo filter affects:
    private boolean color(int a, int b, int c)
    {
        return (a-b) > c && (a-c) > b;
    }
    private void BitmapProcess()
    {
        int w = original.getWidth();
        int h = original.getHeight();
        int[] pixles = new int[w*h];
        int[] pix = new int[w*h];
        int[] rgb = new int[3];
        int index;
        original.getPixels(pixles,0,w,0,0,w,h);
        original.getPixels(pix,0,w,0,0,w,h);

        int r,g,b;
        int i;
        if(isRegular && filterIndex <3 &&filterIndex!= 0)
        {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    index = y * w + x;
                    i = filterIndex==1?(h-1-y)*w+x:y*w+(w-x-1);
                    pixles[index] = pix[i];
                }
            }
        }
        else if(filterIndex!= 0){
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    index = y * w + x;
                    r = Color.red(pixles[index]);
                    b = Color.blue(pixles[index]);
                    g = Color.green(pixles[index]);
                    rgb = isRegular?RegularColorEffects(r,g,b):PremiumColorEffects(r,g,b);
                    pixles[index] = Color.rgb(rgb[0],rgb[1],rgb[2]);
                }
            }
        }
        else
        {
            tmp = Bitmap.createBitmap(original);
            return;
        }
        tmp = Bitmap.createBitmap(w,h,original.getConfig());
        tmp.getPixels(pixles,0,w,0,0,w,h);
        pixles = null;

    }
    private int[] RegularColorEffects(int r,int g,int b)
    {
        int[] ret = new int[]{r,g,b};
        int avrg = 0;
        switch (filterIndex){
            case 3:
                avrg = (r+g+b)/3;
                ret = new int[]{avrg,avrg,avrg};
                break;
            case 4:
                if(color(r,g,b))
                {
                    avrg = (g+b)/2;
                    ret = new int[]{r,avrg,avrg};
                }
                else
                {
                    avrg = (r+g+b)/3;
                    ret = new int[]{avrg,avrg,avrg};
                }
                break;
            case 5:
                if(color(g,r,b))
                {
                    avrg = (b+r)/2;
                    ret = new int[]{g,avrg,avrg};
                }
                else
                {
                    avrg = (r+g+b)/3;
                    ret = new int[]{avrg,avrg,avrg};
                }
                break;
            case 6:
                if(color(b,r,g))
                {
                    avrg = (g+r)/2;
                    ret = new int[]{avrg,avrg,b};
                }
                else
                {
                    avrg = (r+g+b)/3;
                    ret = new int[]{avrg,avrg,avrg};
                }
                break;
            default:
                break;
        }
        return ret;
    }
    private int[] PremiumColorEffects(int r,int g,int b)
    {
        int[] ret = new int[]{r,g,b};
        int avrg = 0;
        switch (filterIndex){
            case 1:
                avrg = (r+b+g)/3;
                ret = new int[]{avrg,0,0};
                break;
            case 2:
                avrg = (r+b+g)/3;
                ret = new int[]{0,avrg,0};
                break;
            case 3:
                avrg = (r+g+b)/3;
                ret = new int[]{0,0,avrg};
                break;
            case 4:
                if(color(r,g,b))
                {
                    ret = new int[]{255,255,255};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 5:
                if(color(r,g,b))
                {
                    ret = new int[]{0,0,0};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 6:
                if(color(r,g,b))
                {
                    avrg = (r+g+b)/3;
                    ret = new int[]{avrg,avrg,avrg};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 7:
                if(color(g,r,b))
                {
                    ret = new int[]{255,255,255};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 8:
                if(color(g,r,b))
                {
                    ret = new int[]{0,0,0};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 9:
                if(color(g,r,b))
                {
                    avrg = (r+g+b)/3;
                    ret = new int[]{avrg,avrg,avrg};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 10:
                if(color(b,r,g))
                {
                    ret = new int[]{255,255,255};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 11:
                if(color(b,r,g))
                {
                    ret = new int[]{0,0,0};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 12:
                if(color(b,r,g))
                {
                    avrg = (r+g+b)/3;
                    ret = new int[]{avrg,avrg,avrg};
                }
                else
                {
                    ret = new int[]{r,g,b};
                }
                break;
            case 13:
                ret = new int[]{255-r,255-g,255-b};
            default:
                break;
        }
        return ret;
    }
}
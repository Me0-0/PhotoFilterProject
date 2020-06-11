package com.example.photofilterproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Photo {
    protected long id;
    protected long premiumFilter;
    protected String filterName;
    protected byte[] photo;
    public Photo()
    {

    }
    public Photo( long id, long premiumFilter, String filterName, byte[] photo)
    {

        this.id = id;
        this.premiumFilter = premiumFilter;
        this.filterName = filterName;
        this.photo = new byte[photo.length];
        System.arraycopy(photo,0,this.photo,0,photo.length);
    }
    public Photo(long id, boolean premiumFilter, String filterName, Bitmap bitmap)
    {
        this.id = id;
        this.premiumFilter = premiumFilter?1:0;
        this.filterName = filterName;
        setPhoto(bitmap);
    }



    public long getId() {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }


    public boolean isPremiumFilter() {
        return premiumFilter!=0;
    }

    public void setPremiumFilter(boolean premiumFilter) {
        this.premiumFilter = premiumFilter?1:0;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public byte[] getPhoto() {
        byte[] b = new byte[this.photo.length];
        int i = 0;
        while( i < b.length)
        {
            b[i] = photo[i];
            i++;
        }
        return b;
    }

    public Bitmap getBitmapPhoto()
    {
        return BitmapFactory.decodeByteArray(this.photo, 0 ,this.photo.length);
    }

    protected void setPhoto(Bitmap bitmap)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        this.photo = bos.toByteArray();
    }
}


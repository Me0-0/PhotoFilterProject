package com.example.photofilterproject;

public class DB_Photo extends Photo
{
    private long uid;
    private boolean share;
    private boolean save;
    private String code;
    public DB_Photo()
    {
        super();
    }
    public  DB_Photo(long uid, long id, long premiumFilter, String filterName, byte[] photo)
    {
        super(id, premiumFilter,filterName,photo);
        this.uid = uid;
    }
    public long getUid() {
        return uid;
    }

}

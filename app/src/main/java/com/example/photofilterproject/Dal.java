package com.example.photofilterproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Dal extends SQLiteAssetHelper {
    public Dal(Context context) {
        super(context, "PhotoFilterProject.db",null,1);
    }

    //Update:
    //Users[_id] : code,save,share (done)
    //Users[username] : code,save,share (done)

    //Insert:
    //Users(done)
    //Gallery(done)

    //Select:
    //Check Existence:
    //
    //Users[username] (done)
    //Users[username,password] (done)
    //Users[_id] (done)
    //Gallery (done)
    //
    //Get Data:
    //
    //Users[_id] -> username (done)
    //Users[username] -> _id (done)
    //Users[_id] -> User (done)
    //Users[username] -> User (done)
    //Gallery -> * (done)


    //Create new User in Users:
    public void User_Add(String username, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsert = "INSERT INTO Users (username ,password) VALUES(?,?)";
        SQLiteStatement statement = db.compileStatement(sqlInsert);
        statement.bindString(1,username);
        statement.bindString(2,password);
        statement.execute();
    }
    //Create new User in Users:
    public void Gallery_Add(long _id, long premiumFilter, String filterName, byte[] photo)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsert = "INSERT INTO Gallery (_id ,premiumFilter ,filterName ,photo) VALUES(?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sqlInsert);
        statement.bindLong(1,_id);
        statement.bindLong(2,premiumFilter);
        statement.bindString(3,filterName);
        statement.bindBlob(4,photo);
        statement.execute();
    }
    public void Gallery_Add(Photo photo)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsert = "INSERT INTO Gallery (_id ,premiumFilter ,filterName ,photo) VALUES(?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sqlInsert);
        statement.bindLong(1,photo.getId());
        statement.bindLong(2,photo.isPremiumFilter()?1:0);
        statement.bindString(3,photo.getFilterName());
        statement.bindBlob(4,photo.getPhoto());
        statement.execute();
    }


    //check if user with username exists in Users:
    public boolean User_CheckUsernameExists(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT _id FROM Users WHERE username = '"+username+"'";
        Cursor c = db.rawQuery(qr,null);
        boolean ret =  c.moveToFirst();
        c.close();
        return ret;
    }
    //check if user with username and password exists in Users
    public boolean User_CheckUsernameAndPasswordMatches(String username, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT _id FROM Users WHERE username = '"+username+"' AND password = '"+password+"'";
        Cursor c = db.rawQuery(qr,null);
        boolean ret = c.moveToFirst();
        c.close();
        return ret;
    }
    public boolean User_CheckIdAndCodeMatches(long _id, String code)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Users WHERE _id = '"+_id+"' AND code = '"+code+"'";
        Cursor c = db.rawQuery(qr,null);
        boolean ret = c.moveToFirst();
        c.close();
        return ret;
    }
    //check if user with _id exists in Users
    public boolean User_CheckIdExists(long _id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Users WHERE _id = '"+_id+"'";
        Cursor c = db.rawQuery(qr,null);
        boolean ret =  c.moveToFirst();
        c.close();
        return ret;
    }
    //check if there are any photos in Gallery
    public boolean Gallery_CheckDataExists()
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Gallery";
        Cursor c = db.rawQuery(qr,null);
        boolean ret =  c.moveToFirst();
        c.close();
        return ret;
    }

    //update user's settings
    public void User_UpdateSettings_ByUsername(String username, long share, long save, String code)
    {
        SQLiteDatabase db = getWritableDatabase();
       // String qr = "' WHERE username = ?";
        //ContentValues values = new ContentValues();
        //values.put("share",share);
        //values.put("save",save);
        //values.put("code",code);
        //db.update("Users",values,qr, new String[]{ username });
        String qr = "UPDATE Users SET share = '"+share+"' ,save = '"+save+"' , code = '"+code+"' WHERE username = '"+username+"'";
        db.execSQL(qr);

    }
    public void User_UpdateSettings_ById(long _id,long share, long save, String code)
    {
        SQLiteDatabase db = getWritableDatabase();
        //String qr = "' WHERE _id = ?";
        //ContentValues values = new ContentValues();
        //values.put("share",share);
        //values.put("save",save);
        //values.put("code",code);
        //String[] args = new String[]{String.valueOf(_id)};
        //db.update("Users", values, qr, args);

        String qr = "UPDATE Users SET share = '"+share+"' ,save = '"+save+"' , code = '"+code+"' WHERE _id = '"+_id+"'";
        db.execSQL(qr);
    }
    public String User_GetUsername_ById(long _id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT username FROM Users WHERE _id = '"+_id+"'";
        Cursor c = db.rawQuery(qr,null);
        String ret = "";
        if(c.moveToFirst())
        {
            ret = c.getString(c.getColumnIndex("username"));
        }
        c.close();
        return ret;

    }
    public long User_GetId_ByUsername(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT username FROM Users WHERE username = '"+username+"'";
        Cursor c = db.rawQuery(qr,null);
        long ret = 0;
        if(c.moveToFirst())
        {
            ret = c.getLong(c.getColumnIndex("username"));
        }
        c.close();
        return ret;
    }


    public DB_User User_GetDBUser_ById(long _id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Users WHERE _id = '"+_id+"'";
        Cursor c = db.rawQuery(qr,null);
        DB_User user = new DB_User();
        if(c.moveToFirst())
        {
            user = new DB_User(c.getLong(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("password")),
                    c.getLong(c.getColumnIndex("share")),
                    c.getLong(c.getColumnIndex("save")),
                    c.getString(c.getColumnIndex("code")));
        }
        c.close();
        return user;
    }
    public User User_GetUser_ById(long _id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Users WHERE _id = '"+_id+"'";
        Cursor c = db.rawQuery(qr,null);
        User user = new User();
        if(c.moveToFirst())
        {
            user = new User(c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("password")),
                    c.getLong(c.getColumnIndex("share")),
                    c.getLong(c.getColumnIndex("save")),
                    c.getString(c.getColumnIndex("code")));
        }
        c.close();
        return user;
    }
    public DB_User User_GetDBUser_ByUsername(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Users WHERE username = '"+username+"'";
        Cursor c = db.rawQuery(qr,null);
        DB_User user = new DB_User();
        if(c.moveToFirst())
        {
            user = new DB_User(c.getLong(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("password")),
                    c.getLong(c.getColumnIndex("share")),
                    c.getLong(c.getColumnIndex("save")),
                    c.getString(c.getColumnIndex("code")));
        }
        c.close();
        return user;
    }

    public User User_GetUser_ByUsername(String username)
    {
        SQLiteDatabase db = getWritableDatabase();
        String qr = "SELECT * FROM Users WHERE username = '"+username+"'";
        Cursor c = db.rawQuery(qr,null);
        User user = new User();
        if(c.moveToFirst())
        {
            user = new User(c.getString(c.getColumnIndex("username")),
                    c.getString(c.getColumnIndex("password")),
                    c.getLong(c.getColumnIndex("share")),
                    c.getLong(c.getColumnIndex("save")),
                    c.getString(c.getColumnIndex("code")));
        }
        c.close();
        return user;
    }

    public ArrayList<DB_Photo> Gallery_GetAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<DB_Photo> photoArrayList = new ArrayList<>();
        String qr = "SELECT * FROM Gallery";
        Cursor c = db.rawQuery(qr,null);
        DB_Photo p;
        if(c.moveToFirst()) {
            p = new DB_Photo(c.getLong(c.getColumnIndex("_uid")),
                    c.getLong(c.getColumnIndex("_id")),
                    c.getLong(c.getColumnIndex("premiumFilter")),
                    c.getString(c.getColumnIndex("filterName")),
                    c.getBlob(c.getColumnIndex("photo")));
            photoArrayList.add(p);
            while (c.moveToNext())
            {
                p = new DB_Photo(c.getLong(c.getColumnIndex("_uid")),
                        c.getLong(c.getColumnIndex("_id")),
                        c.getLong(c.getColumnIndex("premiumFilter")),
                        c.getString(c.getColumnIndex("filterName")),
                        c.getBlob(c.getColumnIndex("photo")));
                photoArrayList.add(p);
            }
        }
        c.close();
        return photoArrayList;
    }



}

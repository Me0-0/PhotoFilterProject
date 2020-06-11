package com.example.photofilterproject;

public class DB_User extends User{
    private long id;

    public DB_User()
    {
        super();
    }
    public DB_User(long id, String username, String password, long share, long save, String code)
    {
        super(username, password, share, save, code);
        this.id = id;
    }
    public DB_User(long id)
    {

    }
    public long getId() {
        return id;
    }

}

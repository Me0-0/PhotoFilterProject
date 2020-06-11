package com.example.photofilterproject;

public class User {


    private String username;

    private String password;
    private long share;
    private long save;
    private String code;

    public User(){}

    public User( String username, String password, long share, long save, String code)
    {
        this.username = username;
        this.password = password;
        this.share = share;
        this.save = save;
        this.code = code;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


    public boolean isShare() {
        return share != 0;
    }

    public void setShare(boolean share) {
        this.share = share?1:0;
    }

    public boolean isSave() {
        return save != 0;
    }

    public void setSave(boolean save) {
        this.save = save?1:0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


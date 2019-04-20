package com.example.schooldetect;

public class Account {
    private String account;
    private String password;

    public Account(String acc, String pw) {
        account = acc;
        password = pw;
    }

    public String getac() {
        return account;
    }
    public String getpw() {
        return password;
    }
}
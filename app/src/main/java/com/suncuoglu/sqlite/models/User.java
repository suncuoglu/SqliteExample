package com.suncuoglu.sqlite.models;

/*
12.01.2021 by Şansal Uncuoğlu
*/

public class User {

    private String note_id;
    private String note_name;
    private String note_surname;
    private String note_username;
    private String note_password;
    private String note_birthday;

    public User(String note_id, String note_name, String note_surname, String note_username, String note_password, String note_birthday) {
        this.note_id = note_id;
        this.note_name = note_name;
        this.note_surname = note_surname;
        this.note_username = note_username;
        this.note_password = note_password;
        this.note_birthday = note_birthday;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String getNote_name() {
        return note_name;
    }

    public void setNote_name(String note_name) {
        this.note_name = note_name;
    }

    public String getNote_surname() {
        return note_surname;
    }

    public void setNote_surname(String note_surname) {
        this.note_surname = note_surname;
    }

    public String getNote_username() {
        return note_username;
    }

    public void setNote_username(String note_username) {
        this.note_username = note_username;
    }

    public String getNote_password() {
        return note_password;
    }

    public void setNote_password(String note_password) {
        this.note_password = note_password;
    }

    public String getNote_birthday() {
        return note_birthday;
    }

    public void setNote_birthday(String note_birthday) {
        this.note_birthday = note_birthday;
    }
}
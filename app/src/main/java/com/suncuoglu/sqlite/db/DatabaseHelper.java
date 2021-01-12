package com.suncuoglu.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.suncuoglu.sqlite.models.User;

import java.util.ArrayList;

/*
12.01.2021 by Şansal Uncuoğlu
*/


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "my_user.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTE_CREATE =
            "CREATE TABLE " + TablesInfo.NoteEntry.TABLE_NAME + " (" +
                    TablesInfo.NoteEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TablesInfo.NoteEntry.COLUMN_NAME + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_SURNAME + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_USERNAME + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_PASSWORD + " TEXT, " +
                    TablesInfo.NoteEntry.COLUMN_BIRTHDAY + " TEXT " +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesInfo.NoteEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addUser(String name, String surname, String username, String password, String birtday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TablesInfo.NoteEntry.COLUMN_NAME, name.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_SURNAME, surname.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_USERNAME, username.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_PASSWORD, password.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_BIRTHDAY, birtday.trim());

        long result = db.insert(TablesInfo.NoteEntry.TABLE_NAME, null, cv);

        if (result > -1)
            Log.i("DatabaseHelper", "Not başarıyla kaydedildi");
        else
            Log.i("DatabaseHelper", "Not kaydedilemedi");

        db.close();
    }

    public void deleteUser(String noteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TablesInfo.NoteEntry.TABLE_NAME, TablesInfo.NoteEntry.COLUMN_ID + "=?", new String[]{noteID});
        db.close();
    }

    public void updateUser(String name, String surname, String username, String password, String birtday,String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TablesInfo.NoteEntry.COLUMN_NAME, name.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_SURNAME, surname.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_USERNAME, username.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_PASSWORD, password.trim());
        cv.put(TablesInfo.NoteEntry.COLUMN_BIRTHDAY, birtday.trim());


        db.update(TablesInfo.NoteEntry.TABLE_NAME, cv,TablesInfo.NoteEntry.COLUMN_ID + "=?", new String[]{id});

    }

    public ArrayList<User> getNoteList() {
        ArrayList<User> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TablesInfo.NoteEntry.COLUMN_ID,
                TablesInfo.NoteEntry.COLUMN_NAME,
                TablesInfo.NoteEntry.COLUMN_SURNAME,
                TablesInfo.NoteEntry.COLUMN_USERNAME,
                TablesInfo.NoteEntry.COLUMN_PASSWORD,
                TablesInfo.NoteEntry.COLUMN_BIRTHDAY};

        Cursor c = db.query(TablesInfo.NoteEntry.TABLE_NAME, projection, null, null, null, null, null);
        while (c.moveToNext()) {
            data.add(new User(c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_ID)), c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_NAME))
                    , c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_SURNAME)), c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_USERNAME))
                    , c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_PASSWORD)), c.getString(c.getColumnIndex(TablesInfo.NoteEntry.COLUMN_BIRTHDAY))));
        }

        c.close();
        db.close();

        return data;
    }
}
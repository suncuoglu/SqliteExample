package com.suncuoglu.sqlite.db;

import android.provider.BaseColumns;

/*
12.01.2021 by Şansal Uncuoğlu
*/


public class TablesInfo {

    public static final class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_ID = "user_id";
        public static final String COLUMN_NAME = "user_name";
        public static final String COLUMN_SURNAME = "user_surname";
        public static final String COLUMN_USERNAME = "user_username";
        public static final String COLUMN_PASSWORD = "user_password";
        public static final String COLUMN_BIRTHDAY= "user_birthday";
    }
}
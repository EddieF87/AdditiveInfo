package com.example.android.additiveinfo.data;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "additives.sqlite";
    private static final int DATABASE_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

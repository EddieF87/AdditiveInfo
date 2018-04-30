package com.example.android.additiveinfo.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.additiveinfo";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ADDITIVES = "additives";

    @SuppressWarnings("unused")
    public DataContract() {
    }

    public static final class DataEntry implements BaseColumns {

        public static final Uri CONTENT_URI_ADDITIVES = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ADDITIVES);
        public static final String ADDITIVES_TABLE_NAME = "additives";

        public static final String ADDITIVE_ID = "_id";
        public static final String ADDITIVE_CODE = "code";
        public static final String ADDITIVE_NAME = "name";
        public static final String ADDITIVE_LEVEL = "warning_level";
        public static final String ADDITIVE_FAVORITE = "favorite";
//        public static final String ADDITIVE_COMMENT = "comment";
//        public static final String ADDITIVE_RATING = "rating";
    }

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}

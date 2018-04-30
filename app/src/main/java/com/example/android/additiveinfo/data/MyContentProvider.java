package com.example.android.additiveinfo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private static final int ADDITIVES = 100;
    private static final int ADDITIVES_WITH_ID = 101;

    private MyDBHelper mDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // content://com.google.developer.taskmaker/tasks
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.DataEntry.ADDITIVES_TABLE_NAME,
                ADDITIVES);

        // content://com.google.developer.taskmaker/tasks/id
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY,
                DataContract.DataEntry.ADDITIVES_TABLE_NAME + "/#",
                ADDITIVES_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MyDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
//        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ADDITIVES:
                break;

            case ADDITIVES_WITH_ID:
                selection = DataContract.DataEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                break;

            default:
                throw new IllegalArgumentException ("Unknown URI: " + uri);
        }

        Cursor cursor = database.query(DataContract.DataEntry.ADDITIVES_TABLE_NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String table = DataContract.DataEntry.ADDITIVES_TABLE_NAME;
        selection = DataContract.DataEntry.ADDITIVE_ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

        int rowsUpdated = database.update(table, contentValues, selection, selectionArgs);
        if(rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsUpdated;
        } else {
            return -1;
        }
    }
}

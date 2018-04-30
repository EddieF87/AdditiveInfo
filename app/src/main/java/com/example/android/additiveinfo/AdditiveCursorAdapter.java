package com.example.android.additiveinfo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.additiveinfo.R;
import com.example.android.additiveinfo.data.DataContract;

public class AdditiveCursorAdapter extends CursorAdapter {

    public AdditiveCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_additive_suggestion, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = DataContract.getColumnString(cursor, DataContract.DataEntry.ADDITIVE_NAME);
        TextView textView = (TextView) view;
        textView.setText(name);
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }
}

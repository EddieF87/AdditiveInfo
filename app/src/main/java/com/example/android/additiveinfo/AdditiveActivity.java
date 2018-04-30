package com.example.android.additiveinfo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.additiveinfo.data.DataContract;

import model.Additive;

public class AdditiveActivity extends AppCompatActivity {

    private Additive mAdditive;
    private ImageView mFavoriteView;
    public static final int REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additive);
        Bundle args = getIntent().getExtras();
        if (args == null) {
            finish();
        }
        mAdditive = args.getParcelable("additive");

        TextView nameView = findViewById(R.id.additive_name);
        TextView codeUSAView = findViewById(R.id.code_usa);
        TextView codeEUView = findViewById(R.id.code_eu);
        TextView codeChinaView = findViewById(R.id.code_china);
        TextView descriptionView = findViewById(R.id.description);
        nameView.setText(mAdditive.getName());
        codeUSAView.setText(mAdditive.getCodeUSA());
        codeEUView.setText(mAdditive.getCodeEU());
        codeChinaView.setText(mAdditive.getCodeChina());
        descriptionView.setText(mAdditive.getDescription());

        int color;
        switch (mAdditive.getLevel()) {
            case 0:
                color = getResources().getColor(R.color.colorLevel0);
                break;
            case 1:
                color = getResources().getColor(R.color.colorLevel1);
                break;
            case 2:
                color = getResources().getColor(R.color.colorLevel2);
                break;
            default:
                color = getResources().getColor(R.color.colorLevel0);
        }
        nameView.setTextColor(color);
        mFavoriteView = findViewById(R.id.favView);
        mFavoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavorite();
            }
        });
        setFavView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.additive_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_additives);
        final SearchView searchView = (SearchView) searchItem.getActionView();


        Cursor cursor = getContentResolver().query(DataContract.DataEntry.CONTENT_URI_ADDITIVES,
                null, null, null, null);
        final AdditiveCursorAdapter adapter = new AdditiveCursorAdapter(this, cursor, 0);
        searchView.setSuggestionsAdapter(adapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.d("xyxyx", "onSuggestionSelect " + position);
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Log.d("xyxyx", "onSuggestionClick " + position + "   " + adapter.getItem(position));
                Cursor cursor = (Cursor) adapter.getItem(position);
                long id = DataContract.getColumnLong(cursor, DataContract.DataEntry.ADDITIVE_ID);
                String name = DataContract.getColumnString(cursor, DataContract.DataEntry.ADDITIVE_NAME);
                int additiveCode = DataContract.getColumnInt(cursor, DataContract.DataEntry.ADDITIVE_CODE);
                Additive additive = new Additive(id, name, String.valueOf(additiveCode), String.valueOf(additiveCode), String.valueOf(additiveCode), name, 0, false);
                Log.d("xyxyx", "onSuggestionClick " + name);
                Intent intent = new Intent(AdditiveActivity.this, AdditiveActivity.class);
                intent.putExtra("additive", additive);
                finish();
                startActivity(intent);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("xyxyx", "submit: " + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("xyxyx", s);
                String selection = DataContract.DataEntry.ADDITIVE_NAME + " LIKE?";
                String[] selectionArgs = new String[]{"%" + s + "%"};

                Cursor cursor = getContentResolver().query(DataContract.DataEntry.CONTENT_URI_ADDITIVES,
                        null, selection, selectionArgs, null);
                adapter.swapCursor(cursor);
                return false;
            }
        });
        return true;
    }

    private void setFavView() {
        if (mAdditive.getFavorite()) {
            mFavoriteView.setColorFilter(getResources().getColor(R.color.colorLevel1));
            mFavoriteView.setAlpha(1f);
        } else {
            mFavoriteView.setColorFilter(null);
            mFavoriteView.setAlpha(.5f);
        }
    }

    private void toggleFavorite() {
        int newFav;
        if (mAdditive.getFavorite()) {
            newFav = 0;
        } else {
            newFav = 1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.DataEntry.ADDITIVE_FAVORITE, newFav);
        long id = mAdditive.get_id();
        Uri additiveUri = ContentUris.withAppendedId(DataContract.DataEntry.CONTENT_URI_ADDITIVES, id);
        int rowsUpdated = getContentResolver().update(additiveUri, contentValues, null, null);
        if (rowsUpdated > 0) {
            mAdditive.setFavorite(newFav == 1);
            setFavView();
            Intent intent = new Intent();
            intent.putExtra(DataContract.DataEntry.ADDITIVE_ID, id);
            intent.putExtra(DataContract.DataEntry.ADDITIVE_FAVORITE, newFav == 1);
            setResult(RESULT_OK, intent);
        }
    }
}

package com.example.android.additiveinfo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.additiveinfo.data.DataContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Additive;

public class MainActivity extends AppCompatActivity
        implements AdditiveRecyclerViewAdapter.OnViewCLickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private List<Additive> mAdditives;
    private int code;
    private CharSequence mFilterSequence;
    private RadioGroup mCodeGroup;
    private RecyclerView mRecyclerView;
    private ImageView mFavView;
    private boolean favoritesOn;
    private AdditiveRecyclerViewAdapter mAdapter;
    private static final int ADDITIVE_LOADER = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFavView = findViewById(R.id.favView);
        mFavView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAdapter != null) {
                    favoritesOn = !favoritesOn;
                    if(favoritesOn) {
                        mFavView.setAlpha(1f);
                        mFavView.setColorFilter(getResources().getColor(R.color.colorLevel1));
                    } else {
                        mFavView.setAlpha(.5f);
                        mFavView.setColorFilter(null);
                    }
                    mAdapter.filterFavorites(favoritesOn, mFilterSequence);
                }
            }
        });

        if(savedInstanceState != null) {
            favoritesOn = savedInstanceState.getBoolean("fav");
            mFilterSequence = savedInstanceState.getCharSequence("filter");
            if(favoritesOn) {
                mFavView.setAlpha(1f);
                mFavView.setColorFilter(getResources().getColor(R.color.colorLevel1));
            } else {
                mFavView.setAlpha(.5f);
                mFavView.setColorFilter(null);
            }
        }

        mCodeGroup = findViewById(R.id.radioGroup);
        mCodeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                code = radioGroup.getCheckedRadioButtonId();
//                mRecyclerView.setAdapter(new AdditiveRecyclerViewAdapter(mAdditives, code));
                mAdapter.setCode(code);
                mAdapter.notifyDataSetChanged();
//                int codeTitleID;
//                switch (code) {
//                    case R.id.radio_usa:
//                        codeTitleID = R.string.code_usa;
//                        break;
//                    case R.id.radio_eu:
//                        codeTitleID = R.string.code_eu;
//                        break;
//                    case R.id.radio_china:
//                        codeTitleID = R.string.code_china;
//                        break;
//                    default:
//                        codeTitleID = R.string.error;
//                }
//                TextView codeTitleTextView = findViewById(R.id.additive_code_title);
//                codeTitleTextView.setText(codeTitleID);
            }
        });

        code = mCodeGroup.getCheckedRadioButtonId();
        getSupportLoaderManager().initLoader(ADDITIVE_LOADER, null, this);

//        mAdditives = new ArrayList<>();
//
//        Cursor cursor = getContentResolver().query(DataContract.DataEntry.CONTENT_URI_ADDITIVES, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int additiveCode = DataContract.getColumnInt(cursor, DataContract.DataEntry.ADDITIVE_CODE);
//            String additiveName = DataContract.getColumnString(cursor, DataContract.DataEntry.ADDITIVE_NAME);
//            boolean favorite = DataContract.getColumnInt(cursor, DataContract.DataEntry.ADDITIVE_FAVORITE) == 1;
//            mAdditives.add(new Additive(additiveName, String.valueOf(additiveCode), String.valueOf(additiveCode), String.valueOf(additiveCode),
//                    additiveName, 0, favorite));
//            Log.d("mojave", additiveCode + "  " + additiveName);
//        }
//        cursor.close();
//
//        mAdapter = new AdditiveRecyclerViewAdapter(mAdditives, code, this);
//        mRecyclerView = findViewById(R.id.rv_additive);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void goToAdditive(Additive additive) {
        Intent intent = new Intent(MainActivity.this, AdditiveActivity.class);
        intent.putExtra("additive", additive);
        startActivityForResult(intent, AdditiveActivity.REQUEST_CODE);
    }

    @Override
    public int updateFavorite(Additive additive, int position) {


        int newFav;
        if (additive.getFavorite()) {
            newFav = 0;
        } else {
            newFav = 1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.DataEntry.ADDITIVE_FAVORITE, newFav);

        long id = additive.get_id();
        Uri additiveUri = ContentUris.withAppendedId(DataContract.DataEntry.CONTENT_URI_ADDITIVES, id);

        if (getContentResolver().update(additiveUri, contentValues, null, null) > 0) {
//            mRecyclerView.scrollToPosition(position);
            mAdapter.notifyItemChanged(position);
            return newFav;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, DataContract.DataEntry.CONTENT_URI_ADDITIVES,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if(mAdditives == null) {
            mAdditives = new ArrayList<>();
        } else {
//            mAdditives.clear();
            return;
        }

        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            long id = DataContract.getColumnLong(cursor, DataContract.DataEntry.ADDITIVE_ID);
            int additiveCode = DataContract.getColumnInt(cursor, DataContract.DataEntry.ADDITIVE_CODE);
            String additiveName = DataContract.getColumnString(cursor, DataContract.DataEntry.ADDITIVE_NAME);
            boolean favorite = DataContract.getColumnInt(cursor, DataContract.DataEntry.ADDITIVE_FAVORITE) == 1;
//            int warningLevel = DataContract.getColumnInt(cursor, DataContract.DataEntry.ADDITIVE_LEVEL);
            int warningLevel = new Random().nextInt(3);
            mAdditives.add(new Additive(id, additiveName, String.valueOf(additiveCode), String.valueOf(additiveCode + 100),
                    String.valueOf(additiveCode + 200), additiveName, warningLevel, favorite));
            Log.d("mojave", additiveCode + "  " + additiveName);
        }

        if(mAdapter == null) {
            mAdapter = new AdditiveRecyclerViewAdapter(mAdditives, code, this, this);
        } else {
            if (mFilterSequence == null) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.getFilter().filter(mFilterSequence);
            }
        }
        if(mFilterSequence != null) {
            mAdapter.resetAdditives(favoritesOn, mFilterSequence);
        } else {
            mAdapter.filterFavorites(favoritesOn, mFilterSequence);
        }


        mRecyclerView = findViewById(R.id.rv_additive);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AdditiveActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            long id = data.getLongExtra(DataContract.DataEntry.ADDITIVE_ID, -1);
            boolean fav = data.getBooleanExtra(DataContract.DataEntry.ADDITIVE_FAVORITE, false);
            if(mAdapter != null) {
                for(Additive additive : mAdditives) {
                    if(additive.get_id() == id) {
                        additive.setFavorite(fav);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
            getSupportLoaderManager().restartLoader(ADDITIVE_LOADER, null, this);
        }
    }

    private void filterList(CharSequence constraint) {
        if(mAdapter != null) {
            mAdapter.getFilter().filter(constraint);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.additive_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_additives);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        if(mFilterSequence != null) {
            searchView.setQuery(mFilterSequence, true);
            searchView.setIconified(false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                mFilterSequence = s;
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fav", favoritesOn);
        outState.putCharSequence("filter", mFilterSequence);
    }
}

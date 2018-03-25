package com.example.android.additiveinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Additive;

public class MainActivity extends AppCompatActivity
        implements AdditiveRecyclerViewAdapter.OnViewCLickListener {

    private List<Additive> mAdditives;
    private int code;
    private RadioGroup mCodeGroup;
    private RecyclerView mRecyclerView;
    private AdditiveRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCodeGroup = findViewById(R.id.radioGroup);
        mCodeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                code = radioGroup.getCheckedRadioButtonId();
//                mRecyclerView.setAdapter(new AdditiveRecyclerViewAdapter(mAdditives, code));
                mAdapter.setCode(code);
                mAdapter.notifyDataSetChanged();
                int codeTitleID;
                switch (code) {
                    case R.id.radio_usa:
                        codeTitleID = R.string.code_usa;
                        break;
                    case R.id.radio_eu:
                        codeTitleID = R.string.code_eu;
                        break;
                    case R.id.radio_china:
                        codeTitleID = R.string.code_china;
                        break;
                    default:
                        codeTitleID = R.string.error;
                }
                TextView codeTitleTextView = findViewById(R.id.additive_code_title);
                codeTitleTextView.setText(codeTitleID);
            }
        });

        code = mCodeGroup.getCheckedRadioButtonId();
        
        String description = "This additive can cause disease1 and disease2! It is extremely dangerous! Beware!" +
                " This additive has also been linked to disease3 and disease4!";

        mAdditives = new ArrayList<>();
        mAdditives.add(new Additive("Acacia", "414", "524", "3567",
                description));
        mAdditives.add(new Additive("Acesulphame", "267", "765w", "4635",
                description));
        mAdditives.add(new Additive("Acetic acid, glacia", "75", "52364", "6473",
                description));
        mAdditives.add(new Additive("Ammonium lactate", "355", "34", "3563",
                description));
        mAdditives.add(new Additive("Ammonium malate", "1402", "753", "3655",
                description));
        mAdditives.add(new Additive("Ammonium phosphate", "503", "133", "356635",
                description));
        mAdditives.add(new Additive("Annatto extracts", "160b", "906", "42553",
                description));

        mAdapter = new AdditiveRecyclerViewAdapter(mAdditives, code, this);
        mRecyclerView = findViewById(R.id.rv_additive);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void goToAdditive(Additive additive) {
        Intent intent = new Intent(MainActivity.this, AdditiveActivity.class);
        intent.putExtra("additive", additive);
        startActivity(intent);
    }
}

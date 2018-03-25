package com.example.android.additiveinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import model.Additive;

public class AdditiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additive);
        Bundle args = getIntent().getExtras();
        if(args == null) {
            finish();
        }
        Additive additive = args.getParcelable("additive");

        TextView nameView = findViewById(R.id.additive_name);
        TextView codeUSAView = findViewById(R.id.code_usa);
        TextView codeEUView = findViewById(R.id.code_eu);
        TextView codeChinaView = findViewById(R.id.code_china);
        TextView descriptionView = findViewById(R.id.description);
        nameView.setText(additive.getName());
        codeUSAView.setText(additive.getCodeUSA());
        codeEUView.setText(additive.getCodeEU());
        codeChinaView.setText(additive.getCodeChina());
        descriptionView.setText(additive.getDescription());
    }
}

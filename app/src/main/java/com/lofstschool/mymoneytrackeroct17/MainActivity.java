package com.lofstschool.mymoneytrackeroct17;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText titleEdit = findViewById(R.id.nameObj);
        EditText numEdit = findViewById(R.id.numObj);
        final Button addButton = findViewById(R.id.add);
        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence len1, int i, int i1, int i2) {
            addButton.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence len1, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(len1));
                { titleEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence len2, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence len2, int i, int i1, int i2) {
                        addButton.setEnabled(!TextUtils.isEmpty(len2));
                    }

                    @Override
                    public void afterTextChanged(Editable len2) {

                    }
                });

                }

            }

            @Override
            public void afterTextChanged(Editable len1) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

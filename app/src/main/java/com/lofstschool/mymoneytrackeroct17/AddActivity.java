package com.lofstschool.mymoneytrackeroct17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final String RESULT_ITEM = "item";
    public static final int RC_ADD_ITEM = 99;

    private EditText name;
    private EditText price;
    private ImageButton add;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        add = findViewById(R.id.add);

        name.addTextChangedListener(textWatcher);
        price.addTextChangedListener(textWatcher);

        type = getIntent().getStringExtra(EXTRA_TYPE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result  = new Intent ();
                result.putExtra(RESULT_ITEM, new Item(name.getText().toString(), Integer.parseInt(price.getText().toString()), type));
                setResult(RESULT_OK,result);
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        name.removeTextChangedListener(textWatcher);
        price.removeTextChangedListener(textWatcher);
        super.onDestroy();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            add.setEnabled(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(price.getText()));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}

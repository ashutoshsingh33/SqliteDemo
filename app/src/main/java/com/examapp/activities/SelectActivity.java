package com.examapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.examapp.R;
import com.examapp.adapter.SelectAdapter;
import com.examapp.utils.AppConstants;
import com.examapp.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity implements AppConstants {

    RecyclerView recyclerView;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentData();
        setRecycler();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(TITLE)) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));
        }

        if (getIntent().hasExtra(DATA)) {
            data = (ArrayList<String>) getIntent().getSerializableExtra(DATA);
        }
    }

    private void setRecycler() {
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SelectAdapter selectAdapter = new SelectAdapter(this, data,
                new SelectAdapter.IResultCallBack() {
                    @Override
                    public void getResult(String selectedItem) {
                        Intent intent = new Intent();
                        intent.putExtra(SELECTED_DATA, selectedItem);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
        recyclerView.setAdapter(selectAdapter);
    }

}

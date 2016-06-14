package com.example.android.mobileperf.render;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class CompareLayoutsActivity extends ActionBarActivity {
    protected static final String TAG = "CompareLayoutActivity";

    ImageView chat_author_avatar1;
    ImageView chat_author_avatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_layouts);
    }
}

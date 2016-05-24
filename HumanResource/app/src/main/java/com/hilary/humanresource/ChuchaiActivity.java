package com.hilary.humanresource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class ChuchaiActivity extends AppCompatActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuchai);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        title.setText("出差");
    }
}

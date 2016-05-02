package com.hilary.humanresource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class XinxiActivity extends AppCompatActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxi);
        title=(TextView)findViewById(R.id.title);
        title.setText("个人信息");
    }
}

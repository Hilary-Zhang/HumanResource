package com.hilary.humanresource;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WenjuanActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;
    private TextView wenjuan_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wenjuan);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        wenjuan_title=(TextView)findViewById(R.id.wenjuan_title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("调查问卷");
        wenjuan_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WenjuanActivity.this,WenjuanXiangqingActivity.class));
            }
        });
    }
}

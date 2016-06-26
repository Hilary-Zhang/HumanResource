package com.hilary.humanresource;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FankuiActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangkui);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("反馈");
    }
}

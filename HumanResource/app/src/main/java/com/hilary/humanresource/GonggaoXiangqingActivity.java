package com.hilary.humanresource;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilary.common.Params;

import org.w3c.dom.Text;

public class GonggaoXiangqingActivity extends AppCompatActivity {
    private ImageView iv_drawer;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao_xiangqing);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("详情");

        Intent i=getIntent();
        TextView title=(TextView)findViewById(R.id.news_title);
        title.setText(i.getStringExtra(Params.title));
        TextView content=(TextView)findViewById(R.id.news_content);
        content.setText(i.getStringExtra(Params.content).replace("\\n","\n"));
    }
}

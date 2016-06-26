package com.hilary.humanresource;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QingjiaActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer,iv_look;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qingjia);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_look=(ImageView)findViewById(R.id.iv_look);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_look.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_look));
        title.setText("请假");
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QingjiaActivity.this,QingjiaListActivity.class));
            }
        });
    }
}

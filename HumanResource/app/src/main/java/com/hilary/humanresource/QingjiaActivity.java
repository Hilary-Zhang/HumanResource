package com.hilary.humanresource;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QingjiaActivity extends AppCompatActivity {
    private TextView title;
    private Button btn_qingjia,btn_shenpi;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_qingjia);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        btn_qingjia=(Button)findViewById(R.id.btn_qingjia);
        btn_shenpi=(Button)findViewById(R.id.btn_shenpi);
        title.setText("请假");
        btn_qingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QingjiaActivity.this,BtnQingjiaActivity.class));
            }
        });
        btn_shenpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QingjiaActivity.this,BtnShenpiActivity.class));
            }
        });

    }
}

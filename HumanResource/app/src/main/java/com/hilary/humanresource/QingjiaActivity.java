package com.hilary.humanresource;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.hilary.humanresource.R.layout.activity_qingjia;

public class QingjiaActivity extends AppCompatActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(activity_qingjia);
        title=(TextView)findViewById(R.id.title);
        title.setText("请假");
    }
}

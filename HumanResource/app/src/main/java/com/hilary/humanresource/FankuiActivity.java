package com.hilary.humanresource;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilary.common.Utils;

public class FankuiActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;
    private EditText content;
    private Button bt_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangkui);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        content=(EditText)findViewById(R.id.content);
        bt_apply=(Button)findViewById(R.id.bt_apply);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("反馈");
        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(content.getText())){
                    Utils.Toast(getApplicationContext(),"请输入反馈内容!");
                    return;
                }
                else{
                    Utils.Toast(getApplicationContext(),"我们将收到您的反馈内容！");
                    content.setText("");
                   return;
                }
            }
        });
    }
}

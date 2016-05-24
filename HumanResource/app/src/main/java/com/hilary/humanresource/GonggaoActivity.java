package com.hilary.humanresource;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class GonggaoActivity extends AppCompatActivity {
    private ImageView iv_drawer;
    private TextView title;
    private TabHost tabHost;
    private TextView tab_text[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        //toolbar返回按钮实现
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("公司公告");
        //设置tab
        tab_text = new TextView[2];
        tab_text[0]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        tab_text[1]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        //设置tab文字
        tab_text[0].setText("未读");
        tab_text[1].setText("已读");
        //初始状态
        tab_text[0].setTextColor(ContextCompat.getColor(this,R.color.blue));
        tab_text[1].setTextColor(ContextCompat.getColor(this,R.color.black));
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setContent(R.id.weidu).setIndicator(tab_text[0]));
        tabHost.addTab(tabHost.newTabSpec("tab2").setContent(R.id.yidu).setIndicator(tab_text[1]));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        tab_text[0].setTextColor(getResources().getColor(R.color.blue));
                        tab_text[1].setTextColor(getResources().getColor(R.color.black));
                        break;
                    case "tab2":
                        tab_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_text[1].setTextColor(getResources().getColor(R.color.blue));
                        break;
                }
            }
        });
    }
}

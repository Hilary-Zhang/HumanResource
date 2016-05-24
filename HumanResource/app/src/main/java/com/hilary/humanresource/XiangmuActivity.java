package com.hilary.humanresource;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class XiangmuActivity extends AppCompatActivity {
    private ImageView iv_drawer;
    private TextView title;
    private TabHost tabHost;
    private TextView tab_text[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangmu);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        title.setText("项目管理");
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        tabHost = (TabHost) findViewById(R.id.tabhost);
        //设置tab
        tab_text = new TextView[3];
        tab_text[0]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        tab_text[1]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        tab_text[2]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        //设置tab文字
        tab_text[0].setText("未处理");
        tab_text[1].setText("处理中");
        tab_text[2].setText("已处理");
        //初始状态
        tab_text[0].setTextColor(ContextCompat.getColor(this,R.color.blue));
        tab_text[1].setTextColor(ContextCompat.getColor(this,R.color.black));
        tab_text[2].setTextColor(ContextCompat.getColor(this,R.color.black));
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setContent(R.id.unwork).setIndicator(tab_text[0]));
        tabHost.addTab(tabHost.newTabSpec("tab2").setContent(R.id.working).setIndicator(tab_text[1]));
        tabHost.addTab(tabHost.newTabSpec("tab3").setContent(R.id.worked).setIndicator(tab_text[2]));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        tab_text[0].setTextColor(getResources().getColor(R.color.blue));
                        tab_text[1].setTextColor(getResources().getColor(R.color.black));
                        tab_text[2].setTextColor(getResources().getColor(R.color.black));
                        break;
                    case "tab2":
                        tab_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_text[1].setTextColor(getResources().getColor(R.color.blue));
                        tab_text[2].setTextColor(getResources().getColor(R.color.black));
                        break;
                    case "tab3":
                        tab_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_text[1].setTextColor(getResources().getColor(R.color.black));
                        tab_text[2].setTextColor(getResources().getColor(R.color.blue));
                        break;
                }
            }
        });
    }
}

package com.hilary.humanresource;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TextView;

public class BtnShenpiActivity extends AppCompatActivity {
    private TextView title;
    private TabHost tabHost;
    private TextView tab_text[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_shenpi);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        title.setText("请假审批");
        tabHost = (TabHost) findViewById(R.id.tabhost);
        //设置tab
        tab_text = new TextView[2];
        tab_text[0]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        tab_text[1]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        //设置tab文字
        tab_text[0].setText("已审批");
        tab_text[1].setText("待审批");
        //初始状态
        tab_text[0].setTextColor(ContextCompat.getColor(this,R.color.blue));
        tab_text[1].setTextColor(ContextCompat.getColor(this,R.color.black));
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setContent(R.id.approve).setIndicator(tab_text[0]));
        tabHost.addTab(tabHost.newTabSpec("tab2").setContent(R.id.unapprove).setIndicator(tab_text[1]));

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

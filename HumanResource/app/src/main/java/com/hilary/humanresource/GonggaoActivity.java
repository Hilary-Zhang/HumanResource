package com.hilary.humanresource;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class GonggaoActivity extends TabActivity {
    private TextView title;
    private TabHost tabHost;
    private TextView tab_text[];
    private LinearLayout ll_weidu,ll_yidu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao);
        //获取组件
        title = (TextView) findViewById(R.id.title);
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        ll_weidu=(LinearLayout)findViewById(R.id.ll_weidu);
        ll_yidu=(LinearLayout)findViewById(R.id.ll_yidu);
        title.setText("公告");
        //设置tab
        tab_text = new TextView[2];
        tabHost.addTab(tabHost.newTabSpec("tab1").setContent(R.id.weidu).setIndicator(tab_text[0]));
        tabHost.addTab(tabHost.newTabSpec("tab2").setContent(R.id.yidu).setIndicator(tab_text[1]));
        //设置tab文字
        tab_text[0].setText("未读");
        tab_text[1].setText("已读");
        //初始状态
        tab_text[0].setTextColor(getResources().getColor(R.color.blue));
        tab_text[1].setTextColor(getResources().getColor(R.color.black));
        ll_weidu.setBackgroundColor(getResources().getColor(R.color.blue));
        ll_yidu.setBackgroundColor(getResources().getColor(R.color.white));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        tab_text[0].setTextColor(getResources().getColor(R.color.blue));
                        tab_text[1].setTextColor(getResources().getColor(R.color.black));
                        ll_weidu.setBackgroundColor(getResources().getColor(R.color.blue));
                        ll_yidu.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case "tab2":
                        tab_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_text[1].setTextColor(getResources().getColor(R.color.blue));
                        ll_weidu.setBackgroundColor(getResources().getColor(R.color.white));
                        ll_yidu.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                }
            }
        });
    }
}

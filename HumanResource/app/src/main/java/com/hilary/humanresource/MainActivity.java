package com.hilary.humanresource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        final TabHost mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        final View tab1=getLayoutInflater().inflate(R.layout.tab_button,null);
        TextView tv= (TextView) tab1.findViewById(R.id.tv);
        TextView title=(TextView)findViewById(R.id.title);
        title.setText("工作");
        tv.setText("工作");
        tv.setTextColor(getResources().getColor(R.color.blue));
        ImageView iv=(ImageView)tab1.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo));
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setContent(
                R.id.tv1).setIndicator(tab1));

        final View tab2=getLayoutInflater().inflate(R.layout.tab_button,null);
        tv= (TextView) tab2.findViewById(R.id.tv);
        tv.setText("联系人");
        iv=(ImageView)tab2.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setContent(
                R.id.tv2).setIndicator(tab2));
        final View tab3=getLayoutInflater().inflate(R.layout.tab_button,null);
        tv= (TextView) tab3.findViewById(R.id.tv);
        tv.setText("应用");
        iv=(ImageView)tab3.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setContent(
                R.id.tv3).setIndicator(tab3));
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {
                    TextView title=(TextView)findViewById(R.id.title);
                    title.setText("工作");
                    TextView tv=(TextView)tab1.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.blue));
                    ImageView iv=(ImageView)tab1.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo));
                    tv=(TextView)tab2.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    iv=(ImageView)tab2.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
                    tv=(TextView)tab3.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    iv=(ImageView)tab3.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
                }
                if (tabId.equals("tab2")) {
                    TextView title=(TextView)findViewById(R.id.title);
                    title.setText("联系人");
                    TextView tv=(TextView)tab1.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    ImageView iv=(ImageView)tab1.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo2));
                    tv=(TextView)tab2.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.blue));
                    iv=(ImageView)tab2.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren));
                    tv=(TextView)tab3.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    iv=(ImageView)tab3.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
                }
                if (tabId.equals("tab3")) {
                    TextView title=(TextView)findViewById(R.id.title);
                    title.setText("应用");
                    TextView tv=(TextView)tab1.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    ImageView iv=(ImageView)tab1.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo2));
                    tv=(TextView)tab2.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    iv=(ImageView)tab2.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
                    tv=(TextView)tab3.findViewById(R.id.tv);
                    tv.setTextColor(getResources().getColor(R.color.blue));
                    iv=(ImageView)tab3.findViewById(R.id.iv);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong));
                }

            }
        });

    }
}

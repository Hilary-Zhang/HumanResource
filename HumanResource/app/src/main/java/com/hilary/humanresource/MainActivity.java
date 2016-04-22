package com.hilary.humanresource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hilary.common.Params;


public class MainActivity extends AppCompatActivity {

    private TextView title;
    private ImageView iv_drawer;
    private ScrollView drawer;
    private DrawerLayout drawer_layout;
    private TabHost tab_host;
    private View tab_view[];
    private TextView tab_view_text[];
    private ImageView tab_view_image[];

    private SharedPreferences user_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);
        if(user_preferences.getString(Params.user_id,"").equals("")){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        //获取组件
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        drawer=(ScrollView)findViewById(R.id.drawer);
        drawer_layout=(DrawerLayout)findViewById(R.id.drawer_layout);
        tab_host=(TabHost)findViewById(android.R.id.tabhost);
        //设置左上角按钮
        iv_drawer.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawer_layout.isDrawerOpen(drawer)){
                    drawer_layout.openDrawer(drawer);
                }
            }
        });

        //设置tab
        if(tab_host!=null)tab_host.setup();
        tab_view=new View[3];
        tab_view_text=new TextView[3];
        tab_view_image=new ImageView[3];
        for(int i=0;i<3;i++){
            tab_view[i]=getLayoutInflater().inflate(R.layout.tab_button,null);
            tab_view_text[i]=(TextView)tab_view[i].findViewById(R.id.tv);
            tab_view_image[i]=(ImageView)tab_view[i].findViewById(R.id.iv);

        }
        tab_host.addTab(tab_host.newTabSpec("tab1").setContent(R.id.tv1).setIndicator(tab_view[0]));
        tab_host.addTab(tab_host.newTabSpec("tab2").setContent(R.id.tv2).setIndicator(tab_view[1]));
        tab_host.addTab(tab_host.newTabSpec("tab3").setContent(R.id.tv3).setIndicator(tab_view[2]));
        //设置tab文字
        tab_view_text[0].setText(getString(R.string.title_work));
        tab_view_text[1].setText(getString(R.string.title_contact));
        tab_view_text[2].setText(getString(R.string.title_application));
        //初始状态
        title.setText(getString(R.string.title_work));
        tab_view_text[0].setTextColor(getResources().getColor(R.color.blue));
        tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo));
        tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
        tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));

        tab_host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {
                    title.setText(getString(R.string.title_work));
                    tab_view_text[0].setTextColor(getResources().getColor(R.color.blue));
                    tab_view_text[1].setTextColor(getResources().getColor(R.color.black));
                    tab_view_text[2].setTextColor(getResources().getColor(R.color.black));
                    tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo));
                    tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
                    tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
                } else if (tabId.equals("tab2")) {
                    title.setText(getString(R.string.title_contact));
                    tab_view_text[0].setTextColor(getResources().getColor(R.color.black));
                    tab_view_text[1].setTextColor(getResources().getColor(R.color.blue));
                    tab_view_text[2].setTextColor(getResources().getColor(R.color.black));
                    tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo2));
                    tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren));
                    tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
                } else {
                    title.setText(getString(R.string.title_application));
                    tab_view_text[0].setTextColor(getResources().getColor(R.color.black));
                    tab_view_text[1].setTextColor(getResources().getColor(R.color.black));
                    tab_view_text[2].setTextColor(getResources().getColor(R.color.blue));
                    tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo2));
                    tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
                    tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong));
                }
            }
        });
    }
}

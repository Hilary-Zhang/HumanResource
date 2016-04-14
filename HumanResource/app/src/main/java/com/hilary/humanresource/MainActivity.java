package com.hilary.humanresource;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this, LoginActivity.class));

        TabHost mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        View tab1=getLayoutInflater().inflate(R.layout.tab_button,null);
        TextView tv= (TextView) tab1.findViewById(R.id.tv);
        tv.setText("Tab1");
         ImageView iv=(ImageView)tab1.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setContent(
                R.id.tv1).setIndicator(tab1));

        View tab2=getLayoutInflater().inflate(R.layout.tab_button,null);
        tv= (TextView) tab2.findViewById(R.id.tv);
        tv.setText("Tab2");
        iv=(ImageView)tab2.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setContent(
                R.id.tv2).setIndicator(tab2));

        View tab3=getLayoutInflater().inflate(R.layout.tab_button,null);
        tv= (TextView) tab3.findViewById(R.id.tv);
        tv.setText("Tab3");
        iv=(ImageView)tab3.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setContent(
                R.id.tv3).setIndicator(tab3));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

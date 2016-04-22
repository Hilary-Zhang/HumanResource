package com.hilary.humanresource;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cn.bmob.v3.Bmob;


public class LoginActivity extends ActionBarActivity {

    private TextView  title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        title=(TextView)findViewById(R.id.title);

        title.setText(getString(R.string.app_name));
    }
}

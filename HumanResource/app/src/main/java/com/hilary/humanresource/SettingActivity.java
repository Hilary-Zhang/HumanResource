package com.hilary.humanresource;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilary.common.Params;

public class SettingActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;
    private TextView banben,gongneng,help,fankui,exit;
    private Button btn_ok,btn_cancel;
    private SharedPreferences user_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        banben=(TextView)findViewById(R.id.banben);
        gongneng=(TextView)findViewById(R.id.gongneng);
        help=(TextView)findViewById(R.id.help);
        fankui=(TextView)findViewById(R.id.fankui);
        exit=(TextView)findViewById(R.id.exit);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        //toolbar返回按钮实现
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("设置");
        banben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,BanbenActivity.class));
            }
        });
        gongneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,GongnengActivity.class));
            }
        });
       help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,HelpActivity.class));
            }
        });
        fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,FankuiActivity.class));
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                final AlertDialog dialog = builder.create();
                btn_ok=(Button)view.findViewById(R.id.btn_ok);
                btn_cancel=(Button)view.findViewById(R.id.btn_cancle);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_preferences = getSharedPreferences(Params.user, Context.MODE_PRIVATE);
                        user_preferences.edit().clear().apply();
                        CloseActivity.exitClient(SettingActivity.this);
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      dialog.dismiss();
                    }
                });
                dialog.setView(view);
                dialog .show();
            }
        });
    }
}

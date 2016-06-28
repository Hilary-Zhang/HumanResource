package com.hilary.humanresource;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilary.common.Config;
import com.hilary.common.Params;
import com.hilary.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.CloudCodeListener;

public class LizhiActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer,iv_look;
    private TextView date;
    private EditText reason;
    private Button bt_apply;
    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lizhi);
        CloseActivity.activityList.add(this);
        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);


        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_look=(ImageView)findViewById(R.id.iv_look);
        date=(TextView)findViewById(R.id.date);
        reason=(EditText)findViewById(R.id.reason);
        bt_apply=(Button)findViewById(R.id.bt_apply);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_look.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_look));
        title.setText("离职");
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LizhiActivity.this,LizhiListActivity.class));
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                new DatePickerDialog(LizhiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(String.format("%d-%d-%d",year,monthOfYear,dayOfMonth));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date.getText().equals(getString(R.string.select))){
                    Utils.Toast(getApplicationContext(),"请选择离职时间!");
                    return;
                }
                if(TextUtils.isEmpty(reason.getText())){
                    Utils.Toast(getApplicationContext(),"请输入离职原因!");
                    return;
                }
                Map<String,String> params= new HashMap<>();
                params.put(Params.user_id,user_preferences.getString(Params.user_id,""));
                params.put(Params.date,date.getText().toString());
                params.put(Params.reason,reason.getText().toString());
                ace.callEndpoint(getApplicationContext(), Params.add_dismission, new JSONObject(params), new CloudCodeListener() {
                    @Override
                    public void onSuccess(Object o) {
                        try {
                            JSONObject data = new JSONObject((String) o);
                            Utils.Toast(getApplicationContext(),data.getString(Params.msg));
                        } catch (JSONException e) {
                            Utils.Toast(getApplicationContext(),R.string.alert_parse_json);
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        System.out.println(s);
                        Utils.Toast(getApplicationContext(),R.string.alert_request_error);
                    }
                });


            }
        });
    }
}

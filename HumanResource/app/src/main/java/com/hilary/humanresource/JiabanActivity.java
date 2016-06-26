package com.hilary.humanresource;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.TimePicker;

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

public class JiabanActivity extends AppCompatActivity {

    private Calendar calendar;

    private TextView date,begin,end;
    private EditText address,reason;
    private TextView title;
    private ImageView iv_drawer;
    private Button bt_save;

    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaban);

        calendar=Calendar.getInstance();
        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);

        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("加班");

        date=(TextView)findViewById(R.id.date);
        begin=(TextView)findViewById(R.id.begin);
        end=(TextView)findViewById(R.id.end);
        address=(EditText)findViewById(R.id.address);
        reason=(EditText)findViewById(R.id.reason);
        bt_save=(Button)findViewById(R.id.bt_save);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(JiabanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(String.format("%d-%d-%d",year,monthOfYear,dayOfMonth));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(JiabanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        begin.setText(String.format("%d:%d",hourOfDay,minute));
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(JiabanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        end.setText(String.format("%d:%d",hourOfDay,minute));
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date.getText().toString().equals(getString(R.string.select))){
                    Utils.Toast(getApplicationContext(),"请选择加班日期!");
                    return;
                }
                if(begin.getText().toString().equals(getString(R.string.select))){
                    Utils.Toast(getApplicationContext(),"请选择开始时间!");
                    return;
                }
                if(end.getText().toString().equals(getString(R.string.select))){
                    Utils.Toast(getApplicationContext(),"请选择结束时间!");
                    return;
                }
                if(TextUtils.isEmpty(address.getText())){
                    Utils.Toast(getApplicationContext(),"请输入加班地点!");
                    return;
                }
                if(TextUtils.isEmpty(reason.getText())){
                    Utils.Toast(getApplicationContext(),"请输入加班原因!");
                    return;
                }
                Map<String,String> params= new HashMap<>();
                params.put(Params.user_id,user_preferences.getString(Params.user_id,""));
                params.put(Params.date,date.getText().toString());
                params.put(Params.begin,begin.getText().toString());
                params.put(Params.end,end.getText().toString());
                params.put(Params.address,address.getText().toString());
                params.put(Params.reason,reason.getText().toString());
                ace.callEndpoint(getApplicationContext(), Params.add_overtime, new JSONObject(params), new CloudCodeListener() {
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

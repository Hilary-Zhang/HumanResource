package com.hilary.humanresource;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilary.common.Config;
import com.hilary.common.Params;
import com.hilary.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.CloudCodeListener;

public class XinxiActivity extends AppCompatActivity {
    private TextView title,touxiang;
    private ImageView iv_drawer;
    private Button bt_command;
    private AsyncCustomEndpoints ace; //在SDK调用云端逻辑
    private SharedPreferences user_Preferences;
    private EditText username,sex,age,department,phone_number,wechat,qq,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxi);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        touxiang=(TextView)findViewById(R.id.touxiang);
        username=(EditText)findViewById(R.id.username);
        sex=(EditText)findViewById(R.id.sex);
        age=(EditText)findViewById(R.id.age);
        department=(EditText)findViewById(R.id.department);
        phone_number=(EditText)findViewById(R.id.phone_number);
        wechat=(EditText)findViewById(R.id.wechat);
        qq=(EditText)findViewById(R.id.qq);
        address=(EditText)findViewById(R.id.address);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        bt_command=(Button)findViewById(R.id.bt_command);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        //toolbar返回按钮实现
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("个人信息");
        Bmob.initialize(this, Config.BMOB_APP_KEY);// 初始化 Bmob SDK
        ace = new AsyncCustomEndpoints();
        user_Preferences=getSharedPreferences(Params.user,Context.MODE_PRIVATE);
        final Map<String,String> params=new HashMap<>();
        params.put(Params.user_id,user_Preferences.getString(Params.user_id,user_Preferences.getString(Params.user_id,"")));
        //第一个参数是上下文对象，第二个参数是云端逻辑的方法名称，第三个参数是上传到云端逻辑的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
        ace.callEndpoint(getApplicationContext(), Params.get_information, new JSONObject(params), new CloudCodeListener() {

            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject result=new JSONObject((String) o);
                    if(result.getInt(Params.code)==1){//获取个人信息
                        JSONObject data = result.getJSONObject(Params.data);
                        touxiang.setText(data.getString(Params.username));
                        username.setText(data.getString(Params.username));
                        sex.setText(data.getString(Params.sex));
                        age.setText(data.getString(Params.age));
                        department.setText(data.getString(Params.department));
                        phone_number.setText(data.getString(Params.phone_number));
                        wechat.setText(data.getString(Params.wechat));
                        qq.setText(data.getString(Params.qq));
                        address.setText(data.getString(Params.address));
                    }
                    else{
                        Utils.Toast(getApplicationContext(), result.getString(Params.msg));
                    }
                } catch (JSONException e) {
                    Utils.Toast(getApplicationContext(), R.string.alert_parse_json);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Utils.Toast(getApplicationContext(), R.string.alert_request_error);
            }
        });

        bt_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt_command.getText().toString().equals("保存")){
                    Map<String,String> params=new HashMap<>();
                    params.put(Params.user_id,user_Preferences.getString(Params.user_id,user_Preferences.getString(Params.user_id,"")));
                    params.put(Params.age,age.getText().toString());
                    params.put(Params.phone_number,phone_number.getText().toString());
                    params.put(Params.wechat,wechat.getText().toString());
                    params.put(Params.qq,qq.getText().toString());
                    params.put(Params.address,address.getText().toString());
                    ace.callEndpoint(getApplicationContext(), Params.edit_information, new JSONObject(params), new CloudCodeListener() {
                        @Override
                        public void onSuccess(Object o) {
                            try {
                                JSONObject result=new JSONObject((String) o);
                                Utils.Toast(getApplicationContext(), result.getString(Params.msg));
                            } catch (JSONException e) {
                                Utils.Toast(getApplicationContext(), R.string.alert_parse_json);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Utils.Toast(getApplicationContext(), R.string.alert_request_error);
                        }
                    });
                    bt_command.setText("修改");
                    bt_command.setBackgroundColor(getResources().getColor(R.color.blue));
                    age.setEnabled(false);
                    phone_number.setEnabled(false);
                    wechat.setEnabled(false);
                    qq.setEnabled(false);
                    address.setEnabled(false);
                    age.setTextColor(getResources().getColor(R.color.gray));
                    phone_number.setTextColor(getResources().getColor(R.color.gray));
                    wechat.setTextColor(getResources().getColor(R.color.gray));
                    qq.setTextColor(getResources().getColor(R.color.gray));
                    address.setTextColor(getResources().getColor(R.color.gray));
                }else{
                    bt_command.setText("保存");
                    bt_command.setBackgroundColor(getResources().getColor(R.color.red));
                    age.setEnabled(true);
                    phone_number.setEnabled(true);
                    wechat.setEnabled(true);
                    qq.setEnabled(true);
                    address.setEnabled(true);
                    age.setTextColor(getResources().getColor(R.color.black));
                    phone_number.setTextColor(getResources().getColor(R.color.black));
                    wechat.setTextColor(getResources().getColor(R.color.black));
                    qq.setTextColor(getResources().getColor(R.color.black));
                    address.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
    }
}

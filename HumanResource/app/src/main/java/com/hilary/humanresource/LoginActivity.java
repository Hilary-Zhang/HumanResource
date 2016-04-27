package com.hilary.humanresource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class LoginActivity extends ActionBarActivity {

    private TextView title;
    private EditText et_username;
    private EditText et_password;
    private Button bt_login;
    //在SDK调用云端逻辑
    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化 Bmob SDK
        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);

        title=(TextView)findViewById(R.id.title);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        bt_login=(Button)findViewById(R.id.bt_login);

        title.setText(getString(R.string.app_name));

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置请求参数
                Map<String,String> params= new HashMap<>();
                params.put(Params.username, et_username.getText().toString());
                params.put(Params.password,et_password.getText().toString());
                ace.callEndpoint(getApplicationContext(), Params.login, new JSONObject(params), new CloudCodeListener() {
                    @Override
                    public void onSuccess(Object o) {
                        try {
                            JSONObject data = new JSONObject((String) o);
                            if(data.getInt(Params.code)==1){//登录成功
                                SharedPreferences.Editor editor=user_preferences.edit();
                                editor.putString(Params.user_id,data.getString(Params.user_id));
                                editor.putString(Params.username,data.getString(Params.username));
                                editor.apply();
                                Utils.Toast(getApplicationContext(), R.string.alert_login_success);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
                                Utils.Toast(getApplicationContext(),data.getString(Params.msg));
                            }
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

package com.hilary.humanresource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hilary.common.Config;
import com.hilary.common.Params;
import com.hilary.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.CloudCodeListener;

public class QiandaoActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;
    private TextView local;
    private ListView lv;
    private Button bt;

    private LocationClient mLocationClient;
    private BDLocation location;
    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiandao);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        local=(TextView)findViewById(R.id.local);
        lv=(ListView)findViewById(R.id.lv);
        bt=(Button)findViewById(R.id.bt);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        //toolbar返回按钮实现
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("签到");

        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);

        initLocation();
        initListView();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location!=null){
                    Map<String,String> params= new HashMap<>();
                    params.put(Params.user_id,user_preferences.getString(Params.user_id,""));
                    params.put(Params.latitude,String.valueOf(location.getLatitude()));
                    params.put(Params.longitude,String.valueOf(location.getLongitude()));
                    params.put(Params.address,location.getAddrStr());
                    ace.callEndpoint(getApplicationContext(), Params.sign_in, new JSONObject(params), new CloudCodeListener() {

                        @Override
                        public void onSuccess(Object o) {
                            try {
                                JSONObject data = new JSONObject((String) o);
                                if(data.getInt(Params.code)==1){//签到成功
                                    Utils.Toast(getApplicationContext(),"签到成功");
                                    initListView();
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
            }
        });
    }

    private void initLocation(){
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDLocationListener() {//注册监听函数
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                local.setText("当前位置："+bdLocation.getAddrStr());
                bt.setEnabled(true);
                //bt.setBackground(R.drawable.btn_qiandao2);
                location=bdLocation;
            }
        });
        mLocationClient.start();
    }

    private void initListView(){
        ace.callEndpoint(getApplicationContext(), Params.get_sign_list, new JSONObject(), new CloudCodeListener() {

            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){
                        JSONArray sign_list=data.getJSONArray(Params.data);
                        List<Map<String, String>> result=new ArrayList<>();
                        for(int i=0;i<sign_list.length();i++){
                            Map<String, String> map=new HashMap<>();
                            map.put(Params.name,sign_list.getJSONObject(i).getString(Params.username));
                            map.put(Params.address,sign_list.getJSONObject(i).getString(Params.address));
                            map.put(Params.sign_time,sign_list.getJSONObject(i).getString(Params.sign_time));
                            result.add(map);
                        }
                        lv.setAdapter(new SimpleAdapter(QiandaoActivity.this,result,R.layout.sign_item,new String[]{Params.name,Params.address,Params.sign_time},new int[]{R.id.name,R.id.address,R.id.sign_time}));
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
}

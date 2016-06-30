package com.hilary.humanresource;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hilary.common.Config;
import com.hilary.common.Params;
import com.hilary.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.CloudCodeListener;

public class RizhiListActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;
    private ListView lv;
    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rizhi_list);
        CloseActivity.activityList.add(this);

        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);

        lv=(ListView)findViewById(R.id.lv);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("日志列表");

        Map<String,String> params= new HashMap<>();
        params.put(Params.user_id,user_preferences.getString(Params.user_id,""));
        ace.callEndpoint(getApplicationContext(), Params.get_log, new JSONObject(params), new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){
                        JSONArray logs=data.getJSONArray(Params.data);
                        List<Map<String,String>> logs_data=new LinkedList<>();
                        for(int i=0;i<logs.length();i++){
                            Map<String,String> log_data=new HashMap<>();
                            log_data.put(Params.worked,logs.getJSONObject(i).getString(Params.worked));
                            log_data.put(Params.unwork,logs.getJSONObject(i).getString(Params.unwork));
                            log_data.put(Params.working,logs.getJSONObject(i).getString(Params.working));
                            log_data.put(Params.date,logs.getJSONObject(i).getString(Params.date));
                            logs_data.add( log_data);
                        }
                        adapter=new SimpleAdapter(RizhiListActivity.this,logs_data,R.layout.rizhi_item,new String[]{Params.worked,Params.worked,Params.working,Params.date},new int[]{R.id.worked,R.id.worked,R.id.working,R.id.date});
                        lv.setAdapter(adapter);
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

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

public class JiabanListActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv_drawer;
    private ListView lv;

    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaban_list);
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
        title.setText("加班列表");

        Map<String,String> params= new HashMap<>();
        params.put(Params.user_id,user_preferences.getString(Params.user_id,""));
        ace.callEndpoint(getApplicationContext(), Params.get_overtime, new JSONObject(params), new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){
                        JSONArray overtimes=data.getJSONArray(Params.data);
                        List<Map<String,String>> overtimes_data=new LinkedList<>();
                        for(int i=0;i<overtimes.length();i++){
                            Map<String,String> overtime_data=new HashMap<>();
                            overtime_data.put(Params.reason,overtimes.getJSONObject(i).getString(Params.reason));
                            overtime_data.put(Params.address,overtimes.getJSONObject(i).getString(Params.address));
                            overtime_data.put(Params.time,overtimes.getJSONObject(i).getString(Params.begin)+"-"+overtimes.getJSONObject(i).getString(Params.end));
                            overtime_data.put(Params.date,overtimes.getJSONObject(i).getString(Params.date));
                            overtimes_data.add(overtime_data);
                        }
                        adapter=new SimpleAdapter(JiabanListActivity.this,overtimes_data,R.layout.list_item,new String[]{Params.reason,Params.address,Params.date,Params.time},new int[]{R.id.one,R.id.two,R.id.three,R.id.four});
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

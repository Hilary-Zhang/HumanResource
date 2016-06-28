package com.hilary.humanresource;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
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

public class XiangmuActivity extends AppCompatActivity {
    private ImageView iv_drawer;
    private TextView title;
    private TabHost tabHost;
    private TextView tab_text[];
    private ListView lv_unwork,lv_working,lv_worked;
    private AsyncCustomEndpoints ace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangmu);
        CloseActivity.activityList.add(this);
        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        lv_unwork=(ListView)findViewById(R.id.lv_unwork);
        lv_working=(ListView)findViewById(R.id. lv_working);
        lv_worked=(ListView)findViewById(R.id.lv_worked);
        title.setText("项目管理");
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabHost = (TabHost) findViewById(R.id.tabhost);
        //设置tab
        tab_text = new TextView[3];
        tab_text[0]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        tab_text[1]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        tab_text[2]= (TextView) LayoutInflater.from(this).inflate(R.layout.tab_text,tabHost.getTabContentView());
        //设置tab文字
        tab_text[0].setText("未处理");
        tab_text[1].setText("处理中");
        tab_text[2].setText("已处理");
        //初始状态
        tab_text[0].setTextColor(ContextCompat.getColor(this,R.color.blue));
        tab_text[1].setTextColor(ContextCompat.getColor(this,R.color.black));
        tab_text[2].setTextColor(ContextCompat.getColor(this,R.color.black));
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setContent(R.id.unwork).setIndicator(tab_text[0]));
        tabHost.addTab(tabHost.newTabSpec("tab2").setContent(R.id.working).setIndicator(tab_text[1]));
        tabHost.addTab(tabHost.newTabSpec("tab3").setContent(R.id.worked).setIndicator(tab_text[2]));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        tab_text[0].setTextColor(getResources().getColor(R.color.blue));
                        tab_text[1].setTextColor(getResources().getColor(R.color.black));
                        tab_text[2].setTextColor(getResources().getColor(R.color.black));
                        break;
                    case "tab2":
                        tab_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_text[1].setTextColor(getResources().getColor(R.color.blue));
                        tab_text[2].setTextColor(getResources().getColor(R.color.black));
                        break;
                    case "tab3":
                        tab_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_text[1].setTextColor(getResources().getColor(R.color.black));
                        tab_text[2].setTextColor(getResources().getColor(R.color.blue));
                        break;
                }
            }
        });

        Map<String,String> params= new HashMap<>();
        params.put(Params.status,"1");
        ace.callEndpoint(getApplicationContext(), Params.get_project, new JSONObject(params), new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){
                        JSONArray projects=data.getJSONArray(Params.data);
                        List<Map<String,String>> prjects_data=new LinkedList<>();
                        for(int i=0;i<projects.length();i++){
                            Map<String,String> project_data=new HashMap<>();
                            project_data.put(Params.name,projects.getJSONObject(i).getString(Params.name));
                            project_data.put(Params.detail,projects.getJSONObject(i).getString(Params.detail));
                            prjects_data.add( project_data);
                        }
                        lv_unwork.setAdapter(new SimpleAdapter(XiangmuActivity.this,prjects_data,R.layout.xiangmu_item,new String[]{Params.name,Params.detail},new int[]{R.id.xiangmu_title,R.id.xiangmu_detail}));
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
        params.put(Params.status,"2");
        ace.callEndpoint(getApplicationContext(), Params.get_project, new JSONObject(params), new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){
                        JSONArray projects=data.getJSONArray(Params.data);
                        List<Map<String,String>> prjects_data=new LinkedList<>();
                        for(int i=0;i<projects.length();i++){
                            Map<String,String> project_data=new HashMap<>();
                            project_data.put(Params.name,projects.getJSONObject(i).getString(Params.name));
                            project_data.put(Params.detail,projects.getJSONObject(i).getString(Params.detail));
                            prjects_data.add( project_data);
                        }
                        lv_working.setAdapter(new SimpleAdapter(XiangmuActivity.this,prjects_data,R.layout.xiangmu_item,new String[]{Params.name,Params.detail},new int[]{R.id.xiangmu_title,R.id.xiangmu_detail}));
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

        params.put(Params.status,"3");
        ace.callEndpoint(getApplicationContext(), Params.get_project, new JSONObject(params), new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){
                        JSONArray projects=data.getJSONArray(Params.data);
                        List<Map<String,String>> prjects_data=new LinkedList<>();
                        for(int i=0;i<projects.length();i++){
                            Map<String,String> project_data=new HashMap<>();
                            project_data.put(Params.name,projects.getJSONObject(i).getString(Params.name));
                            project_data.put(Params.detail,projects.getJSONObject(i).getString(Params.detail));
                            prjects_data.add( project_data);
                        }
                        lv_worked.setAdapter(new SimpleAdapter(XiangmuActivity.this,prjects_data,R.layout.xiangmu_item,new String[]{Params.name,Params.detail},new int[]{R.id.xiangmu_title,R.id.xiangmu_detail}));
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

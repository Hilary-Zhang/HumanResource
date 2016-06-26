package com.hilary.humanresource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.CloudCodeListener;


public class GonggaoActivity extends AppCompatActivity {
    private ImageView iv_drawer;
    private TextView title;
    private ListView lv_news;
    private ListAdapter adapter;
    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao);
        CloseActivity.activityList.add(this);
        title=(TextView)findViewById(R.id.title);
        iv_drawer=(ImageView)findViewById(R.id.iv_drawer);
        iv_drawer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back));
        lv_news=(ListView)findViewById(R.id.lv_news);

        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace=new AsyncCustomEndpoints();
        user_preferences=getSharedPreferences(Params.user, Context.MODE_PRIVATE);
        //toolbar返回按钮实现
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("公司公告");

        ace.callEndpoint(getApplicationContext(), Params.get_notice, new JSONObject(), new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                try {
                    JSONObject data = new JSONObject((String) o);
                    if(data.getInt(Params.code)==1){//登录成功
                        JSONArray notices=data.getJSONArray(Params.data);
                        List<Map<String, String>> notices_data = new ArrayList<>();
                        for(int i=0;i<notices.length();i++){
                            Map<String,String> notice_data=new HashMap<>();
                            notice_data.put(Params.objectId,notices.getJSONObject(i).getString(Params.objectId));
                            notice_data.put(Params.title,notices.getJSONObject(i).getString(Params.title));
                            notice_data.put(Params.createdAt,notices.getJSONObject(i).getString(Params.createdAt));
                            notice_data.put(Params.content,notices.getJSONObject(i).getString(Params.content));
                            notices_data.add(notice_data);
                        }
                        adapter=new SimpleAdapter(GonggaoActivity.this,notices_data,R.layout.gonggao_item,new String[]{Params.title,Params.createdAt},new int[]{R.id.news_title,R.id.news_time});
                        lv_news.setAdapter(adapter);
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
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter==null)return;
                Map<String,String> data= (Map<String, String>) adapter.getItem(position);

                Intent i=new Intent(GonggaoActivity.this,GonggaoXiangqingActivity.class);
                i.putExtra(Params.title,data.get(Params.title));
                i.putExtra(Params.content,data.get(Params.content));
                startActivity(i);
            }
        });
    }
}

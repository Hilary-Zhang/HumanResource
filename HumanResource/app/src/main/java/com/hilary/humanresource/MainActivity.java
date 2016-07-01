package com.hilary.humanresource;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TabHost;
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


public class MainActivity extends AppCompatActivity {

    private TextView title;
    private ImageView iv_drawer;
    private ScrollView drawer;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer_layout;
    private TabHost tab_host;
    private ExpandableListView ep;
    private View tab_view[];
    private TextView tab_view_text[],touxiang;
    private ImageView tab_view_image[];
    private LinearLayout ll_shouye,ll_xinxi, ll_zhidu, ll_gonggao, ll_setting;
    private RelativeLayout rl_qiandao, rl_qingjia, rl_chuchai, rl_jiaban, rl_lizhi, rl_hetong;
    private RelativeLayout rl_xinzi, rl_wenjuan, rl_peixun, rl_rizhi, rl_xiangmu;
    //在SDK调用云端逻辑
    private AsyncCustomEndpoints ace;
    private SharedPreferences user_preferences;
    private SimpleExpandableListAdapter adapter;
    private String select_items[]={"拨打电话","发送短信"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        // 初始化 Bmob SDK
        Bmob.initialize(this, Config.BMOB_APP_KEY);
        ace = new AsyncCustomEndpoints();
        user_preferences = getSharedPreferences(Params.user, Context.MODE_PRIVATE);
        if (user_preferences.getString(Params.user_id, "").equals("")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        initView();//Tab切换图标及文字变化
        contact(); //联系人界面
        drawer();//右划界面
        work();//工作界面的实现
        apply();//应用界面实现
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    //Tab切换设置
    private void initView() {
        //获取组件
        title = (TextView) findViewById(R.id.title);
        iv_drawer = (ImageView) findViewById(R.id.iv_drawer);
        drawer = (ScrollView) findViewById(R.id.drawer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tab_host = (TabHost) findViewById(android.R.id.tabhost);
        ep = (ExpandableListView) findViewById(R.id.ep1);
        //设置左上角按钮
        //iv_drawer.setImageDrawable(ContextCompat.getDrawable(android.R.drawable.dr));
        iv_drawer.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer_layout.isDrawerOpen(drawer)) {
                    drawer_layout.openDrawer(drawer);
                }
            }
        });
        //drawer
        toggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawer_layout.setDrawerListener(toggle);

        //设置tab
        if (tab_host != null) tab_host.setup();
        tab_view = new View[3];
        tab_view_text = new TextView[3];
        tab_view_image = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            tab_view[i] = getLayoutInflater().inflate(R.layout.tab_button, null);
            tab_view_text[i] = (TextView) tab_view[i].findViewById(R.id.tv);
            tab_view_image[i] = (ImageView) tab_view[i].findViewById(R.id.iv);

        }
        tab_host.addTab(tab_host.newTabSpec("tab1").setContent(R.id.tv1).setIndicator(tab_view[0]));
        tab_host.addTab(tab_host.newTabSpec("tab2").setContent(R.id.tv2).setIndicator(tab_view[1]));
        tab_host.addTab(tab_host.newTabSpec("tab3").setContent(R.id.tv3).setIndicator(tab_view[2]));
        //设置tab文字
        tab_view_text[0].setText(getString(R.string.title_work));
        tab_view_text[1].setText(getString(R.string.title_contact));
        tab_view_text[2].setText(getString(R.string.title_application));
        //初始状态
        title.setText(getString(R.string.title_work));
        tab_view_text[0].setTextColor(getResources().getColor(R.color.blue));
        tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo));
        tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
        tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));

        tab_host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        title.setText(getString(R.string.title_work));
                        tab_view_text[0].setTextColor(getResources().getColor(R.color.blue));
                        tab_view_text[1].setTextColor(getResources().getColor(R.color.black));
                        tab_view_text[2].setTextColor(getResources().getColor(R.color.black));
                        tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo));
                        tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
                        tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
                        break;
                    case "tab2":
                        title.setText(getString(R.string.title_contact));
                        tab_view_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_view_text[1].setTextColor(getResources().getColor(R.color.blue));
                        tab_view_text[2].setTextColor(getResources().getColor(R.color.black));
                        tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo2));
                        tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren));
                        tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong2));
                        break;
                    default:
                        title.setText(getString(R.string.title_application));
                        tab_view_text[0].setTextColor(getResources().getColor(R.color.black));
                        tab_view_text[1].setTextColor(getResources().getColor(R.color.black));
                        tab_view_text[2].setTextColor(getResources().getColor(R.color.blue));
                        tab_view_image[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_gongzuo2));
                        tab_view_image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_lianxiren2));
                        tab_view_image[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_yingyong));
                        break;
                }
            }
        });
    }

    //联系人界面
    private void contact() {
        //第一个参数是上下文对象，第二个参数是云端逻辑的方法名称，第三个参数是上传到云端逻辑的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
        ace.callEndpoint(getApplicationContext(), Params.get_contact_table, new JSONObject(), new CloudCodeListener() {
            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject result = new JSONObject((String) object);
                    if (result.getInt(Params.code) == 1) {//获取部门和联系人
                        JSONArray data = result.getJSONArray(Params.data);
                        List<Map<String, String>> department_data = new ArrayList<>();
                        List<List<Map<String, String>>> member_data = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject department = data.getJSONObject(i);
                            Map<String, String> maps = new HashMap<>();
                            maps.put(Params.name, department.getString(Params.name));
                            department_data.add(maps);
                            JSONArray members = department.getJSONArray(Params.member);
                            List<Map<String, String>> child = new ArrayList<>();
                            for (int j = 0; j < members.length(); j++) {
                                JSONObject member = members.getJSONObject(j);
                                Map<String, String> temp = new HashMap<>();
                                temp.put(Params.member, member.getString(Params.name));
                                temp.put(Params.phone_number, member.getString(Params.phone_number));
                                child.add(temp);
                            }
                            member_data.add(child);
                        }
                        adapter=new SimpleExpandableListAdapter(getApplicationContext(), department_data, R.layout.department, new String[]{Params.name},
                                new int[]{R.id.department}, member_data, R.layout.member, new String[]{Params.member}, new int[]{R.id.member});
                        ep.setAdapter(adapter);
                        ep.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                Map<String, String> temp= (Map<String, String>) adapter.getChild(groupPosition,childPosition);
                                final String phone_number=temp.get(Params.phone_number);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(temp.get(Params.member))
                                        .setItems(select_items, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(which==0){
                                                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone_number)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                                }else if(which==1){
                                                    startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+phone_number)));
                                                }
                                            }
                                        })
                                        .show();
                                return true;
                            }
                        });
                    } else {
                        Utils.Toast(getApplicationContext(), result.getString(Params.msg));
                    }

                } catch (JSONException e) {
                    Utils.Toast(getApplicationContext(), R.string.alert_parse_json);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                Utils.Toast(getApplicationContext(), R.string.alert_request_error);
            }
        });
    }

    //右划界面实现
    private void drawer() {

        touxiang=(TextView)findViewById(R.id.touxiang);
        touxiang.setText(user_preferences.getString(Params.username,""));

        ll_shouye=(LinearLayout)findViewById(R.id.shouye);
        ll_shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer_layout.isDrawerOpen(drawer)) {
                    drawer_layout.closeDrawer(drawer);
                }
            }
        });
        ll_xinxi = (LinearLayout) findViewById(R.id.xinxi);
        ll_xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, XinxiActivity.class));
            }
        });
        ll_zhidu = (LinearLayout) findViewById(R.id.zhidu);
        ll_zhidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ZhiduActivity.class));
            }
        });
        ll_gonggao = (LinearLayout) findViewById(R.id.gonggao);
        ll_gonggao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GonggaoActivity.class));
            }
        });
        ll_setting = (LinearLayout) findViewById(R.id.setting);
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

    }

    //工作界面的实现
    private void work() {
        rl_qiandao = (RelativeLayout) findViewById(R.id.rl_qiandao);
        rl_qingjia = (RelativeLayout) findViewById(R.id.rl_qingjia);
        rl_chuchai = (RelativeLayout) findViewById(R.id.rl_chuchai);
        rl_jiaban = (RelativeLayout) findViewById(R.id.rl_jiaban);
        rl_lizhi = (RelativeLayout) findViewById(R.id.rl_lizhi);
        rl_hetong = (RelativeLayout) findViewById(R.id.rl_hetong);
        rl_qiandao.setClickable(true);
        rl_qingjia.setClickable(true);
        rl_chuchai.setClickable(true);
        rl_jiaban.setClickable(true);
        rl_lizhi.setClickable(true);
        rl_hetong.setClickable(true);
        rl_qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QiandaoActivity.class));
            }
        });
        rl_qingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QingjiaActivity.class));
            }
        });
        rl_chuchai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChuchaiActivity.class));
            }
        });
        rl_jiaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JiabanActivity.class));
            }
        });
        rl_lizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LizhiActivity.class));
            }
        });
        rl_hetong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HetongActivity.class));
            }
        });


    }

    //应用界面实现
    private void apply(){
        rl_xinzi=(RelativeLayout)findViewById(R.id.rl_xinzi);
        rl_wenjuan=(RelativeLayout)findViewById(R.id.rl_wenjuan);
        rl_peixun=(RelativeLayout)findViewById(R.id.rl_peixun);
        rl_rizhi = (RelativeLayout) findViewById(R.id.rl_rizhi);
        rl_xiangmu = (RelativeLayout) findViewById(R.id.rl_xiangmu);
        rl_xinzi.setClickable(true);
        rl_wenjuan.setClickable(true);
        rl_peixun.setClickable(true);
        rl_rizhi.setClickable(true);
        rl_xiangmu.setClickable(true);
        rl_xinzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,XinziActivity.class));
            }
        });
        rl_wenjuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WenjuanActivity.class));
            }
        });
        rl_peixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PeixunActivity.class));
            }
        });

        rl_rizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RizhiActivity.class));
            }
        });
        rl_xiangmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, XiangmuActivity.class));
            }
        });



    }
}

package com.iseeyou.lsspview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.iseeyou.lsspview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前言：
 * 初步的完成App中的功能
 * 没有考虑到本库的一个架构设计
 * 在扩展性上面很是欠缺
 * 也没有详细的设计主要是以完成功能为主
 * 此点后面会去详细的设计以及修改
 *
 * 对本库的架构做一个简单的整改
 */
public class MainActivity extends AppCompatActivity {

    SimpleAdapter simpleAdapter;
    ListView listView;
    private List<Map<String, String>> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置标题
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        listView= (ListView) findViewById(R.id.listView);
        //添加数据
        datas=new ArrayList<>();
        String[] titles=getResources().getStringArray(R.array.titles);
        String[] infos=getResources().getStringArray(R.array.infos);
        for (int i = 0; i < titles.length; i++) {
            Map<String, String> listem = new HashMap<String, String>();
            listem.put("title", titles[i]);
            listem.put("infos", infos[i]);
            datas.add(listem);
        }
        //设置Adapter
        simpleAdapter=new SimpleAdapter(this,datas,
                android.R.layout.simple_list_item_2,new String[]{"title","infos"}
                ,new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        //设置监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, ShowViewActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

}

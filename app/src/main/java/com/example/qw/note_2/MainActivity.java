package com.example.qw.note_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity
{

    private TextView add;

    private TextView search_btn;

    private EditText search_text;

    private DBManager dbManager;

    private ListView listView;

    private ArrayList<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        search_text = findViewById(R.id.search_text);
        search_btn = findViewById(R.id.search_btn);
        listView = findViewById(R.id.listview);
        dbManager = new DBManager(this);
        query();
        add.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        search_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 String tx = search_text.getText().toString();
                 Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                 intent.putExtra("search_text",tx);
                 startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra("title", list.get(position).get("title"));
                intent.putExtra("text", list.get(position).get("text"));
                intent.putExtra("date",list.get(position).get("date"));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                query();
                return false;
            }
        });

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbManager.closeDB();// 释放数据库资源
    }
    private void query()
    {
        List<Info> persons = dbManager.query();
        list = new ArrayList<>();
        for (Info person : persons)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("title", person.title);
            map.put("text",  person.text);
            map.put("date",  person.date);
            list.add(map);
        }
        MyAdapter adapter = new MyAdapter(list, this);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        query();
    }
}


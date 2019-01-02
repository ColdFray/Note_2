package com.example.qw.note_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class SearchActivity extends Activity
{

    private DBManager dbManager;

    private TextView first_btn;

    private TextView src_res;

    private ListView listView;

    private ArrayList<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String tx = intent.getStringExtra("search_text");

        first_btn = findViewById(R.id.first);
        src_res   = findViewById(R.id.src_res);
        listView  = findViewById(R.id.listview2);
        dbManager = new DBManager(this);
        query(tx);

        src_res.setText(tx);

        first_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(SearchActivity.this, NewActivity.class);
                intent.putExtra("title", list.get(position).get("title"));
                intent.putExtra("text", list.get(position).get("text"));
                intent.putExtra("date", list.get(position).get("date"));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = getIntent();
                String tx = intent.getStringExtra("search_text");
                query(tx);
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

    private void query(String tx)
    {
        List<Info> persons = dbManager.search(tx);
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
        Intent intent = getIntent();
        String tx = intent.getStringExtra("search_text");
        query(tx);
    }
}



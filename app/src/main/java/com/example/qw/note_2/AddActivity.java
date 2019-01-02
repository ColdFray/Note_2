package com.example.qw.note_2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity
{

    private EditText info_title1;

    private EditText info_text1;

    private Button add;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        info_title1 = findViewById(R.id.info_title1);
        info_text1  = findViewById(R.id.info_text1);
        add         = findViewById(R.id.add);
        dbManager = new DBManager(this);
        add.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add();
                finish();
            }
        });
    }
    public void add()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = sdf.format(date);
        ArrayList<Info> persons = new ArrayList<Info>();
        Info person1 = new Info(info_title1.getText().toString(), info_text1.getText().toString(),dateString);
        persons.add(person1);
        dbManager.add(persons);
    }

}

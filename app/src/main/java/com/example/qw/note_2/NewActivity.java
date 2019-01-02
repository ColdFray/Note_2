package com.example.qw.note_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewActivity extends Activity
{

    private EditText info_title;

    private EditText info_text;

    private TextView dateText;

    private Button update,delete;

    private DBManager dbManager;

    private String title1, text1, date1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        info_title = findViewById(R.id.info_title);
        info_text  = findViewById(R.id.info_text);
        dateText   = findViewById(R.id.date);
        update     = findViewById(R.id.update);
        delete     = findViewById(R.id.delete);
        Intent intent = getIntent();

        title1 = intent.getStringExtra("title");
        info_title.setText(title1);
        text1 = intent.getStringExtra("text");
        info_text.setText(text1);
        date1 = intent.getStringExtra("date");
        dateText.setText(date1);



        update.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                update(info_title.getText().toString(), info_text.getText().toString());
                Toast.makeText(NewActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        delete.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                delete(info_title.getText().toString());
                Toast.makeText(NewActivity.this, "刪除成功", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        dbManager = new DBManager(this);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dbManager.closeDB();
    }

    public void update(String title, String text)
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String now = sdf.format(date);

        Info person = new Info();
        person.title = title;
        person.text = text;
        person.date = now;

        Info person2 = new Info();
        person2.title = title1;
        person2.text = text1;
        person2.date = date1;
        dbManager.updateAge(person, person2);
    }

    public void delete(String title)
    {
        Info person = new Info();
        person.title = title;
        dbManager.deleteOldPerson(person);
    }
}


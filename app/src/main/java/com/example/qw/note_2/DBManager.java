package com.example.qw.note_2;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager
{
    private DatabaseHelper helper;

    private SQLiteDatabase db;

    public DBManager(Context context)
    {
        helper = new DatabaseHelper(context);
        // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
        // mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }
    /**
     * add persons
     *
     * @parampersons
     */
    public void add(List<Info> infos)
    {
        db.beginTransaction(); // 开始事务
        try
        {
            for (Info info : infos)
            {
                db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME + " VALUES(NULL, ?, ?, ?)", new Object[] {info.title, info.text, info.date});
            }
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * update person's age
     *
     * @param person
     */
    public void updateAge(Info person, Info person2)
    {
        ContentValues cv = new ContentValues();
        cv.put("title", person.title);
        cv.put("text", person.text);
        cv.put("date", person.date);
        db.update(DatabaseHelper.TABLE_NAME, cv, "title = ? and  text=? and date = ?", new String[] {person2.title, person2.text,person2.date});
    }

    /**
     * delete old person
     *
     * @param person
     */
    public void deleteOldPerson(Info person)
    {
        db.delete(DatabaseHelper.TABLE_NAME, "title = ?", new String[] {String.valueOf(person.title)});
    }

    /**
     * query all persons, return list
     *
     * @return List<Person>
     */
    public List<Info> query()
    {
        ArrayList<Info> persons = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext())
        {
            Info person = new Info();
            person.title = c.getString(c.getColumnIndex("title"));
            person.text = c.getString(c.getColumnIndex("text"));
            person.date = c.getString(c.getColumnIndex("date"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    public List<Info> search(String tx)
    {
        ArrayList<Info> persons = new ArrayList<>();
        Cursor c = searchTheCursor(tx);
        while (c.moveToNext())
        {
            Info person = new Info();
            person.title = c.getString(c.getColumnIndex("title"));
            person.text = c.getString(c.getColumnIndex("text"));
            person.date = c.getString(c.getColumnIndex("date"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor()
    {
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        return c;
    }

    public Cursor searchTheCursor(String tx)
    {
        String[] t  = new String[]{"%"+tx+"%","%"+tx+"%","%"+tx+"%"};
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE title LIKE ? or text LIKE ? or date LIKE ?" ,t);
        return c;
    }

    /**
     * close database
     */
    public void closeDB()
    {
        // 释放数据库资源
        db.close();
    }

}


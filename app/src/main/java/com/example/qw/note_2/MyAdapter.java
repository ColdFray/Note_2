package com.example.qw.note_2;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter
{
    private ArrayList<Map<String, String>> list;
    private Context context;
    public MyAdapter(ArrayList<Map<String, String>> list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount()
    {
        return null!=list?list.size():0;
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder=null;
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder=new Holder();
            holder.title=convertView.findViewById(R.id.item_text1);
            holder.text=convertView.findViewById(R.id.item_text2);
            holder.date=convertView.findViewById(R.id.item_text3);
            convertView.setTag(holder);
        }else
        {
            holder=(Holder)convertView.getTag();
        }
        holder.title.setText(list.get(position).get("title"));
        holder.text.setText(list.get(position).get("text"));
        holder.date.setText(list.get(position).get("date"));
        return convertView;
    }
    class Holder
    {
        TextView title;
        TextView text;
        TextView date;
    }

}


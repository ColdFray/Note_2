package com.example.qw.note_2;

public class Info {

    public String title;
    public int id;
    public String text;
    public String date;

    public Info() {
    }

    public Info(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Info(String title,String text,String date){
        this.date = date;
        this.text = text;
        this.title = title;
    }
}

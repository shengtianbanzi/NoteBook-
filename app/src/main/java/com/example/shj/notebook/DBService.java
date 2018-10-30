package com.example.shj.notebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

public class DBService {

    private static SQLiteDatabase db = null;

    static {
        //新建或者打开db
        db = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.shj.notebook/NoteBook.db", null);

        String sql = "create table NoteBook(_id integer primary key autoincrement,title varchar(255),content TEXT, createTime varchar(25))";

        //判断是否存在表NoteBook，如果不存在会抛出异常，捕捉异常后创建表
        try{
            db.rawQuery("select count(1) from NoteBook ",null);
        }catch(Exception e){
            db.execSQL(sql);
        }
    }

    public static SQLiteDatabase getSQLiteDatabase(){
        return db;
    }

    public static Cursor queryAll(){
        return db.rawQuery("select * from NoteBook ",null);
    }

    public static Cursor queryNoteById(Integer id){
        return db.rawQuery("select * from NoteBook where _id =?",new String[]{id.toString()});
    }

    public static void deleteNoteById(Integer id){
        if(id == null)
            return ;
        db.delete("NoteBook", "_id=?", new String[]{id.toString()});
    }

    public static void updateNoteById(Integer id, ContentValues values){
        db.update("NoteBook", values, "_id=?", new String[]{id.toString()});
    }

    /**
     * 添加一个笔记，并且记录当前添加的时间
     */
    public static void addNote(ContentValues values){
        values.put("createTime", DateFormat.format("yyyy-MM-dd kk:mm:ss", System.currentTimeMillis()).toString());
        db.insert("NoteBook", null, values);
    }
}
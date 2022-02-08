package com.shivam.webscrapper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper {
    private static final String history="SearchHistory.db";
    public DbManager(Context context) {
        super(context, history, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry="create table SearchHistory(id integer primary key autoincrement,title text,url text,image text,date text)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SearchHistory");
        onCreate(db);
    }
    public Cursor populateListview(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM SearchHistory", null);
        return res;
    }
    public String delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SearchHistory");
        db.close();
        return "History Deleted";
    }
    public String deleteRow(int position){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SearchHistory where id="+position);
        db.close();
        return "History Deleted";
    }

    public String addHistory(String title,String url,String image,String time){
        SQLiteDatabase ob=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("title",title);
        cv.put("url",url);
        cv.put("image",image);
        cv.put("date",time);
        long res= ob.insert("SearchHistory",null,cv);
        if(res==-1){
            return "failed";

        }else{
            return "success";
        }


    }
}

package com.priyanka.todoappandroid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.priyanka.todoappandroid.model.toDoModel;

import java.util.ArrayList;
import java.util.List;

public class databaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";

    public databaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTask(toDoModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,model.getTask());
        values.put(COL_3,0);
        db.insert(TABLE_NAME,null,values);
        db.close();

    }

    public void updateTask(int id,String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , task);
        db.update(TABLE_NAME , values,"ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateStatus(int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3,status);
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public List<toDoModel> getAllTask(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<toDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if (cursor !=null){
                if(cursor.moveToFirst()){
                    do{
                        toDoModel task = new toDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndexOrThrow(COL_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COL_3)));
                        modelList.add(task);
                    }while(cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
            db.close(); // Close the database connection after use
        }
        return modelList;

    }
}

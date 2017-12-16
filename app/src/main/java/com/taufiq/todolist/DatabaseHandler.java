package com.taufiq.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taufiq on 13/12/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ToDoDesember";
    private static final String TABLE_TODO="todos";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";

    SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +KEY_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    void addToDo(String todo) {
        db= this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,todo);

        db.insert(TABLE_TODO,null,values);
        db.close();
    }

    public List<String> getAllToDo() {
        List<String> toDoList = new ArrayList<String>();
        String selectQuery = "SELECT * FROM "+ TABLE_TODO;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                toDoList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        return toDoList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO);
        onCreate(db);
    }

    public void deleteToDo(String todo){
        db =this.getWritableDatabase();
        db.delete(TABLE_TODO,KEY_NAME+ " = ?",
                new String[] {String.valueOf(todo)});
        db.close();
    }
}

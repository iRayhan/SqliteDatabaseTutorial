package com.irayhan.sqlitedatabasetutorial;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "SampleDb.db";
    private static final String TABLE_NAME = "Database_Table";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String AGE = "AGE";
    private static final String INTEREST = "INTEREST";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT NOT NULL,AGE INTEGER NOT NULL,INTEREST TEXT NOT NULL)");
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }


    public boolean insertData(String name, Integer age, String interest){


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(INTEREST, interest);

        long result = db.insert(TABLE_NAME, null, contentValues);


        db.close();


        return result != -1;


    }



    public List<String> getAllData(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        int index;
        List<String> nameList = new ArrayList<>();

        while(cursor.moveToNext()){

            index = cursor.getColumnIndexOrThrow(NAME);
            nameList.add(cursor.getString(index));

        }

        db.close();

        return nameList;

    }







    public String getAll(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + TABLE_NAME + " where "+ NAME + " = '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);


        int index;
        String age = "";
        String interest = "";


        while(cursor.moveToNext()){

            index = cursor.getColumnIndexOrThrow(AGE);
            age = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow(INTEREST);
            interest = cursor.getString(index);

        }


        db.close();


        return "Name: " + name + ", Age: " + age + ", Interest: " + interest;


    }








    public boolean hasName(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select " + NAME + " from " + TABLE_NAME + " where "+ NAME + " = '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);

        int index = 0;

        while(cursor.moveToNext()){

            index++;

        }

        return index > 0;


    }








    public String getId(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select " + ID + " from " + TABLE_NAME + " where "+ NAME + " = '" + name + "'";
        Cursor cursor = db.rawQuery(query, null);


        int index;
        String id = "";


        while(cursor.moveToNext()){

            index = cursor.getColumnIndexOrThrow(ID);
            id = cursor.getString(index);

        }


        return id;


    }






    public boolean updateData(String name, Integer age, String interest){


        String id = getId(name);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(INTEREST, interest);

        db.update(TABLE_NAME, contentValues, "ID = " + id, null);


        db.close();

        return true;


    }





    public boolean deleteData(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        if(hasName(name)) {
            db.execSQL("delete from " + TABLE_NAME + " where "+ NAME + " = '" + name + "'");
            db.close();
            return true;
        }
        else {
            return false;
        }

    }




    public boolean deleteAll(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_NAME);

        db.close();


        return  true;

    }









}


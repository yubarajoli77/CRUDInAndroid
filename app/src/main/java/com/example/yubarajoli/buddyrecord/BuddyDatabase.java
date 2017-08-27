package com.example.yubarajoli.buddyrecord;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yubar on 8/26/2017.
 */

public class BuddyDatabase extends SQLiteOpenHelper {
public static final String DATABASE_NAME="Buddy.db";
    public static final String TABLE_NAME="BuddyInfo";
    public static final String COL1="ID";
    public static final String COL2="NAME";
    public static final String COL3="ADDRESS";
    public static final String COL4="PHONE";
    public static final String COL5="EMAIL";
    public BuddyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, " +
                "ADDRESS TEXT, PHONE TEXT, EMAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS" + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean insertData(String name, String address, String phone,
                              String email){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,address);
        contentValues.put(COL4,phone);
        contentValues.put(COL5,email);
        Long value= sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
        if(value==-1)
            return false;
        else
            return true;


    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return res;
    }
    public Cursor getById(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE ID= ?",new String[]{id});
        return res;

    }
    public boolean updateData(String id,String name,String address,
                              String phone, String email){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,address);
        contentValues.put(COL4,phone);
        contentValues.put(COL5,email);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id=?",new String[]{id});
        return true;
    }
    public boolean deleteData(String id){
        SQLiteDatabase sqliteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        sqliteDatabase.delete(TABLE_NAME,"id=?",new String[]{id});
        return true;

    }
}

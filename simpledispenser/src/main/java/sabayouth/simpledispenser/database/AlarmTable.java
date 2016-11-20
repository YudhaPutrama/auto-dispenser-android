package sabayouth.simpledispenser.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sabayouth.simpledispenser.SimpleDispenser;
import sabayouth.simpledispenser.model.AlarmItem;

public class AlarmTable {
    public static final String TABLE_NAME = "alarm";
    public static final String COL_ID = "id";
    public static final String COL_TIME = "time";
    public static final String COL_STATUS = "status";

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME
            + "(" + COL_ID + " integer primary key autoincrement,"
            + COL_TIME + " text not null,"
            + COL_STATUS + " text not null" + ");";

    public static void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(AlarmTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public static long addAlarm(AlarmItem alarmItem){
        SQLiteDatabase db = SimpleDispenser.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TIME, alarmItem.getTime());
        values.put(COL_STATUS, alarmItem.getStatus());

        return db.insert(TABLE_NAME, null, values);
    }

    public static void deleteAlarm(AlarmItem alarmItem){
        SQLiteDatabase db = SimpleDispenser.dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID+"=?", new String[]{String.valueOf(alarmItem.getId())});
    }

    public static AlarmItem getAlarm(long alarmId){
        SQLiteDatabase db = SimpleDispenser.dbHelper.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_ID+"="+alarmId;

        Cursor c = db.rawQuery(query,null);
        if(c!=null) c.moveToFirst();

        AlarmItem alarmItem = new AlarmItem();
        alarmItem.setId(c.getInt(c.getColumnIndex(COL_ID)));
        alarmItem.setTime(c.getString(c.getColumnIndex(COL_TIME)));
        alarmItem.setStatus(c.getString(c.getColumnIndex(COL_STATUS)));
        return alarmItem;
    }
    public static final List<AlarmItem> getAllAlarms(){
        List<AlarmItem> alarms = new ArrayList<AlarmItem>();
        String query = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = SimpleDispenser.dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                AlarmItem td = new AlarmItem();
                td.setId(c.getInt((c.getColumnIndex(COL_ID))));
                td.setTime((c.getString(c.getColumnIndex(COL_TIME))));
                td.setStatus(c.getString(c.getColumnIndex(COL_STATUS)));

                // adding todo list
                alarms.add(td);
            } while (c.moveToNext());
        }
        return alarms;
    }


}

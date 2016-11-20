package sabayouth.simpledispenser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sabayouth.simpledispenser.SimpleDispenser;
import sabayouth.simpledispenser.model.AlarmItem;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simpleDispenser.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ALARM_NAME = "alarm";
    public static final String COL_ALARM_ID = "id";
    public static final String COL_ALARM_TIME = "time";
    public static final String COL_ALARM_STATUS = "status";

    private static final String CREATE_TABLE_ALARM = "create table if not exists "
            + TABLE_ALARM_NAME
            + "(" + COL_ALARM_ID + " integer primary key autoincrement,"
            + COL_ALARM_TIME + " text not null,"
            + COL_ALARM_STATUS + " text not null" + ");";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM_NAME);
        onCreate(db);
        //AlarmTable.onUpgrade(db, oldVersion, newVersion);
    }

    public long addAlarmM(String time, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ALARM_TIME, time);
        values.put(COL_ALARM_STATUS, status);
        return db.insert(TABLE_ALARM_NAME, null, values);
    }
    public long addAlarm(AlarmItem alarmItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ALARM_TIME, alarmItem.getTime());
        values.put(COL_ALARM_STATUS, alarmItem.getStatus());


        return db.insert(TABLE_ALARM_NAME, null, values);
        ///return db.insert(TABLE_ALARM_NAME,)

    }

    public void deleteAlarm(AlarmItem alarmItem){
        SQLiteDatabase db = SimpleDispenser.dbHelper.getWritableDatabase();
        db.delete(TABLE_ALARM_NAME, COL_ALARM_ID+"=?", new String[]{String.valueOf(alarmItem.getId())});
    }

    public AlarmItem getAlarm(long alarmId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_ALARM_NAME+" WHERE "+COL_ALARM_ID+"="+alarmId;

        Cursor c = db.rawQuery(query,null);
        if(c!=null) c.moveToFirst();

        AlarmItem alarmItem = new AlarmItem();
        alarmItem.setId(c.getInt(c.getColumnIndex(COL_ALARM_ID)));
        alarmItem.setTime(c.getString(c.getColumnIndex(COL_ALARM_TIME)));
        alarmItem.setStatus(c.getString(c.getColumnIndex(COL_ALARM_STATUS)));
        return alarmItem;
    }
    public List<AlarmItem> getAllAlarms(){
        List<AlarmItem> alarms = new ArrayList<AlarmItem>();
        String query = "SELECT * FROM "+TABLE_ALARM_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                AlarmItem td = new AlarmItem();
                td.setId(c.getInt((c.getColumnIndex(COL_ALARM_ID))));
                td.setTime((c.getString(c.getColumnIndex(COL_ALARM_TIME))));
                td.setStatus(c.getString(c.getColumnIndex(COL_ALARM_STATUS)));

                // adding todo list
                alarms.add(td);
            } while (c.moveToNext());
        }
        return alarms;
    }
}

package sabayouth.alarmdispenser.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sabayouth.alarmdispenser.model.StatisticContract.Statistic;

public class DBStatisticHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dispenserStatistic.db";


    private static final String SQL_CREATE_STATISTIC = "CREATE TABLE "+Statistic.TABLE_NAME+" ("+
            Statistic._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            Statistic.COLUMN_NAME_STATISTIC_DATETIME+" TEXT,"+
            Statistic.COLUMN_NAME_STATISTIC_DATE+" TEXT,"+
            Statistic.COLUMN_NAME_STATISTIC_TIME+" TEXT,"+
            Statistic.COLUMN_NAME_STATISTIC_VOLUME+" INTEGER"+
            ");";

    private static final String SQL_DELETE_STATISTIC =
            "DROP TABLE IF EXISTS " + Statistic.TABLE_NAME;

    public DBStatisticHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STATISTIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STATISTIC);
        onCreate(db);
    }


    public long addStatistic(){
        ContentValues values;
        values = new ContentValues();
        values.put(Statistic.COLUMN_NAME_STATISTIC_DATETIME, getDateTime());
        values.put(Statistic.COLUMN_NAME_STATISTIC_DATE, getDate());
        values.put(Statistic.COLUMN_NAME_STATISTIC_TIME, getTime());
        values.put(Statistic.COLUMN_NAME_STATISTIC_VOLUME, 250);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(Statistic.TABLE_NAME, null, values);
    }


    public Integer countToday(){
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Statistic.TABLE_NAME + " WHERE " + Statistic.COLUMN_NAME_STATISTIC_DATE + " = " + getDate();
        Integer nilai = db.rawQuery(select, null).getCount();
        if(nilai!=null){
            return nilai;
        }
        return 0;
    }
    /*
     * SQLite Datetime Format : yyyy-mm-dd hh:mm:ss.xxxxxx
     * Example : '2007-01-01 10:00:00'
     *
     */

    private String getDateTime(){
        return this.FormatDateTime("yyyy-MM-dd HH:mm:ss");
    }
    private String getDate(){
        return this.FormatDateTime("yyyy-MM-dd");
    }
    private String getTime(){
        return this.FormatDateTime("HH:mm:ss");
    }
    private String FormatDateTime(String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}

package sabayouth.simpledispenser.database;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class StatisticTable {
    private static final String TABLE_NAME="statistic";
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_VOLUME = "volume";


    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_NAME
            + "(" + COL_ID + " integer primary key autoincrement,"
            + COL_DATE + " text not null,"
            + COL_VOLUME + " integer not null" + ");";

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


}

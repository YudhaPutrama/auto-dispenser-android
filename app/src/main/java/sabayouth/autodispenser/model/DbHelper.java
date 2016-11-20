package sabayouth.autodispenser.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    private static final String DB_NAME = "autoDispenser.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Alarm.getSql());
        db.execSQL(Aktivitas.getSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Alarm.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Aktivitas.TABLE_NAME);

        onCreate(db);
    }

    /*
     * Function for Alarm Database
     * Created Date : 24 Septemeber 2015
     * Created At : Rumah Kurniawan
     * Author : Kurniawan Yudha Putrama
     */
    public boolean createAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Alarm.COL_CREATEDTIME, getTime());
        values.put(Alarm.COL_MODIFIEDTIME, getTime());
        //values.put(Alarm.COL_TIME, alar)
        return false;


        /*
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long todo_id = db.insert(TABLE_TODO, null, values);

        // insert tag_ids
        for (long tag_id : tag_ids) {
            createTodoTag(todo_id, tag_id);
        }

        return todo_id;
        */
    }

    /*
     *
     */
    public List<Alarm> listAlarms(){
        return null;
    }

    public boolean updateAlarm(Alarm alarm){
        return false;
    }

    public boolean deleteAlarm(Alarm alarm){
        return false;
    }

    public long countAlarms(){
        return 0;
    }

    /*
     * Function for Aktivitas Database
     * Created Date : 24 September 2015
     * Created Time : 17.43
     * Created At : Rumah Kurniawan
     * Author : Kurniawan Yudha Putrama
     */

    public boolean createAktivitas(Aktivitas aktivitas){
        return false;
    }
    public List<Aktivitas> listAktivitas(long fromDate, long toDate){
        return null;
    }
    public boolean updateAktivitas(Aktivitas aktivitas){
        return false;
    }
    public void deleteAktivitas(Aktivitas aktivitas){}
    public void truncAktivitas(){}
    public Integer countAktivitas(long fromDate, long toDate){
        return 0;
    }

    private String getTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        Date date = new Date();
        return timeFormat.format(date);
    }
}

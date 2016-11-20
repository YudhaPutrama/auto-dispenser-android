package sabayouth.simpledispenser;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import sabayouth.simpledispenser.database.DatabaseHelper;

/**
 * Created by Yudha Putrama on 10/1/2015.
 * App Name     : InfoFBL
 * Version      : 0.1a
 * Description  : Schedule and Now Playing Application
 */
public class SimpleDispenser extends Application {

    public static DatabaseHelper dbHelper;
    public static SQLiteDatabase db;
    public static SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        //PreferenceManager
        //sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //dbHelper = new DatabaseHelper(getApplicationContext());
        //db = dbHelper.getWritableDatabase();

    }
}

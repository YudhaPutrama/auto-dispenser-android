package sabayouth.autodispenser;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import sabayouth.autodispenser.model.DbHelper;

/**
 * Created by Yudha Putrama on 9/22/2015.
 * App Name     : AutoDispenser
 */
public class AutoDispenser extends Application {

    public static DbHelper dbHelper;
    public static SQLiteDatabase db;
    public static SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
    }
}

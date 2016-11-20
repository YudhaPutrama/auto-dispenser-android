package sabayouth.autodispenser.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import sabayouth.autodispenser.Util;
/**
 * Created by Yudha Putrama on 9/22/2015.
 * App Name     : AutoDispenser
 * Version      : 0.1a
  */
abstract class AbstractModel {
    static final String COL_ID = "_id";
    static final String COL_CREATEDTIME = "createdtime";

    static String getSql() {
        return Util.concat(COL_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ",
                COL_CREATEDTIME," DATETIME, ");
    }

    void update(ContentValues cv) {
        cv.put(COL_ID, id);
    }

    void load(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(COL_ID));
    }

    //--------------------------------------------------------------------------

    protected long id;

    protected void reset() {
        id = 0;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    abstract long save(SQLiteDatabase db);
    abstract boolean update(SQLiteDatabase db);

    public long persist(SQLiteDatabase db) {
        if (id > 0)
            return update(db) ? id : 0;
        else
            return save(db);
    }

}

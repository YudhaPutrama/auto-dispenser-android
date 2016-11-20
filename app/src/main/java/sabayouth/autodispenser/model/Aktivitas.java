package sabayouth.autodispenser.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import sabayouth.autodispenser.Util;

/**
 * Created by Yudha Putrama on 9/23/2015.
 */
public class Aktivitas {

    public static final String TABLE_NAME = "alarm";
    public static final String COL_ID = "id";
    public static final String COL_CREATEDTIME = "created_time";
    public static final String COL_VOLUME = "volume";
    public static final String COL_TIMER = "timer";

    public static final Boolean AUTO = true;
    public static final Boolean MANUAL = false;

    static String getSql(){
        return Util.concat("CREATE TABLE IF NOT EXISTS ",TABLE_NAME," (",
                COL_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ",
                COL_CREATEDTIME, " DATETIME, ",
                COL_VOLUME," INTEGER, ",
                COL_TIMER, " TEXT ",
                ");");
    }

    private Integer id;
    private Date createdTime;
    private Integer volume;
    private Boolean timer;

    public Aktivitas(){}

    public Aktivitas(Date createdTime, Integer Volume, Boolean timer){
        this.createdTime = createdTime;
        this.volume = Volume;
        this.timer = timer;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public void setVolume(Integer volume){
        this.volume = volume;
    }

    public void setFromTimer(Boolean timer){
        if (timer.equals(AUTO)){
            this.timer = true;
        } else if (timer.equals(MANUAL)){
            this.timer = false;
        }
    }

    public Integer getId(){
        return id;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public Integer getVolume(){
        return volume;
    }

    public Boolean getFromTimer(){
        return timer;
    }
}

package sabayouth.autodispenser.model;

import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiConfiguration;
import android.text.format.Time;

import java.util.Date;

import sabayouth.autodispenser.Util;

/**
 * Created by Yudha Putrama on 9/22/2015.
 */
public class Alarm {

    public static final String TABLE_NAME = "alarm";
    public static final String COL_ID = "id";
    public static final String COL_CREATEDTIME = "created_time";
    public static final String COL_MODIFIEDTIME = "modified_time";
    public static final String COL_TIME = "time";
    public static final String COL_SOUND = "sound";
    public static final String COL_VIBRATE = "vibrate";
    public static final String COL_STATUS = "status";

    public static final String ACTIVE = "A";
    public static final String CANCELED = "C";

    static String getSql(){
        return Util.concat("CREATE TABLE IF NOT EXISTS ", TABLE_NAME, " (",
                COL_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ",
                COL_CREATEDTIME, " DATETIME, ",
                COL_MODIFIEDTIME, " DATETIME, ",
                COL_TIME, " TEXT",
                ");");
    }

    private Integer id;
    private Date createdTime;   //Format DateTime
    private Date modifiedTime;  //Format DateTime
    private String timer;       //Format HH:mm
    private String hourTimer;
    private String minuteTimer;

    public Alarm(){
        this.timer = "00:00";
    }
    public Alarm(String timer){
        this.timer = timer;
        this.createdTime = new Date();
        this.modifiedTime = new Date();
    }
    public Alarm(Date createdTime, Date modifiedTime, String timer){
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.timer = timer;
    }
    public Alarm(Integer hour, Integer minute){

    }

    public void setId(Integer id){this.id = id;}
    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
    public void setModifiedTime(Date modifiedTime){
        this.modifiedTime = modifiedTime;
    }
    public void setTimer(String timer){
        this.timer = timer;
        this.hourTimer = timer.substring(0,1);
        this.minuteTimer = timer.substring(3,4);
    }
    public void setHourTimer(Integer hour){this.hourTimer = hour.toString();}
    public void setMinuteTimer(Integer minute){this.minuteTimer = minute.toString();}

    public Integer getId(){
        return id;
    }
    public Date getCreatedTime(){
        return createdTime;
    }
    public Date getModifiedTime(){
        return modifiedTime;
    }
    public String getTimer(){return timer;}
    public Integer getHourTimer(){return Integer.getInteger(hourTimer);}
    public Integer getMinuteTimer(){return Integer.getInteger(minuteTimer);}

}

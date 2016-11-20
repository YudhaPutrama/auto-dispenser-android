package sabayouth.alarmdispenser.model;


import android.provider.BaseColumns;

public class StatisticContract {
    public StatisticContract() {}

    public static abstract class Statistic implements BaseColumns {
        public static final String TABLE_NAME = "statistic";
        public static final String COLUMN_NAME_STATISTIC_DATETIME = "datetime";
        public static final String COLUMN_NAME_STATISTIC_DATE = "date";
        public static final String COLUMN_NAME_STATISTIC_TIME = "time";
        public static final String COLUMN_NAME_STATISTIC_VOLUME = "volume";


        //public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
        //public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
        //public static final String COLUMN_NAME_ALARM_REPEAT_DAYS = "days";
        //public static final String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "weekly";
        //public static final String COLUMN_NAME_ALARM_TONE = "tone";
        //public static final String COLUMN_NAME_ALARM_ENABLED = "enabled";
    }
}

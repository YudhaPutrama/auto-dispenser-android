package sabayouth.simpledispenser.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

import sabayouth.simpledispenser.R;
import sabayouth.simpledispenser.SimpleDispenser;
import sabayouth.simpledispenser.database.AlarmTable;
import sabayouth.simpledispenser.database.DatabaseHelper;
import sabayouth.simpledispenser.model.AlarmItem;

public class HomeFragment extends Fragment {

    private ImageButton actionButton;
    private List<AlarmItem> aList;
	public HomeFragment(){}
    private static DatabaseHelper dbHelper;
    private AlarmItem item;
    private AlarmAdapter mAlarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        item = new AlarmItem();
        item.setStatus(AlarmItem.ACTIVE);
        item.setTime("00:00");
        //long ala1 = db.addAlarm(item);
        //long ala2 = db.addAlarm(new AlarmItem("02:00",AlarmItem.ACTIVE));
        //long ala3 = db.addAlarm(new AlarmItem("03:00",AlarmItem.ACTIVE));
        Log.d("AlarmItem:Status",item.getStatus());
        Log.d("AlarmItem:Time",item.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        actionButton = (ImageButton)getActivity().findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddAlarmFragment().show(getFragmentManager(),"Add New Alarm");
            }
        });

        updateList();
    }

    public static class AddAlarmFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //AlarmItem item = new AlarmItem();
            AlarmItem item = new AlarmItem();
            item.setStatus(AlarmItem.ACTIVE);
            item.setTime("00:00");
            Log.d("AlarmItem:Status",item.getStatus());
            Log.d("AlarmItem:Time",item.getTime());
            dbHelper.addAlarm(item);
        }
    }

    private void updateList(){
        mAlarm = new AlarmAdapter();
        aList = dbHelper.getAllAlarms();
        ListView lv = (ListView) getActivity().findViewById(R.id.listView);
        lv.setAdapter(new AlarmAdapter());
        mAlarm.notifyDataSetChanged();
    }

    private void ToggleSwitch(int pos){

    }

    private class AlarmAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return aList.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_alarm,null);
            AlarmItem alarm = getItem(position);
            TextView tv = (TextView) convertView.findViewById(R.id.timeList);
            Switch sw = (Switch) convertView.findViewById(R.id.alarmSwitch);
            tv.setText(alarm.getTime());
            if (alarm.getStatus()==alarm.ACTIVE){
                sw.setChecked(true);
            } else {
                sw.setChecked(false);
            }
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ToggleSwitch(position);
                }
            });
            return convertView;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public AlarmItem getItem(int position) {
            return aList.get(position);
        }
    }
}

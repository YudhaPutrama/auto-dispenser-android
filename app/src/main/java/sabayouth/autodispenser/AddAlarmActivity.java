package sabayouth.autodispenser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import sabayouth.autodispenser.model.Alarm;
import sabayouth.autodispenser.model.DbHelper;

/**
 * Created by Yudha Putrama on 9/23/2015.
 */
public class AddAlarmActivity extends Activity {
    //TimePicker timePicker;
    //DbHelper db = AutoDispenser.dbHelper;
    //Alarm alarm = new Alarm();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Add Alarm");

        //timePicker = (TimePicker) findViewById(R.id.timePicker);
        //timePicker.setIs24HourView(true);

        findViewById(R.id.new_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAlarmButton();
            }
        });
    }

    private void AddAlarmButton(){
        DialogFragment newFrag = new TimePickerFragment();
        newFrag.show(getFragmentManager(),"timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    }

}

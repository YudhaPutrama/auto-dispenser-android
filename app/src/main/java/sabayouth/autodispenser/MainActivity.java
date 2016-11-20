package sabayouth.autodispenser;

import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.github.mikephil.charting.charts.BarChart;

import sabayouth.autodispenser.model.Alarm;
import sabayouth.autodispenser.model.DbHelper;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    TabsPagerAdapter mTabsPagerAdapter;

    private static final DbHelper db = AutoDispenser.dbHelper;

    ViewPager mViewPager;
    ActionBar mActionBar;
    String[] tabs = {"Home", "Alarm", "Activity"};

    private BarChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mActionBar = getActionBar();
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mTabsPagerAdapter);
        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name: tabs){
            mActionBar.addTab(mActionBar.newTab().setText(tab_name).setTabListener(this));
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        } else if (id == R.id.action_new_alarm){
            DialogFragment newFrag = new AddAlarmFragment();
            newFrag.show(getFragmentManager(),"timePicker");
            //startActivity(new Intent(MainActivity.this, AddAlarmActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
            db.createAlarm(new Alarm(hourOfDay, minute));
        }
    }

    public class TabsPagerAdapter extends FragmentPagerAdapter{
        public TabsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0: return new MainFragment();
                case 1: return new AlarmFragment();
                case 2: return new ChartFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

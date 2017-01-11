package sabayouth.alarmdispenser;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sabayouth.alarmdispenser.connection.DeviceWifiService;
import sabayouth.alarmdispenser.helper.DBStatisticHelper;

public class DeviceActivity extends ActionBarActivity implements View.OnClickListener{
    Button connectButton;
    Button showAlarm;
    Button showStatus;
    Button actionPour;
    Button actionStop;
    Button addStatistic;

    TextView sensor1;
    TextView sensor2;
    TextView status;

    String url = "http://192.168.4.1/";

    DBStatisticHelper dbS = new DBStatisticHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDevice);
        setSupportActionBar(toolbar);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Auto Dispenser Demonstration");

        connectButton = (Button) findViewById(R.id.connectButton);
        showAlarm = (Button) findViewById(R.id.showAlarm);
        showStatus= (Button) findViewById(R.id.getStatusButton);
        actionPour= (Button) findViewById(R.id.actionPour);
        actionStop= (Button) findViewById(R.id.actionStop);
        addStatistic = (Button) findViewById(R.id.addStatistic);

        sensor1 = (TextView) findViewById(R.id.TextSensor1);
        sensor2 = (TextView) findViewById(R.id.TextSensor2);
        status = (TextView) findViewById(R.id.TextStatus);

        connectButton.setOnClickListener(this);
        showAlarm.setOnClickListener(this);
        showStatus.setOnClickListener(this);
        actionPour.setOnClickListener(this);
        actionStop.setOnClickListener(this);
    }

    private void tampilkanPesan(String text, int duration){
        Toast.makeText(this, text, duration).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showAlarm:{
                Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                alarmIntent.putExtra(AlarmManagerHelper.TIME_HOUR, 10);
                alarmIntent.putExtra(AlarmManagerHelper.TIME_MINUTE, 10);
                alarmIntent.putExtra(AlarmManagerHelper.TONE,"");
                getApplication().startActivity(alarmIntent);
                break;
            }
            case R.id.getStatusButton:{
                url="http://192.168.4.1/";
                new GetStatus().execute();
                break;
            }
            case R.id.actionPour:{
                url="http://192.168.4.1/tuang";
                new GetStatus().execute();
                break;
            }
            case R.id.actionStop:{
                url="http://192.168.4.1/stop";
                new GetStatus().execute();
                break;
            }
            case R.id.addStatistic:{
                dbS.addStatistic();
                break;
            }
        }
    }

    private void connectWifi(){
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID="\"AutoDispenser\"";
        conf.preSharedKey="\"12345678\"";

        WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> wList = wifi.getConfiguredNetworks();
        for (WifiConfiguration item : wList){
            if (item.SSID != null && item.SSID.equals("\"AutoDispenser\"")){
                wifi.disconnect();
                wifi.enableNetwork(item.networkId,true);
                wifi.reconnect();
                break;
                ///if (wifi.get)
            }
        }
    }

    private class GetStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tampilkanPesan("Memulai mengirim perintah",Toast.LENGTH_SHORT);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            DeviceWifiService deviceWifiService = new DeviceWifiService(getApplicationContext());

            // Making a request to url and getting response
            String jsonStr = deviceWifiService.makeServiceCall(url, DeviceWifiService.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null && !jsonStr.isEmpty()) {
                /*
                try {

                    /*
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    //contacts = jsonObj.getJSONArray(TAG_CONTACTS);
                    JSONObject sensor = jsonObj.getJSONObject("sensor");
                    tampilkanPesan("Kondisi saat ini : "+sensor.getString("status"),Toast.LENGTH_SHORT);

                    sensor1.setText(sensor.getString("irValue"));
                    sensor2.setText(sensor.getString("usValue"));
                    status.setText(sensor.getString("status"));
                    //Boolean PumpOn = status.getString("pumpIs").equals("ON");

                    Log.d("JSON",jsonStr);
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */
                String[] separate = jsonStr.split(",");
                Log.d("CODE",separate[0]);
                Log.d("STATUS",separate[1]);
                Log.d("SENSOR1",separate[2]);
                Log.d("SENSOR2",separate[3]);
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                //tampilkanPesan("Tidak tersambung ke AutoDispenser", Toast.LENGTH_SHORT);
                //Toast.makeText(MainActivity.this, "Tidak tersambung ke Auto Dispenser", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            //if (pDialog.isShowing())
//                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            /*
             ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                    TAG_PHONE_MOBILE }, new int[] { R.id.name,
                    R.id.email, R.id.mobile });

            setListAdapter(adapter);
            */
        }

    }

}

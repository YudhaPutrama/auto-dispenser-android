package sabayouth.autodispenser;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Yudha Putrama on 9/23/2015.
 * Aktivitas untuk menyambungkan hardware Android dengan Dispenser
 */
public class DeviceActivity extends Activity{

    String networkSSID;
    String networkPass;
    String networkType;

    private static final String OPEN = "OPEN";
    private static final String WEP = "WEP";
    private static final String WPA = "WPA";



    WifiConfiguration conf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkSSID = getString(R.string.networkSSID);
        networkPass = getString(R.string.networkPass);
        networkType = getString(R.string.networkType);
        connectWifi();
    }

    private void connectWifi(){
        conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";
        if (networkType.equals(OPEN)) {
            /*
             * Open Network
             */
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (networkType.equals(WEP)) {
            /*
             * WEP Network
             */
            conf.wepKeys[0] = "\"" + networkPass + "\"";
            conf.wepTxKeyIndex = 0;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        } else if(networkType.equals(WPA)) {
            /*
             * WPA Network
             */
            conf.preSharedKey = "\"" + networkPass + "\"";
        }

        WifiManager wifiManager = (WifiManager) this.getSystemService(this.WIFI_SERVICE);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list){
            if(i.SSID != null && i.SSID.equals("\""+networkSSID+"\"")){
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
            }
        }
    }
}

package sabayouth.autodispenser;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;

/**
 * Created by Yudha Putrama on 9/26/2015.
 * IntentService untuk menyambungakan software dengan hardware AutoDispenser
 * Action List:
 * - CONNECT : Untuk menyambungkan smartphone Android dengan Wifi AutoDispenser
 * - GET : Untuk
 */
public class DeviceService extends IntentService {
    private static final String TAG = "DeviceService";

    public static final String CONNECTION = "CONNECT";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DISCONNECT = "DISCONNECT";

    IntentFilter intentFilter;

    public DeviceService(){
        super(TAG);
        intentFilter.addAction(CONNECTION);
        intentFilter.addAction(GET);
        intentFilter.addAction(POST);
        intentFilter.addAction(DISCONNECT);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}

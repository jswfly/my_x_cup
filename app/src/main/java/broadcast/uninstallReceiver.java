package broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import util.DataCleanManager;


public class uninstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Log.e("Test", "---------------" + "PACKAGE_REMOVED" + packageName);
            File appDir = new File(Environment.getExternalStorageDirectory(), "MyCup");
            if(appDir.exists()){
                DataCleanManager.cleanApplicationData(context, appDir.toString());
            }
        }
        Log.d("11111111111111111111111","222222222222222222222222");
    }
}

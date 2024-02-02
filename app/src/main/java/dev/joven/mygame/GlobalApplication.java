package dev.joven.mygame;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;


public class GlobalApplication extends Application {
    public static final boolean DEBUG = true;
    public static GlobalApplication context;

    public void onCreate() {
        super.onCreate();
        context = this;
        //FirebaseApp.initializeApp(context, new FirebaseOptions.Builder().setGcmSenderId(getString(R.string.project_number)).setProjectId(getString(R.string.project_id)).setStorageBucket(getString(R.string.storage_bucket)).setApplicationId(getString(R.string.mobilesdk_app_id)).setApiKey(getString(R.string.current_key)).build());
        CommonUtil.log("FirebaseApp.initializeApp finish");
    }

    public boolean isMainProcess() {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
            if (next.pid == myPid) {
                return getApplicationInfo().packageName.equals(next.processName);
            }
        }
        return false;
    }
}

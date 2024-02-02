package dev.joven.mygame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;

import dev.joven.mygame.Constant;
import dev.joven.mygame.CommonUtil;
import dev.joven.mygame.FileUtils;
import dev.joven.mygame.SettingHelper;
import dev.joven.mygame.ZipUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dev.joven.mygame.BaseActivity;

public class LoadingSplash extends BaseActivity {

    private static final int SD_CARD_REQUEST_CODE = 1123;
    private Map<Integer, Runnable> allowablePermissionRunnables = new HashMap();
    private Map<Integer, Runnable> disallowablePermissionRunnables = new HashMap();
    private boolean isReject = false;
    /* access modifiers changed from: private */
    public LoadingSplash mContext;
    private String mRootDir;

    public void finishActivity(int i) {
        super.finishActivity(i);
    }

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        setContentView(R.layout.activity_welcome);
        this.isReject = false;

        FacebookSdk.fullyInitialize();
        AppEventsLogger.activateApp(this);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> new MyAsyncTask().execute(new Void[0]), 2000);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    private void initStorage() {
        if (FileUtils.storageAvailable()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    new MyAsyncTask().execute(new Void[0]);
                }
            }, 2000);
        } else if (this.isReject) {
            showNormalDialog();
        } else {
            showConfirmDialog();
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        MyAsyncTask() {
        }

        public void onPreExecture() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            return null;
        }

        public void onCancelled() {
            super.onCancelled();
        }


        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            LoadingSplash.this.enterMainActivity();
        }
    }

    /* access modifiers changed from: private */
    public void enterMainActivity() {
        startActivity(new Intent(this.mContext, AdjustSDK.class));
        overridePendingTransition(0, 0);
        finish();
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setCancelable(false);
        builder.setMessage("游戏资源需要更新下载,需要开启访问读写权限哦!~");
        builder.setPositiveButton("确定", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setNegativeButton("关闭", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            LoadingSplash welcomeActivity = LoadingSplash.this;
            welcomeActivity.back(welcomeActivity.mContext);
        });
        builder.show();
    }

    private void showNormalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setCancelable(false);
        builder.setMessage("游戏资源需要更新下载,请开启访问读写权限哦!~");
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            CommonUtil.toSelfSetting(LoadingSplash.this.mContext);
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("关闭", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            LoadingSplash welcomeActivity = LoadingSplash.this;
            welcomeActivity.back(welcomeActivity.mContext);
        });
        builder.show();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0) {
            Runnable runnable = this.allowablePermissionRunnables.get(Integer.valueOf(i));
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        Runnable runnable2 = this.disallowablePermissionRunnables.get(Integer.valueOf(i));
        if (runnable2 != null) {
            runnable2.run();
        }
    }
}
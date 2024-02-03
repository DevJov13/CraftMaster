package dev.joven.mygame;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.VideoView;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LoadingSplash extends BaseActivity {
    private Map<Integer, Runnable> allowablePermissionRunnables = new HashMap();
    private Map<Integer, Runnable> disallowablePermissionRunnables = new HashMap();
    private boolean isReject = false;
    public LoadingSplash mContext;

    public void finishActivity(int i) {
        super.finishActivity(i);
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        setContentView(R.layout.activity_loading_splash);
        this.isReject = false;
        FacebookSdk.fullyInitialize();
        AppEventsLogger.activateApp(this);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + File.separator + R.raw.loading;

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            videoView.start();

            new Handler().postDelayed(this::enterMainActivity, 8000);
        });
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e("VideoError", "Error during video playback. What: " + what + ", Extra: " + extra);
            return true;
        });


    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
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

package com.zic.bigonecheater.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.zic.bigonecheater.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstallActivity extends Activity {

    @SuppressLint("SdCardPath")
    private static final String boAppsPath = "/sdcard/Zickie/BigOneApps";

    List<String> apks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finish();
        moveTaskToBack(true);

        Toast.makeText(this, getString(R.string.toast_installing), Toast.LENGTH_SHORT).show();

        new InstallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void installApk(String apkFilePath) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.fromFile(new File(apkFilePath)), "application/vnd.android.package-archive");
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(installIntent);
    }

    private class InstallTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected final Void doInBackground(Void... params) {
            File bigOneAppsDir = new File(boAppsPath);
            apks = new ArrayList<>();
            for (File apkFile : bigOneAppsDir.listFiles()) {
                String apkFilePath = apkFile.getAbsolutePath();
                //String fileName = apkFile.getName();

                apks.add(apkFilePath);
            }

            for (int i = 0; i < apks.size(); i++) {
                String apkFilePath = apks.get(i);
                 // AppUtils.installAsRoot(apkFilePath);

                installApk(apkFilePath);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(InstallActivity.this, getString(R.string.toast_installed), Toast.LENGTH_SHORT).show();
        }
    }

}

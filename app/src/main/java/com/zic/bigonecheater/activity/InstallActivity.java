package com.zic.bigonecheater.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.utils.AppUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstallActivity extends Activity {

    @SuppressLint("SdCardPath")
    private static final String boAppsPath = "/sdcard/Zickie/BigOneApps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File bigOneAppsDir = new File(boAppsPath);
        List<String> apks = new ArrayList<>();
        for (File apkFile : bigOneAppsDir.listFiles()) {
            String apkFilePath = apkFile.getAbsolutePath();
            //String fileName = apkFile.getName();

            apks.add(apkFilePath);
        }

        Toast.makeText(this, "Installing...", Toast.LENGTH_SHORT).show();

        new InstallActivity.InstallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apks);
    }

    private class InstallTask extends AsyncTask<List<String>, Void, String> {

        @SafeVarargs
        @Override
        protected final String doInBackground(List<String>... params) {
            List<String> apks = params[0];
            for (int i = 0; i < apks.size(); i++) {
                String apkFilePath = apks.get(i);
                AppUtils.installAsRoot(apkFilePath);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(InstallActivity.this, getString(R.string.toast_installed), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}

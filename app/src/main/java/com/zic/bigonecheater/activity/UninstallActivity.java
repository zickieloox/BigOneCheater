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

public class UninstallActivity extends Activity {

    @SuppressLint("SdCardPath")
    private static final String boAppsPath = "/sdcard/Zickie/BigOneApps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moveTaskToBack(true);
        finish();

        Toast.makeText(this, "Uninstalling...", Toast.LENGTH_SHORT).show();

        new UninstallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class UninstallTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected final Void doInBackground(Void... params) {
            File bigOneAppsDir = new File(boAppsPath);
            List<String> pkgNames = new ArrayList<>();
            for (File apkFile : bigOneAppsDir.listFiles()) {
                String apkFilePath = apkFile.getAbsolutePath();
                String pkgName = AppUtils.getPkgNameFromApk(UninstallActivity.this, apkFilePath);

                pkgNames.add(pkgName);
            }

            for (int i = 0; i < pkgNames.size(); i++) {
                AppUtils.uninstallAsRoot(pkgNames.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(UninstallActivity.this, getString(R.string.toast_uninstalled), Toast.LENGTH_SHORT).show();

            finish();
        }
    }

}
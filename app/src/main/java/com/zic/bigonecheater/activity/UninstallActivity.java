package com.zic.bigonecheater.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.utils.AppUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UninstallActivity extends AppCompatActivity {

    @SuppressLint("SdCardPath")
    private static final String boAppsPath = "/sdcard/Zickie/BigOneApps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File bigOneAppsDir = new File(boAppsPath);
        List<String> pkgNames = new ArrayList<>();
        for (File apkFile : bigOneAppsDir.listFiles()) {
            String apkFilePath = apkFile.getAbsolutePath();
            String pkgName = AppUtils.getPkgNameFromApk(this, apkFilePath);

            pkgNames.add(pkgName);
        }
        new UninstallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pkgNames);
    }

    private class UninstallTask extends AsyncTask<List<String>, Void, String> {

        @SafeVarargs
        @Override
        protected final String doInBackground(List<String>... params) {
            List<String> pkgNames = params[0];
            for (int i = 0; i < pkgNames.size(); i++) {
                AppUtils.uninstallAsRoot(pkgNames.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(UninstallActivity.this, getString(R.string.toast_uninstalled), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
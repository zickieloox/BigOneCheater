package com.zic.bigonecheater.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.utils.AppUtils;
import com.zic.bigonecheater.utils.FileUtils;
import com.zic.bigonecheater.utils.PrefsUtils;
import com.zic.bigonecheater.utils.RandomUtils;
import com.zic.bigonecheater.utils.ShellUtils;

public class AutoPreviousActivity extends AppCompatActivity {

    @SuppressLint("SdCardPath")
    private static final String androidDataPath = "/data/data/";
    private static final String bigOnePkgName = "bigone.danh.bai.online";
    private static final String deviceIdChangerPkgName = "com.phoneinfo.changerpro";
    private static final String myPackageName = "com.zic.bigonecheater";
    private static final String deviceIdChangerPrefsPath = androidDataPath + deviceIdChangerPkgName + "/shared_prefs/xpref_config.xml";
    private static final String myDeviceIdChangerPrefsPath = androidDataPath + myPackageName + "/shared_prefs/xpref_config.xml";

    @SuppressLint("SdCardPath")
    private static final String accountListPath = "/sdcard/Zickie/accounts.txt";

    @SuppressLint("SdCardPath")
    private static final String zickiePath = "/sdcard/Zickie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moveTaskToBack(true);
        finish();

        changeDeviceInfo();

    }

    public void changeDeviceInfo() {
        String newImei = RandomUtils.newValidImei();

        PrefsUtils.putImei(getApplicationContext(), newImei);

        new ChangeDeviceInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void prevFbAccount() {
        int curAccount = PrefsUtils.getCurrentAccount(this);
        curAccount--;
        PrefsUtils.putCurrentAccount(this, curAccount);

        Toast.makeText(this, String.valueOf(curAccount), Toast.LENGTH_SHORT).show();

        String account = FileUtils.getLine(accountListPath, curAccount);
        String[] accountArr;
        String facebookId;
        String password;

        if (account != null) {
            if (account.contains("|")) {
                accountArr = account.split("[|]");
                facebookId = accountArr[0];
                password = accountArr[1];
            } else {
                facebookId = account;
                password = "zxzxzx";
            }

        } else {
            facebookId = "N/A";
            password = "N/A";
        }

        ((ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("FacebookID", facebookId));

        String textData = facebookId + "|" + password;
        FileUtils.writeToTextFile(zickiePath, "cur_account", textData);
    }

    public void uninstallApps() {
        Intent uninstallIntent = new Intent(getApplicationContext(), UninstallActivity.class);
        startActivity(uninstallIntent);
    }

    private class ChangeDeviceInfoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Copy back the new Device Id Changer pref to apply changes
            ShellUtils.copyFileAsRoot(myDeviceIdChangerPrefsPath, deviceIdChangerPrefsPath);

            // Set read - write permission for new Device Id Changer prefs
            ShellUtils.setPerm(deviceIdChangerPrefsPath);

            AppUtils.killAsRoot(bigOnePkgName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_new_device), Toast.LENGTH_SHORT).show();
            //new NewBigOneTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

            if (!AppUtils.launch(getApplicationContext(), bigOnePkgName)) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_err_launch), Toast.LENGTH_SHORT).show();
            }

            prevFbAccount();
            uninstallApps();
        }
    }

    private class NewBigOneTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            AppUtils.killAsRoot(bigOnePkgName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!AppUtils.launch(getApplicationContext(), bigOnePkgName)) {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_err_launch), Toast.LENGTH_SHORT).show();
            }
        }
    }

}

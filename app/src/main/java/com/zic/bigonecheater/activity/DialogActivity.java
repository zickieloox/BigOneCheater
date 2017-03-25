package com.zic.bigonecheater.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.fragment.DeviceIdFragment;
import com.zic.bigonecheater.utils.AppUtils;
import com.zic.bigonecheater.utils.FileUtils;
import com.zic.bigonecheater.utils.PrefsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String bigOnePkgName = "bigone.danh.bai.online";

    @SuppressLint("SdCardPath")
    private static final String boAppsPath = "/sdcard/Zickie/BigOneApps";

    @SuppressLint("SdCardPath")
    private static final String accountListPath = "/sdcard/Zickie/accounts.txt";

    private Button btnNextFid;
    private Button btnPrevFid;
    private TextView tvFid;
    private TextView tvPassword;
    private EditText edtCurAccount;

    private int curAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Button btnNewBigOne = (Button) findViewById(R.id.btnNewBigOne);
        Button btnInstall = (Button) findViewById(R.id.btnInstall);
        Button btnUninstall = (Button) findViewById(R.id.btnUninstall);
        btnPrevFid = (Button) findViewById(R.id.btnPrevFid);
        btnNextFid = (Button) findViewById(R.id.btnNextFid);
        edtCurAccount = (EditText) findViewById(R.id.edtCurAccount);
        tvFid = (TextView) findViewById(R.id.tvFid);
        tvPassword = (TextView) findViewById(R.id.tvPassword);

        showCurAccount();
        showAccountInfo();

        DeviceIdFragment deviceIdFragment = new DeviceIdFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.deviceIdContainer, deviceIdFragment).commit();

        btnNewBigOne.setOnClickListener(this);
        btnPrevFid.setOnClickListener(this);
        btnNextFid.setOnClickListener(this);
        btnInstall.setOnClickListener(this);
        btnUninstall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnNewBigOne:
                newBigOne();
                break;
            case R.id.btnInstall:
                installApps();
                break;
            case R.id.btnUninstall:
                uninstallApps();
                break;
            case R.id.btnPrevFid:
                prevFid();
                break;
            case R.id.btnNextFid:
                nextFid();
                break;
        }
    }

    public void newBigOne() {
        AppUtils.killAsRoot(bigOnePkgName);
        if (!AppUtils.launch(this, bigOnePkgName)) {
            Toast.makeText(this, getString(R.string.toast_err_launch), Toast.LENGTH_SHORT).show();
        }
    }

    public void installApps() {
        File bigOneAppsDir = new File(boAppsPath);
        List<String> apks = new ArrayList<>();
        for (File apkFile : bigOneAppsDir.listFiles()) {
            String apkFilePath = apkFile.getAbsolutePath();
            //String fileName = apkFile.getName();

            apks.add(apkFilePath);
        }
        new InstallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, apks);
    }

    public void uninstallApps() {
        File bigOneAppsDir = new File(boAppsPath);
        List<String> pkgNames = new ArrayList<>();
        for (File apkFile : bigOneAppsDir.listFiles()) {
            String apkFilePath = apkFile.getAbsolutePath();
            String pkgName = AppUtils.getPkgNameFromApk(this, apkFilePath);

            pkgNames.add(pkgName);
        }
        new UninstallTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pkgNames);
    }

    public void prevFid() {
        if (curAccount <= 0) {
            btnPrevFid.setEnabled(false);
            return;
        }
        btnNextFid.setEnabled(true);
        PrefsUtils.putCurrentAccount(this, curAccount - 1);
        showCurAccount();
        showAccountInfo();
    }

    public void nextFid() {
        ArrayList<String> accountList;
        accountList = FileUtils.getLineList(accountListPath);
        if (curAccount >= (accountList.size() - 1)) {
            btnNextFid.setEnabled(false);
            return;
        }
        btnPrevFid.setEnabled(true);
        PrefsUtils.putCurrentAccount(this, curAccount + 1);
        showCurAccount();
        showAccountInfo();
    }

    public void showCurAccount() {
        curAccount = PrefsUtils.getCurrentAccount(this);
        edtCurAccount.setText(String.valueOf(curAccount));
    }

    public void showAccountInfo() {
        String account = FileUtils.getLine(accountListPath, curAccount);
        String[] accountArr;
        String fid;
        String password;

        if (account != null) {
            if (account.contains("|")) {
                accountArr = account.split("[|]");
                fid = accountArr[0];
                password = accountArr[1];
            } else {
                fid = account;
                password = "default";
            }

        } else {
            Toast.makeText(this, "Current FB Account: " + curAccount + " Not Found!", Toast.LENGTH_SHORT).show();
            tvFid.setText("N/A");
            tvPassword.setText("N/A");
            return;
        }

        ((ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("FacebookID", fid));

        tvFid.setText(getString(R.string.tv_fb_id) + fid);
        tvPassword.setText(getString(R.string.tv_fb_pass) + password);
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
            Toast.makeText(DialogActivity.this, getString(R.string.toast_installed), Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(DialogActivity.this, getString(R.string.toast_uninstalled), Toast.LENGTH_SHORT).show();
        }
    }
}

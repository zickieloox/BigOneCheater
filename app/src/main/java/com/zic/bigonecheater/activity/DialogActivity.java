package com.zic.bigonecheater.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String bigOnePkgName = "bigone.danh.bai.online";

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
        new NewBigOneTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void installApps() {
        Intent installIntent = new Intent(DialogActivity.this, InstallActivity.class);
        startActivity(installIntent);
    }

    public void uninstallApps() {
        Intent uninstallIntent = new Intent(DialogActivity.this, UninstallActivity.class);
        startActivity(uninstallIntent);
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

        String text = getString(R.string.tv_fb_id) + fid;
        tvFid.setText(text);
        text = getString(R.string.tv_fb_pass) + password;
        tvPassword.setText(text);
    }

    private class NewBigOneTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            AppUtils.killAsRoot(bigOnePkgName);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (!AppUtils.launch(DialogActivity.this, bigOnePkgName)) {
                Toast.makeText(DialogActivity.this, getString(R.string.toast_err_launch), Toast.LENGTH_SHORT).show();
            }
        }
    }
}



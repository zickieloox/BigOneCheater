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

    @SuppressLint("SdCardPath")
    private static final String zickiePath = "/sdcard/Zickie";

    private Button btnNextFbAccount;
    private Button btnPrevFbAccount;
    private TextView tvFacebookId;
    private TextView tvPassword;
    private TextView tvCurAccount;

    private int curAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Button btnNewBigOne = (Button) findViewById(R.id.btnNewBigOne);
        Button btnInstall = (Button) findViewById(R.id.btnInstall);
        Button btnUninstall = (Button) findViewById(R.id.btnUninstall);
        btnPrevFbAccount = (Button) findViewById(R.id.btnPrevFbAccount);
        btnNextFbAccount = (Button) findViewById(R.id.btnNextFbAccount);
        tvCurAccount = (TextView) findViewById(R.id.tvCurAccount);
        tvFacebookId = (TextView) findViewById(R.id.tvFacebookId);
        tvPassword = (TextView) findViewById(R.id.tvPassword);

        showCurAccount();
        showAccountInfo();

        DeviceIdFragment deviceIdFragment = new DeviceIdFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.deviceIdContainer, deviceIdFragment).commit();

        btnNewBigOne.setOnClickListener(this);
        btnPrevFbAccount.setOnClickListener(this);
        btnNextFbAccount.setOnClickListener(this);
        btnInstall.setOnClickListener(this);
        btnUninstall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnNewBigOne:
                launchBigOne();
                break;
            case R.id.btnInstall:
                installApps();
                break;
            case R.id.btnUninstall:
                uninstallApps();
                break;
            case R.id.btnPrevFbAccount:
                prevFbAccount();
                break;
            case R.id.btnNextFbAccount:
                nextFbAccount();
                break;
        }
    }

    public void launchBigOne() {
        new NewBigOneTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        uninstallApps();
    }

    public void installApps() {
        Intent installIntent = new Intent(DialogActivity.this, InstallActivity.class);
        startActivity(installIntent);
    }

    public void uninstallApps() {
        Intent uninstallIntent = new Intent(DialogActivity.this, UninstallActivity.class);
        startActivity(uninstallIntent);
    }

    public void prevFbAccount() {
        if (curAccount <= 0) {
            btnPrevFbAccount.setEnabled(false);
            return;
        }
        btnNextFbAccount.setEnabled(true);
        PrefsUtils.putCurrentAccount(this, curAccount - 1);
        showCurAccount();
        showAccountInfo();
    }

    public void nextFbAccount() {
        ArrayList<String> accountList;
        accountList = FileUtils.getLineList(accountListPath);
        if (curAccount >= (accountList.size() - 1)) {
            btnNextFbAccount.setEnabled(false);
            return;
        }
        btnPrevFbAccount.setEnabled(true);
        PrefsUtils.putCurrentAccount(this, curAccount + 1);
        showCurAccount();
        showAccountInfo();
    }

    public void showCurAccount() {
        curAccount = PrefsUtils.getCurrentAccount(this);
        tvCurAccount.setText(String.valueOf(curAccount));
    }

    public void showAccountInfo() {
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
            tvFacebookId.setText("N/A");
            tvPassword.setText("N/A");
            return;
        }

        ((ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("FacebookID", facebookId));

        String textData = facebookId + "|" + password;
        FileUtils.writeToTextFile(zickiePath, "cur_account", textData);

        String text = getString(R.string.tv_fb_id) + facebookId;
        tvFacebookId.setText(text);
        text = getString(R.string.tv_fb_pass) + password;
        tvPassword.setText(text);
    }

    private class NewBigOneTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            AppUtils.killAsRoot(bigOnePkgName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!AppUtils.launch(DialogActivity.this, bigOnePkgName)) {
                Toast.makeText(DialogActivity.this, getString(R.string.toast_err_launch), Toast.LENGTH_SHORT).show();
            }
        }
    }
}



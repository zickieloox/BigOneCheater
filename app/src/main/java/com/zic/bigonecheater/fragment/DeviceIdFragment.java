package com.zic.bigonecheater.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zic.bigonecheater.R;
import com.zic.bigonecheater.utils.PrefsUtils;
import com.zic.bigonecheater.utils.RandomUtils;
import com.zic.bigonecheater.utils.ShellUtils;

public class DeviceIdFragment extends Fragment implements View.OnClickListener {

    @SuppressLint("SdCardPath")
    private static final String androidDataPath = "/data/data/";
    private static final String deviceIdChangerPkgName = "com.phoneinfo.changerpro";
    private static final String myPackageName = "com.zic.bigonecheater";
    private static final String deviceIdChangerPrefsPath = androidDataPath + deviceIdChangerPkgName + "/shared_prefs/xpref_config.xml";
    private static final String myDeviceIdChangerPrefsPath = androidDataPath + myPackageName + "/shared_prefs/xpref_config.xml";

    private TextView tvCurDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device_id, container, false);

        Button btnNewDevice = (Button) rootView.findViewById(R.id.btnNewDevice);
        tvCurDevice = (TextView) rootView.findViewById(R.id.tvCurDevice);

        showCurDevice();

        btnNewDevice.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnNewDevice:
                changeDeviceInfo();
                break;
        }
    }

    public void showCurDevice() {
        new ShowCurDeviceTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void changeDeviceInfo() {

        String newImei = RandomUtils.newValidImei();

        PrefsUtils.putImei(getActivity(), newImei);

        new ChangeDeviceInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ChangeDeviceInfoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Copy back the new Device Id Changer pref to apply changes
            ShellUtils.copyFileAsRoot(myDeviceIdChangerPrefsPath, deviceIdChangerPrefsPath);

            // Set read - write permission for new Device Id Changer pref
            ShellUtils.setPerm(deviceIdChangerPrefsPath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showCurDevice();
            Toast.makeText(getActivity(), getString(R.string.toast_new_device), Toast.LENGTH_SHORT).show();
        }
    }

    private class ShowCurDeviceTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Copy the old Device Id Changer pref to get information and edit
            ShellUtils.copyFileAsRoot(deviceIdChangerPrefsPath, myDeviceIdChangerPrefsPath);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String text = PrefsUtils.getImei(getActivity());
            tvCurDevice.setText(text);
        }
    }

}
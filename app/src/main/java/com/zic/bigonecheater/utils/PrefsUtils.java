package com.zic.bigonecheater.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {

    private static final String PREFS_MAIN = "prefs";
    private static final String KEY_CURRENT_ACCOUNT = "cur_account";
    private static final String KEY_FIRST_RUN = "first_run";

    private static final String PREFS_DEVICE_ID_CHANGER = "xpref_config";
    private static final String KEY_IMEI = "imei";
    private static final String KEY_DEVICE_ID = "android_id";
    private static final String KEY_ADS_ID = "googlead_id";
    private static final String KEY_SERIAL = "serial";
    private static final String KEY_BLUE_MAC = "bluetooth_mac";
    private static final String KEY_WIFI_MAC = "mac_address";

    private static SharedPreferences getSharedPreferences(Context context, String prefsName) {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public static int getCurrentAccount(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_MAIN);
        return prefs.getInt(KEY_CURRENT_ACCOUNT, 0);
    }

    public static boolean putCurrentAccount(final Context context, int curAccount) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_MAIN).edit();
        editor.putInt(KEY_CURRENT_ACCOUNT, curAccount);

        return editor.commit();
    }

    public static boolean isFirstRun(Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_MAIN);

        if (prefs.getBoolean(KEY_FIRST_RUN, true)) {
            prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
            return true;
        } else {
            return false;
        }
    }

    public static boolean putImei(final Context context, String newImei) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER).edit();

        editor.putString(KEY_IMEI, newImei);

        return editor.commit();
    }

    public static String getImei(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER);
        return prefs.getString(KEY_IMEI, "");
    }

    public static boolean putDeviceId(final Context context, String newDeviceId) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER).edit();

        editor.putString(KEY_DEVICE_ID, newDeviceId);

        return editor.commit();
    }

    public static String getDeviceId(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER);
        return prefs.getString(KEY_DEVICE_ID, "");
    }

    public static boolean putAdsId(final Context context, String newAdsId) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER).edit();

        editor.putString(KEY_ADS_ID, newAdsId);

        return editor.commit();
    }

    public static String getAdsId(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER);
        return prefs.getString(KEY_ADS_ID, "");
    }

    public static boolean putSerial(final Context context, String newSerial) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER).edit();

        editor.putString(KEY_SERIAL, newSerial);

        return editor.commit();
    }

    public static String getSerial(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER);
        return prefs.getString(KEY_SERIAL, "");
    }

    public static boolean putBlueMac(final Context context, String newBlueMac) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER).edit();

        editor.putString(KEY_BLUE_MAC, newBlueMac);

        return editor.commit();
    }

    public static String getBlueMac(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER);
        return prefs.getString(KEY_BLUE_MAC, "");
    }

    public static boolean putWifiMac(final Context context, String newWifiMac) {
        SharedPreferences.Editor editor = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER).edit();

        editor.putString(KEY_WIFI_MAC, newWifiMac);

        return editor.commit();
    }

    public static String getWifiMac(final Context context) {
        SharedPreferences prefs = PrefsUtils.getSharedPreferences(context, PREFS_DEVICE_ID_CHANGER);
        return prefs.getString(KEY_WIFI_MAC, "");
    }
}

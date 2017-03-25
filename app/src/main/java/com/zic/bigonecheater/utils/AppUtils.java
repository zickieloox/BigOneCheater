package com.zic.bigonecheater.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.jrummyapps.android.shell.CommandResult;
import com.jrummyapps.android.shell.Shell;

public class AppUtils {

    private static final String TAG = "AppUtils";

    public static boolean installAsRoot(String apkFilePath) {
        apkFilePath = getValid(apkFilePath);
        CommandResult result = Shell.SU.run("pm install " + apkFilePath);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "installAsRoot: " + result.getStderr());
            return false;
        }
    }

    public static boolean uninstallAsRoot(String pkgName) {
        CommandResult result = Shell.SU.run("pm uninstall " + pkgName);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "uninstallAsRoot: " + result.getStderr());
            return false;
        }
    }

    public static boolean clearData(String pkgName) {
        CommandResult result = Shell.SU.run("pm clear " + pkgName);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "clearData: " + result.getStderr());
            return false;
        }
    }

    public static boolean killAsRoot(String pkgName) {
        CommandResult result = Shell.SU.run("am force-stop " + pkgName);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "killAsRoot: " + result.getStderr());
            return false;
        }
    }

    public static boolean launch(Context context, String pkgName) {

        PackageManager pm = context.getPackageManager();
        Intent intentLaunch = pm.getLaunchIntentForPackage(pkgName);
        if (intentLaunch != null) {
            context.startActivity(intentLaunch);
            return true;
        }

        Log.e(TAG, "launch: " + "Error! Cannot launch " + pkgName);
        return false;
    }

    public static String getPkgNameFromApk(Context context, String apkFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, 0);

        return pi.applicationInfo.packageName;
    }

    // A function make the target String to a valid String works with
    // Shell Commands - replace all spaces with " "
    private static String getValid(String targetString) {
        return targetString.replaceAll("\\s+", "\" \"");
    }
}
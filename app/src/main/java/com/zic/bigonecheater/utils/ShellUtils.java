package com.zic.bigonecheater.utils;

import android.util.Log;

import com.jrummyapps.android.shell.CommandResult;
import com.jrummyapps.android.shell.Shell;

public class ShellUtils {

    private static final String TAG = "ShellUtils";

    public static boolean isSuAvailable() {
        return Shell.SU.available();
    }

    // Delete a file or dir as root (no read/write permission)
    public static boolean deleteAsRoot(String filePath) {
        filePath = getValid(filePath);

        CommandResult result = Shell.SU.run("rm -rf " + filePath);
        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "deleteAsRoot: " + result.getStderr());
            return false;
        }
    }

    public static boolean isBusyBoxAvailabe() {

        CommandResult result = Shell.SU.run("busybox");
        return result.exitCode == 0;
    }

    public static boolean busyBoxCopyFile(String srcPath, String desPath) {
        srcPath = getValid(srcPath);
        desPath = getValid(desPath);

        CommandResult result = Shell.SU.run("busybox cp " + srcPath + " " + desPath);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "busyBoxCopyFile: " + result.getStderr());
            return false;
        }
    }

    public static boolean busyBoxMoveFile(String srcPath, String desPath) {
        srcPath = getValid(srcPath);
        desPath = getValid(desPath);

        CommandResult result = Shell.SU.run("busybox mv " + srcPath + " " + desPath);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "busyBoxMoveFile: " + result.getStderr());
            return false;
        }
    }

    public static boolean copyFileAsRoot(String srcPath, String desPath) {

        srcPath = getValid(srcPath);
        desPath = getValid(desPath);

        CommandResult result = Shell.SU.run("cat " + srcPath + " > " + desPath);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "copyFileAsRoot: " + result.getStderr());
            return false;
        }
    }

    public static boolean setPerm(String filePath) {
        filePath = getValid(filePath);

        CommandResult result = Shell.SU.run("chmod 664 " + filePath);

        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "setPerm: " + result.getStderr());
            return false;
        }
    }

    // A function make the target String to a valid String works with
    // Shell Commands - replace all spaces with " "
    private static String getValid(String targetString) {
        return targetString.replaceAll("\\s+", "\" \"");
    }
}

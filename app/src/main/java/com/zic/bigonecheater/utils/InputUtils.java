package com.zic.bigonecheater.utils;

import android.util.Log;

import com.jrummyapps.android.shell.CommandResult;
import com.jrummyapps.android.shell.Shell;

public class InputUtils {

    private static final String TAG = "InputUtils";

    public static boolean key(String keycode) {

        CommandResult result = Shell.SU.run("input keyevent " + keycode);
        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "key: " + result.getStderr());
            return false;
        }
    }

    public static boolean type(String text) {
        CommandResult result = Shell.SU.run("input text " + text);
        if (result.exitCode == 0) {
            return true;
        } else {
            Log.e(TAG, "type: " + result.getStderr());
            return false;
        }
    }

    // A function make the target String to a valid String works with
    // Shell Commands "input text" - replace all spaces with "%s"
    private static String getValidText(String targetString) {
        return targetString.replaceAll("\\s+", "%s");
    }

}

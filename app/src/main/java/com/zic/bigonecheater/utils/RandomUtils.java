package com.zic.bigonecheater.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static String newValidImei() {
        String imei;
        Random random = new Random();

        // More information: https://en.wikipedia.org/wiki/Reporting_Body_Identifier
        String[] rbi = new String[]{"01", "10", "30", "33", "35", "44", "45", "49", "50", "51", "52", "53", "54", "86", "91", "98", "99"};

        imei = rbi[random.nextInt(rbi.length)];
        while (imei.length() < 14) {
            imei = imei + Character.forDigit(random.nextInt(10), 10);
        }

        return imei + imeiLastDigit(imei);
    }

    // Choose the last digit so that it causes the entire string to pass the checksum
    private static String imeiLastDigit(String str) {
        int i = 0;
        int k = 0;
        while (i < str.length()) {
            int digit = Character.digit(str.charAt((str.length() - 1) - i), 10);
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            k += digit;
            i++;
        }
        return String.valueOf(Character.forDigit((k * 9) % 10, 10));
    }


    public static String newDeviceId() {
        final int DEVICE_ID_DIGITS = 16;
        String deviceId = "";

        for (int i = 1; i <= DEVICE_ID_DIGITS; i++) {

            if (isNaN()) {
                deviceId = deviceId + newHex();
            } else {
                deviceId = deviceId + newNumber();
            }
        }

        return deviceId;
    }

    // Example: 9e0681e7-4d20-4aea-b27c-8c071f220c16
    public static String newAdsId() {
        final int ADS_ID_DIGITS = 36;
        String blueMac = "";
        List<Integer> semicolonPosition = new ArrayList<>();
        semicolonPosition.add(9);
        semicolonPosition.add(14);
        semicolonPosition.add(19);
        semicolonPosition.add(24);

        for (int i = 1; i <= ADS_ID_DIGITS; i++) {

            if (semicolonPosition.contains(i)) {
                blueMac = blueMac + "-";
                continue;
            }

            if (isNaN()) {
                blueMac = blueMac + newHex();
            } else {
                blueMac = blueMac + newNumber();
            }
        }

        return blueMac;
    }


    public static String newSerial() {
        final int SERIAL_DIGITS = 12;
        String serial = "";

        for (int i = 1; i <= SERIAL_DIGITS; i++) {

            if (isNaN()) {
                serial = serial + newHex().toUpperCase();
            } else {
                serial = serial + newNumber();
            }
        }

        return serial;
    }

    // Example: 3D:45:56:7E:8A:12
    public static String newBlueMac() {
        final int MAC_DIGITS = 17;
        String blueMac = "";
        List<Integer> semicolonPosition = new ArrayList<>();
        semicolonPosition.add(3);
        semicolonPosition.add(6);
        semicolonPosition.add(9);
        semicolonPosition.add(12);
        semicolonPosition.add(15);

        for (int i = 1; i <= MAC_DIGITS; i++) {

            if (semicolonPosition.contains(i)) {
                blueMac = blueMac + ":";
                continue;
            }

            if (isNaN()) {
                blueMac = blueMac + newHex().toUpperCase();
            } else {
                blueMac = blueMac + newNumber();
            }
        }

        return blueMac;
    }

    // Example: 3D:45:56:7E:8A:12
    public static String newWifiMac() {
        final int MAC_DIGITS = 17;
        String blueMac = "";
        List<Integer> semicolonPosition = new ArrayList<>();
        semicolonPosition.add(3);
        semicolonPosition.add(6);
        semicolonPosition.add(9);
        semicolonPosition.add(12);
        semicolonPosition.add(15);

        for (int i = 1; i <= MAC_DIGITS; i++) {

            if (semicolonPosition.contains(i)) {
                blueMac = blueMac + ":";
                continue;
            }

            if (isNaN()) {
                blueMac = blueMac + newHex().toUpperCase();
            } else {
                blueMac = blueMac + newNumber();
            }
        }

        return blueMac;
    }

    // Return 0 -> 9
    private static String newNumber() {
        return String.valueOf(new Random().nextInt(10));
    }

    // Not a number: true or false
    private static boolean isNaN() {
        return Math.random() < 0.5;
    }

    // Return a, b, c, d, e, f
    private static String newHex() {
        int random = new Random().nextInt(6);
        String character = null;
        switch (random) {
            case 0:
                character = "a";
                break;
            case 1:
                character = "b";
                break;
            case 2:
                character = "c";
                break;
            case 3:
                character = "d";
                break;
            case 4:
                character = "e";
                break;
            case 5:
                character = "f";
                break;
        }

        return character;
    }
}

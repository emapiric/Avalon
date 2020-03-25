package com.example.sharedPreferences;

import android.content.SharedPreferences;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

//Cuvaju samo dva podatka, macAdresu korisnika i idSobe
public class Base {

    private final String preferencesKey = "PreferencesKey";
    private final String userKey = "userKey";
    private final String roomkey = "roomKey";
    public static SharedPreferences sharedPreferences;

    public String getPreferencesKey() {
        return preferencesKey;
    }

    public void saveUserMacAddress() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userKey, getMacAddress());
        editor.commit();

    }

    public void saveUserRoomId(String roomId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(roomkey, roomId);
        editor.commit();

    }

    public String getUserMacAddress() {
        return sharedPreferences.getString(userKey, "User doesn't exist");
    }

    public String getUserRoom() {
        return sharedPreferences.getString(roomkey, "No room for this user");
    }

    public String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}

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
    //U clasi ne moze da se inicijalizuje SharedPreferences da znate ! Mora u onCreate metodi
    public static SharedPreferences sharedPreferences;

    //Sve ovo sto sam nazvao key , to je identifikator za posebne objekte u bazi (preciznije xml jer ovo SharedPref radi
    //sa XML-om ) znaci prefernecesKey je bukvalno kljuc kojoj ti pristupas xml-u
    //userKey je kljuc za pristup podatku gdje se nalazi njegova mac adresu u xml
    //roomKey ista fora kao i userKey
    public String getPreferencesKey() {
        return preferencesKey;
    }

    //Ovde ubacujemo mac adresu korisnika u bazu
    public void saveUserMacAddress() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userKey, getMacAddress());
        editor.commit();

    }

    //Ovdje ubacujemo id sobe u bazu
    public void saveUserRoomId(String roomId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(roomkey, roomId);
        editor.commit();

    }
//Vraca MAC adresu korisnika
    public String getUserMacAddress() {
        return sharedPreferences.getString(userKey, "User doesn't exist");
    }
//Vraca id sobe u kojoj se korisnik nalazi
    public String getUserRoom() {
        //Ovaj drugi parametar znaci ako nema nikakvog podatka sa roomKey , on vraca ovu poruju sto sam napisao ,,no room for this user''
        return sharedPreferences.getString(roomkey, "No room for this user");
    }
//Metoda vraca MAC adresu korisnika
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

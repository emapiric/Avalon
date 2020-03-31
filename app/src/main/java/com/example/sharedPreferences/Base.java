package com.example.sharedPreferences;

import android.content.SharedPreferences;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

//Cuvaju samo dva podatka, macAdresu korisnika i idSobe
public class Base {

    private final String preferencesKey = "PreferencesKey";
    private final String playerId = "playerId";
    private final String roomId = "roomId";
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
    public void savePlayerId(String playerId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.playerId, playerId);
        editor.commit();

    }

    //Ovdje ubacujemo id sobe u bazu
    public void saveRoomId(String roomId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.roomId, roomId);
        editor.commit();

    }

    //Vraca MAC adresu korisnika
//    public String getUserMacAddress() {
//        return sharedPreferences.getString(userKey, "User doesn't exist");
//    }

    //Vraca id sobe u kojoj se korisnik nalazi
    public String getRoomId() {
        //Ovaj drugi parametar znaci ako nema nikakvog podatka sa roomKey , on vraca ovu poruju sto sam napisao ,,no room for this user''
        return sharedPreferences.getString(roomId, "null");
    }

    //Metoda vraca MAC adresu korisnika
    public String getPlayerId() {
        return sharedPreferences.getString(playerId, "null");
    }
}

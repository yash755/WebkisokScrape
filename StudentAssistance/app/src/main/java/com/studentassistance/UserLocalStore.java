package com.studentassistance;

import android.content.Context;
import android.content.SharedPreferences;


public class UserLocalStore {

    public static final String SP_Name = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_Name,0);
    }

    public void userData(String eno)
    {
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("eno",eno);
        speditor.commit();
    }

    public String geteno(){

        String name = userLocalDatabase.getString("eno", "");
        return name;

    }


    public void setUserloggedIn(boolean loggedIn){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putBoolean("loggedIn",loggedIn);
        speditor.commit();

    }



    public boolean getuserloggedIn(){

        if(userLocalDatabase.getBoolean("loggedIn",false) == true)
            return true;
        else
            return false;
    }



    public void clearUserdata(){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.clear();

    }

    public void setGcm(String token){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("gcm",token);
        speditor.commit();

    }

    public String getgcm(){

        String name = userLocalDatabase.getString("gcm", "");
        return name;

    }

    public void setWishlistCount(int count){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putInt("wishlistCount",count);
        speditor.commit();

    }

    public int getWishlistCount(){

        int name = userLocalDatabase.getInt("wishlistCount",0);
        return name;

    }

}

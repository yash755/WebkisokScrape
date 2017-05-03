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

    public void userData(String eno,String password,String dob,String phone)
    {
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("eno",eno);
        speditor.putString("password",password);
        speditor.putString("dob",dob);
        speditor.putString("phone",phone);
        speditor.commit();
    }

    public String geteno(){

        String name = userLocalDatabase.getString("eno", "");
        return name;

    }

    public String getpno(){

        String name = userLocalDatabase.getString("phone", "");
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



}

package com.itsfrz.authentication.model.database

import android.content.Context
import android.content.SharedPreferences


public const val PREFERENCE_NAME = "PREF_CONTACT_APP"
public const val PREFERENCE_PRIVATE_MODE : Int = Context.MODE_PRIVATE;
public const val PREFERENCE_LOGGED_IN = "PREF_LOGGED_IN"
public const val PREFERENCE_CURRENT_THEME = "PREF_COLOR_THEME"
public const val PREFERENCE_LOGGED_IN_DATE = "PREF_LOGGED_DATE"
public const val PREFERENCE_USERNAME = "PREF_USERNAME"
public const val PREFERENCE_EMAIL = "PREF_USER_EMAIL"


class PreferenceRespository(val context: Context){

    private val sharedPreferences : SharedPreferences

    init {
            sharedPreferences  = context.getSharedPreferences(
            PREFERENCE_NAME,
            PREFERENCE_PRIVATE_MODE
        )

    }


    private val editor = sharedPreferences.edit()

    private fun String.put(long : Long){
        editor.putLong(this,long)
        editor.commit()
    }

    private fun String.put(int : Int){
        editor.putInt(this,int)
        editor.commit()
    }

    private fun String.put(boolean : Boolean){
        editor.putBoolean(this,boolean)
        editor.commit()
    }

    private fun String.put(string : String){
        editor.putString(this,string)
        editor.commit()
    }

    private fun String.getLong() = sharedPreferences.getLong(this,0)

    private fun String.getBoolean() = sharedPreferences.getBoolean(this,false)

    private fun String.getInt() = sharedPreferences.getInt(this,0)

    private fun String.getString() = sharedPreferences.getString(this,"")

    fun setLoggedIn(isLoggedIn : Boolean){
        PREFERENCE_LOGGED_IN.put(isLoggedIn)
    }

    fun getLoggedIn () = PREFERENCE_LOGGED_IN.getBoolean()

    fun setCurrentUser(username : String){
        PREFERENCE_USERNAME.put(username)
    }

    fun getCurrentUser() = PREFERENCE_USERNAME.getString()

    fun setCurrentEmail(emailId : String){
        PREFERENCE_EMAIL.put(emailId)
    }

    fun getCurrentEmail() = PREFERENCE_EMAIL.getString()



    fun setLoggedInDate(date : String){
        PREFERENCE_LOGGED_IN_DATE.put(date)
    }

    fun getLoggedInDate() = PREFERENCE_LOGGED_IN_DATE.getString()

    fun clearUser(){
       editor.clear()
       editor.commit()
    }
}
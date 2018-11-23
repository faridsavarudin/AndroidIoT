package id.dycode.edu.androidforiot.service

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import id.co.dycode.nb_iot.model.LoginResponse

class PreferenceHelper(context: Context) {

    val PREF_FLEX : String = "PREF_FLEX"

    val  IS_USER_LOGIN = "IS_USER_LOGIN"
    val AUTHENTICATE = "AUTHENTICATE"

    val preferenceHelper = context.getSharedPreferences(PREF_FLEX, Context.MODE_PRIVATE)

    fun setAutenticate(token: String){
        preferenceHelper.edit().putString(AUTHENTICATE, token).apply()
    }

    fun setLoginResponse(loginResponse: LoginResponse?){
        preferenceHelper.edit().putString(IS_USER_LOGIN, Gson().toJson(loginResponse)).apply()
    }

    fun getLoginResponse() : LoginResponse? {
        var json = preferenceHelper.getString(IS_USER_LOGIN, "")
        if (TextUtils.isEmpty(json)){
            return null
        }else {
            return Gson().fromJson(json, LoginResponse::class.java)
        }
    }

    fun getAuthenticate() : String{
        return preferenceHelper.getString(AUTHENTICATE, "")
    }

    fun deleteAccessToken(){
        preferenceHelper.edit().remove(AUTHENTICATE).apply()
        preferenceHelper.edit().remove(IS_USER_LOGIN).apply()
    }

}
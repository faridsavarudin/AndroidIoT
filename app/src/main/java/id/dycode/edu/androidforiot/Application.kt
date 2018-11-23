package id.dycode.edu.androidforiot

import android.app.Application
import id.dycode.edu.androidforiot.service.PreferenceHelper

class Application : Application(){

    companion object {
        var preferenceHelper : PreferenceHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        preferenceHelper = PreferenceHelper(applicationContext)

    }

}
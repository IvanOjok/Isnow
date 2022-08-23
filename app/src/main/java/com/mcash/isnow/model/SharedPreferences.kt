package com.mcash.isnow.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.mcash.isnow.utils.BaseActivity

class SharedPreferencesFile {

    private val myPREFERENCES = "USER"

    fun init(activity: BaseActivity): SharedPreferences {
        val sharedPreferences = activity.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences!!
    }


}
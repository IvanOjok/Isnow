package com.mcash.isnow.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences {
    private val preferenceFile = "LoanFile"
    private val LOAN_LIST = "loanList"

    private fun addLoanList(context: Context, loanList:String) {
        val editor = context.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE).edit()
        editor.putString(LOAN_LIST, loanList)
        editor.apply()
    }


    fun getLoanData(context: Context){
        val preferences = context.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE)
        val data = preferences.getString(LOAN_LIST, null)

        val retrievedDataType= object: TypeToken<List<Loan>>() {}.type
        //Gson().fromJson(data, retrievedDataType)
        Gson().fromJson<List<Loan>>(data, retrievedDataType)
    }

}
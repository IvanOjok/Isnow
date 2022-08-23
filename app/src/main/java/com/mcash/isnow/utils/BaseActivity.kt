package com.mcash.isnow.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcash.isnow.model.Account
import com.mcash.isnow.model.LoginResponse
import com.mcash.isnow.model.OTPResponse
import com.mcash.isnow.model.User

abstract class BaseActivity: AppCompatActivity() {

    lateinit var progressUtils: ProgressUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressUtils = ProgressUtils(this)
    }

    protected fun showProgressDialog() {
        progressUtils.showDialog()
    }

    protected fun hideProgressDialog() {
        progressUtils.hideDialog()
    }

    fun init(): SharedPreferences {
        val sharedPreferences = this.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences!!
    }

    fun insertUser(account: OTPResponse) {
        val pref = init()
        val editor = pref.edit()
        editor.putString(KEY_USER, account.data!!.user!!.name)
        editor.putString(KEY_ID, account.data!!.user!!.id.toString())
        editor.putString(KEY_CLUB, account.data!!.summary!!.investiment_club_name)
        editor.putString(KEY_LOAN, account.data!!.summary!!.loans.toString())
        editor.putString(KEY_CONT, account.data!!.summary!!.credit_limit_contributions.toString())
        editor.apply()
        editor.commit()
    }

    fun getUser() : User{
        val pref = init()
        val name = pref.getString(KEY_USER, "Default")
        val id = pref.getString(KEY_ID, "id")
        val ic = pref.getString(KEY_IC, "ic")
        val club = pref.getString(KEY_CLUB, "club")
        val loan = pref.getString(KEY_LOAN, "loan")
        val contributions = pref.getString(KEY_CONT, "cont")

        return User(name, id, ic, club, loan, contributions)

    }

    fun insertIC(ic:String){
        val pref = init()
        val editor = pref.edit()
        editor.putString(KEY_IC, ic)
        editor.apply()
        editor.commit()
    }

    fun getIC(): String {
        val pref = init()
        val ic = pref.getString(KEY_IC, "ic")
        return ic!!
    }

    fun insertEmail(email: String) {
        val pref = init()
        val editor = pref.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
        editor.commit()
    }

    fun getEmail(): String? {
        val pref = init()
        val email = pref.getString(KEY_EMAIL, null)
        return email!!
    }

    fun isLoggedIn(): Boolean {
        return true
    }

}
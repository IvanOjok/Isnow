package com.mcash.isnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.mcash.isnow.ui.auth.Login
import com.mcash.isnow.utils.BaseActivity
import com.mcash.isnow.utils.KEY_EMAIL

class Splash : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (init().getString(KEY_EMAIL, null) != null){
                Log.d("Email 1", KEY_EMAIL)
                Log.d("Email 2", getEmail()!!)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }, 4000)
    }
}
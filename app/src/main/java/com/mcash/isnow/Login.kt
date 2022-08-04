package com.mcash.isnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val clPinVerify = findViewById<ConstraintLayout>(R.id.clPinVerify)
        val clCode = findViewById<ConstraintLayout>(R.id.clCode)
        val btnSend = findViewById<MaterialButton>(R.id.btnSend)
        val btnVerifyCode = findViewById<MaterialButton>(R.id.btnVerifyCode)
        btnSend.setOnClickListener{
            clPinVerify.visibility = View.GONE
            clCode.visibility = View.VISIBLE
        }

        btnVerifyCode.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
package com.mcash.isnow.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.mcash.isnow.MainActivity
import com.mcash.isnow.databinding.ActivityVerificationBinding
import com.mcash.isnow.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Verification : BaseActivity() {

    private lateinit var binding: ActivityVerificationBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val ic = intent.getStringExtra("id")
        Log.d("IC intent", ic?: "")
        insertIC(ic!!)
        val email = getEmail()

        with(binding){

            //set email address
            id.setText(email)

            with(viewModel){
                loginState.observe(this@Verification){
                    when(it){
                        is LoginState.Loading -> {
                            hideProgressDialog()
                        }
                        is LoginState.Error -> {
                            hideProgressDialog()
                            mtvTest.visibility = View.VISIBLE
                            mtvTest.text = it.message
                        }
                        is LoginState.Success -> {
                            hideProgressDialog()
                            clPinVerify.visibility = View.GONE
                            clCode.visibility = View.VISIBLE
                        }
                        else -> {

                        }
                    }
                }

                verifyOtpState.observe(this@Verification){
                    when(it){
                        is VerifyOtpState.Loading -> {
                            showProgressDialog()
                        }
                        is VerifyOtpState.Error -> {
                            hideProgressDialog()
                            mtvTest2.visibility = View.VISIBLE
                            resend.visibility = View.GONE
                            mtvTest2.text = it.message
                        }
                        is VerifyOtpState.Success -> {
                            hideProgressDialog()
                            insertUser(it.data)
                            startActivity(Intent(this@Verification, MainActivity::class.java))
                            finish()
                        }
                        else -> {

                        }
                    }
                }
            }

            btnSend.setOnClickListener {
                when{
                    id.text.toString().isEmpty() -> {
                        mtvTest.visibility = View.VISIBLE
                        mtvTest.text = "Enter Correct ID"
                    }
                    etPin.text.toString().isEmpty() -> {
                        mtvTest.visibility = View.VISIBLE
                        mtvTest.text = "Enter Correct Password"
                    }
                    else -> {
                        val c = getIC()
                        viewModel.loginRequest(id.text.toString(), c, etPin.text.toString())

                    }
                }
            }

            btnVerifyCode.setOnClickListener {
                when{
                    codePinView.text.toString().isEmpty() -> {
                        mtvTest.visibility = View.VISIBLE
                        mtvTest.text = "Enter Correct OTP"
                    }
                    else -> {
                        val c = getIC()
                        viewModel.verifyOTP(id.text.toString(), c,  etPin.text.toString(), codePinView.text.toString())
                    }
                }
            }

        }

    }
}
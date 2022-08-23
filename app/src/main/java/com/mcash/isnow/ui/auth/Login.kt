package com.mcash.isnow.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mcash.isnow.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcash.isnow.MainActivity
import com.mcash.isnow.adapter.ClubAdapter
import com.mcash.isnow.utils.BaseActivity

@AndroidEntryPoint
class Login : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        with(binding){

            with(viewModel){
                accountState.observe(this@Login){
                    when(it){
                        is AccountState.Loading -> {
                            showProgressDialog()
                        }
                        is AccountState.Error -> {
                            hideProgressDialog()
                            mtvTest1.visibility = View.VISIBLE
                            mtvTest1.text = it.message
                        }
                        is AccountState.Success -> {
                            hideProgressDialog()
                            clCheck.visibility = View.GONE
                            clList.visibility = View.VISIBLE

                            insertEmail(phone.text.toString())

                            accList.adapter = ClubAdapter(this@Login, it.account.data!!.investiment_clubs!!)
                            accList.layoutManager = LinearLayoutManager(this@Login)

                            //clPinVerify.visibility = View.VISIBLE
                        }
                        else -> {

                        }
                    }
                }


            }

            btnAcc.setOnClickListener {
                when{
                    phone.text.toString().isEmpty() -> {
                        mtvTest1.visibility = View.VISIBLE
                        mtvTest1.text = "Enter Correct ID"
                    }
                    else -> {
                        viewModel.checkUser(phone.text.toString())
                    }
                }
            }

        }
    }
}
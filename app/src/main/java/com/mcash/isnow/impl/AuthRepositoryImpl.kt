package com.mcash.isnow.impl

import android.util.Log
import com.mcash.isnow.model.Account
import com.mcash.isnow.model.Borrow
import com.mcash.isnow.model.LoanProducts
import com.mcash.isnow.model.OTPResponse
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.retrofit.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository  {
    override suspend fun getAccount(user: String): Account {
        return try{
            val response = authService.getAccount(user)
            Log.d("Response", "$response")
            response
        }
        catch (t: Throwable){
            Log.d("T",  "$t")
            throw t
        }
    }

    override suspend fun sendLoginRequest(params: HashMap<String, Any>): Int {
        return try {
            val response = authService.sendLoginRequest(params).status_code
            response!!
        }catch (t: Throwable){
            throw t
        }
    }

    override suspend fun sendLoginWithOTP(params: HashMap<String, Any>): OTPResponse {
        return try{
            val response = authService.sendLoginWithOTP(params)
            response
        }catch (t: Throwable){
            throw t
        }
    }

    override suspend fun getLoanProducts(params: HashMap<String, Any>): LoanProducts {
        return try {
            val response = authService.getLoanProducts(params)
            Log.d("server", "$response")
            response
        }catch (t: Throwable){
            throw t
        }
    }

    override suspend fun borrow(params: HashMap<String, Any>): Borrow {
        return try {
            val response = authService.borrow(params)
            response
        }catch (t: Throwable){
            throw t
        }
    }
}
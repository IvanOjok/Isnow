package com.mcash.isnow.repository

import com.mcash.isnow.model.Account
import com.mcash.isnow.model.Borrow
import com.mcash.isnow.model.LoanProducts
import com.mcash.isnow.model.OTPResponse


interface AuthRepository {

    suspend fun getAccount(user: String): Account

    suspend fun sendLoginRequest(params: HashMap<String, Any>): Int

    suspend fun sendLoginWithOTP(params: HashMap<String, Any>): OTPResponse

    suspend fun getLoanProducts(params: HashMap<String, Any>): LoanProducts

    suspend fun borrow(params: HashMap<String, Any> /* = java.util.HashMap<kotlin.String, kotlin.Any> */): Borrow
}
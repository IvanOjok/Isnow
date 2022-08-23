package com.mcash.isnow.retrofit

import com.mcash.isnow.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @GET("retrieve_accounts")
    suspend fun getAccount(@Query("user") user: String): Account

    @POST("login")
    suspend fun sendLoginRequest(@Body params:HashMap<String, Any>): LoginResponse

    @POST("login")
    suspend fun sendLoginWithOTP(@Body params: HashMap<String, Any>): OTPResponse

    @POST("loan_products")
    suspend fun getLoanProducts(@Body params: HashMap<String, Any>):LoanProducts

    @POST("borrow")
    suspend fun borrow(@Body params: HashMap<String, Any>): Borrow
}
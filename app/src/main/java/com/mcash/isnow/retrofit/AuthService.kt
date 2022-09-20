package com.mcash.isnow.retrofit

import com.mcash.isnow.model.*
import retrofit2.http.*

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

    @POST("repay_loan")
    suspend fun repayLoan(@Body params: HashMap<String, Any>): LoanRepay

    @POST("add_contribution")
    suspend fun makeContribution(@Body params: HashMap<String, Any>): Contribution

    @POST("contributions")
    suspend fun getContProducts(@Body params: HashMap<String, Any>): ContributionProducts

    @POST("contributions/{id}")
    suspend fun getContList(@Path("id") id:String, @Body params: HashMap<String, Any>): ContributionList


}
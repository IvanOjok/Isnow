package com.mcash.isnow.repository

import com.mcash.isnow.model.*


interface AuthRepository {

    suspend fun getAccount(user: String): Account

    suspend fun sendLoginRequest(params: HashMap<String, Any>): Int

    suspend fun sendLoginWithOTP(params: HashMap<String, Any>): OTPResponse

    suspend fun getLoanProducts(params: HashMap<String, Any>): LoanProducts

    suspend fun borrow(params: HashMap<String, Any>): Borrow

    suspend fun repayLoan(params: HashMap<String, Any>): LoanRepay

    suspend fun makeContribution(params: HashMap<String, Any>): Contribution

    suspend fun getContributionProducts(params: HashMap<String, Any>): ContributionProducts

    suspend fun getContributionList(id: String, params: HashMap<String, Any>): ContributionList


}
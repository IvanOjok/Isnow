package com.mcash.isnow.repository

interface UtilRepository {

    fun getNetworkError(throwable: Throwable): String
}
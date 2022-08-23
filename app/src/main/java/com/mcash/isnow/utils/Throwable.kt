package com.mcash.isnow.utils

import com.google.gson.Gson
import com.mcash.isnow.model.GenericError
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.Throwable

fun Throwable.resolveError(): String {

    return when (this) {
        is SocketTimeoutException -> {
            "Connection failed"
        }
        is ConnectException -> {
            "No internet access"
        }
        is UnknownHostException -> {
            "Unable to reach server"
        }

        is HttpException -> {
            try {
                var m: String ?= null
                this.response()?.errorBody()?.charStream()?.let {
                    m = Gson().fromJson(it, GenericError::class.java).message
//                    if (m == null){
//                        m = Gson().fromJson(it, GenericTokenError::class.java).message
//                    }
                }
                m.toString()

            } catch (ex: Exception) {
                "Connection failed"
            }
        }

        else -> {
            "Error occurred"
        }
    }
}
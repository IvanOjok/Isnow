package com.mcash.isnow.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    var message: String?,
    var status_code: Int?,
    var app_status: Boolean?,
    var data: Data?
)
data class Data (
    var otp: String?
)
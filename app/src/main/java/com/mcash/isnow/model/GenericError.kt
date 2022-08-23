package com.mcash.isnow.model

data class GenericError (
    var message: String?,
    var status_code: Int?,
    var app_status: Boolean?,
)
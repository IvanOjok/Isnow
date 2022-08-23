package com.mcash.isnow.model

data class OTPResponse (
    var message: String?,
    var status_code: Int?,
    var app_status: Boolean?,
    var data: OTPData?
)

data class OTPData(
    var user: UserData?,
    var summary: Summary?
)

data class UserData(
    var name: String?,
    var id: Int?
)

data class Summary (
    var loans: Int?,
    var credit_limit_contributions: Int?,
    var investiment_club_name: String?
)
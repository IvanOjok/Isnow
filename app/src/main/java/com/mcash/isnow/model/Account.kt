package com.mcash.isnow.model

data class Account(
    var app_status: Boolean?,
    var message: String?,
    var status_code: Int?,
    var data: AccountInfo?
)

data class AccountInfo (
    var investiment_clubs: ArrayList<AccountData>?
)

data class Club (
    var data: ArrayList<AccountData>?
)

data class AccountData (
    var id: Int?,
    var name: String?
)


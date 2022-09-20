package com.mcash.isnow.model

//data class LoanRepay (
//    var user: String,
//    val ic : String,
//    var payment_method: String,
//    var loan_product:String,
//    var amount:Int
//)

data class LoanRepay(
    var message:String?,
    var status_code:Int?,
    var loan_account_balance:Int?,
    var app_status:Boolean?
)
package com.mcash.isnow.model

import com.google.gson.annotations.SerializedName

data class LoanProducts(
    var message:String?,
    var status_code: Int?,
    var app_status: Boolean?,
    var data: LoanProd?
)

data class LoanProd(
    var loan_products:HashMap<String, String>?,
    var credit_limit:CreditLimit?,
    var payment_methods: HashMap<String, String>?
)

data class CreditLimit(
    var type:String?,
    var amount:String?
)


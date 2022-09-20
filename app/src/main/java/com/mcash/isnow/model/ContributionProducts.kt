package com.mcash.isnow.model

data class ContributionProducts (
    var message:String,
    var status_code:Int,
    var app_status:Boolean,
    var contributions: HashMap<String, String>
)


data class ContributionList(
    var message:String,
    var status_code:Int,
    var app_status:Boolean,
    var contributions: List<ContributionEntity>
)

data class ContributionEntity(
    var user:String,
    var contribution_amount:Int,
    var created_at:String,
    var contribution_id:String
)



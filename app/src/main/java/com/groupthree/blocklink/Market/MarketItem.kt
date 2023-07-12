package com.groupthree.blocklink.Market
import com.google.firebase.database.IgnoreExtraProperties
data class MarketItem(
    var name:String?= "",
    var description:String? = "",
    var price:Double? = 0.0
    )
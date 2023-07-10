package com.groupthree.blocklink.Market

import android.view.View

class MarketItem(var name:String, var description:String, var price:Double) {


    fun getItemName(): String {
        return name
    }

   fun setItemName(name:String){
        this.name = name
    }

    fun getItemDescription():String{
        return description
    }

    fun setItemDescription(description: String){
        this.description = description
    }

    fun getItemPrice():Double{
        return price
    }

    fun setItemPrice(price:Double){
        this.price = price
    }
}
package com.groupthree.blocklink.Market

class MarketItem(var id:Int, var name:String, var description:String, var price:Double) {


    fun getItemID(): Int {
        return id
    }

    fun setItemName(id: Int){
        this.id = id
    }
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
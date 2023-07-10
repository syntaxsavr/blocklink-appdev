package com.groupthree.blocklink.Market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.groupthree.blocklink.R


class RecyclerViewAdapterMarket(private val dataSet: Array<MarketItem>) :
    RecyclerView.Adapter<RecyclerViewAdapterMarket.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var image:ImageView
        var name:TextView
        var description: TextView
        var price: TextView


        init {
            image = view.findViewById(R.id.itemImage)
            name = view.findViewById(R.id.itemName)
            description = view.findViewById(R.id.itemDescription)
            price = view.findViewById(R.id.itemPrice)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.market_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.name.text = dataSet[position].getItemName()
        viewHolder.description.text = dataSet[position].getItemDescription()
        var price = dataSet[position].getItemPrice()

        if(price%1 == 1.00 || price%1 ==0.00){
            var priceInt = price.toInt()
            viewHolder.price.text = "€ $priceInt.-"
        }
        else{
            viewHolder.price.text = "€ $price"
        }
    }

    override fun getItemCount() = dataSet.size

}

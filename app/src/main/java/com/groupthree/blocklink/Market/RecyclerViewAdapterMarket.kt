package com.groupthree.blocklink.Market

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.groupthree.blocklink.R


class RecyclerViewAdapterMarket(var databaseReference: DatabaseReference) :
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


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                viewHolder.name.text =
                    dataSnapshot.child("i${viewHolder.adapterPosition}")
                        .child("name").getValue().toString()

                viewHolder.description.text =
                    dataSnapshot.child("i${viewHolder.adapterPosition}")
                        .child("description").getValue().toString()

                var price = dataSnapshot.child("i${viewHolder.adapterPosition}")
                    .child("price").getValue().toString().toDouble()


                if(price%1 == 1.00 || price%1 ==0.00){
                    var priceInt = price.toInt()
                    viewHolder.price.text = "€ $priceInt.-"
                }
                else{
                    viewHolder.price.text = "€ $price"
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

    }

    override fun getItemCount() = 10

}

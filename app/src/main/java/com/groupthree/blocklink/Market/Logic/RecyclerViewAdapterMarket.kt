package com.groupthree.blocklink.Market.Logic

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.groupthree.blocklink.R
import com.bumptech.glide.Glide


class RecyclerViewAdapterMarket(var databaseReference: DatabaseReference, var count:Int) :
    RecyclerView.Adapter<RecyclerViewAdapterMarket.ViewHolder>() {

    private lateinit var context: Context

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

        context = viewGroup.context


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val storage= Firebase.storage("gs://group3-appdev-2023.appspot.com")
        val storageReference = storage.reference


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var priceString=""

                if (viewHolder.adapterPosition < 10) {
                    viewHolder.name.text =
                        dataSnapshot.child("i0${viewHolder.adapterPosition}")
                            .child("name").getValue().toString()


                    viewHolder.description.text =
                        dataSnapshot.child("i0${viewHolder.adapterPosition}")
                            .child("description").getValue().toString()


                    priceString = dataSnapshot.child("i0${viewHolder.adapterPosition}")
                        .child("price").getValue().toString()


                }
                else{
                    viewHolder.name.text =
                        dataSnapshot.child("i${viewHolder.adapterPosition}")
                            .child("name").getValue().toString()

                    viewHolder.description.text =
                        dataSnapshot.child("i${viewHolder.adapterPosition}")
                            .child("description").getValue().toString()


                    priceString = dataSnapshot.child("i${viewHolder.adapterPosition}")
                        .child("price").getValue().toString()
                }

                val regex1 = "^[0-9]*.([0-9][0-9]?)?"
                if(priceString.matches(regex1.toRegex())){
                    viewHolder.price.text = "€ $priceString"
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        var pathString = ""
        if(viewHolder.absoluteAdapterPosition < 10){
            pathString = "i0${viewHolder.adapterPosition}.png"
        }
        else{
            pathString = "i${viewHolder.adapterPosition}.png"
        }

        Glide.with(context)
            .load(storageReference.child(pathString))
            .into(viewHolder.image)
    }

    override fun getItemCount() = count

}

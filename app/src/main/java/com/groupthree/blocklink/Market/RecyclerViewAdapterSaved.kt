package com.groupthree.blocklink.Market

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.groupthree.blocklink.MainActivity
import com.groupthree.blocklink.R

class RecyclerViewAdapterSaved(var count:Int) :
    RecyclerView.Adapter<RecyclerViewAdapterSaved.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReferenceItems: DatabaseReference
    private lateinit var databaseReferenceUsers: DatabaseReference

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var image: ImageView
        var name: TextView
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

        database = FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReferenceItems = database.getReference("items")
        databaseReferenceUsers = database.getReference("users")

        context = viewGroup.context


        return ViewHolder(view)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storage= Firebase.storage("gs://group3-appdev-2023.appspot.com")
        val storageReference = storage.reference

        var itemPosition = position.toString()

        databaseReferenceUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //TODO: replace with actual current user
                var itemId = dataSnapshot.child("cathrinsc").child("savedItems").child(itemPosition).getValue().toString()

                databaseReferenceItems.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        println(itemId)
                        holder.name.text = dataSnapshot.child(itemId).child("name").getValue().toString()
                        holder.description.text = dataSnapshot.child(itemId).child("description").getValue().toString()
                        var price = dataSnapshot.child(itemId).child("price").getValue().toString()
                        holder.price.text = "€ $price"


                        Glide.with(context)
                            .load(storageReference.child("$itemId.png"))
                            .into(holder.image)

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                    }

                })
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })







    }

    override fun getItemCount() = count

}
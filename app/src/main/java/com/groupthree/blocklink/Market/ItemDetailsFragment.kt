package com.groupthree.blocklink.Market

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.ItemdetailsFragmentBinding
import com.groupthree.blocklink.databinding.MarketFragmentBinding

class ItemDetailsFragment(var position:Int?) : Fragment(R.layout.itemdetails_fragment) {

    private var _binding: ItemdetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ItemdetailsFragmentBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("items")
        val storage= Firebase.storage("gs://group3-appdev-2023.appspot.com")
        val storageReference = storage.reference

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(position!! < 10 ) {
                    binding.itemNameDetails.text = dataSnapshot.child("i0$position")
                        .child("name").getValue().toString()

                    binding.descriptionDetails.text = dataSnapshot.child("i0$position")
                        .child("description").getValue().toString()

                    var price = dataSnapshot.child("i0$position").child("price").getValue().toString()
                    price = "€ $price"
                    binding.priceDetails.text = price

                }
                else{
                    binding.itemNameDetails.text = dataSnapshot.child("i$position")
                        .child("name").getValue().toString()

                    binding.descriptionDetails.text = dataSnapshot.child("i$position")
                        .child("description").getValue().toString()

                    var price = dataSnapshot.child("i$position")
                        .child("price").getValue().toString()
                    price = "€ $price"
                    binding.priceDetails.text = price

                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        var pathString = ""
        if(position!! < 10){
            pathString = "i0$position.png"
        }
        else{
            pathString = "i$position.png"
        }

        context?.let {
            Glide.with(it)
                .load(storageReference.child(pathString))
                .into(binding.itemimageDetails)
        }

        binding.backDetails.setOnClickListener{
            val marketFragment = MarketFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,marketFragment )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return binding.root
    }
}
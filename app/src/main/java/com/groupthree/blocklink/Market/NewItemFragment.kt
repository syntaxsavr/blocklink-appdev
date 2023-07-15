package com.groupthree.blocklink.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.NewitemFragmentBinding

class NewItemFragment: Fragment(R.layout.newitem_fragment) {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var _binding: NewitemFragmentBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = NewitemFragmentBinding.inflate(inflater, container, false)


        database =FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("items")


        binding.add.setOnClickListener() {
            if(binding.nameText.text.isNullOrBlank() || binding.descriptionText.text.isNullOrBlank()|| binding.priceText.text.isNullOrBlank()){
                val toast = Toast.makeText(requireContext(), "Please enter Name, Description and Price.", Toast.LENGTH_LONG)
                toast.show()

            }
            else{
                var priceDouble = 0.00
                val priceString = binding.priceText.text.toString()
                if (!isDoubleFormat(priceString)){
                    val toast = Toast.makeText(requireContext(), "Please enter price in following format: \"xxx.xx\".", Toast.LENGTH_LONG)
                    toast.show()
                    }
                else {
                    priceDouble = priceString.toDouble()

                    var item = MarketItem(
                        binding.nameText.text.toString(),
                        binding.descriptionText.text.toString(),
                        priceDouble
                    )

                    var count = 0
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            count = dataSnapshot.childrenCount.toInt()
                            databaseReference.child("i$count").setValue(item)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.i("Firebase", "addListenerForSingleValueEvent: cancelled")
                        }
                    })



                    val marketFragment = MarketFragment()
                    val fragmentTransaction = requireFragmentManager().beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, marketFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }

        binding.cancel.setOnClickListener{
            val marketFragment = MarketFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, marketFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return binding.root
    }

    fun isDoubleFormat(str: String): Boolean {
        return try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
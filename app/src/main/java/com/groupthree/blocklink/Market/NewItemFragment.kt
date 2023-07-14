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
            if(binding.name.text == null || binding.description.text == null || binding.price.text == null){
                val toast = Toast.makeText(requireContext(), "Please enter Name, Description and Price.", Toast.LENGTH_LONG)
                toast.show()
            }
            else{
                var count = 0
                var bool = true
                while(bool) {
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.child("i$count").exists()) {
                                count++
                            } else {
                                bool = false
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                        }
                    })
                }

            var item = MarketItem(binding.name.text.toString(), binding.description.text.toString(), binding.price.text.toString().toDouble())
            databaseReference.child("items").child("i$count").setValue(item)

            val marketFragment = MarketFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, marketFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
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

}
package com.groupthree.blocklink.Market

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.groupthree.blocklink.MainActivity
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.MarketFragmentBinding
import com.groupthree.blocklink.databinding.SavedFragmentBinding

class SavedFragment: Fragment(R.layout.saved_fragment) {
    private lateinit var database: FirebaseDatabase
    private lateinit var recyclerViewSaved: RecyclerView
    private lateinit var databaseReferenceItems: DatabaseReference
    private lateinit var databaseReferenceUsers: DatabaseReference
    private lateinit var adapterSaved: RecyclerViewAdapterSaved
    private lateinit var gestureDetector: GestureDetector

    private var _binding: SavedFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = SavedFragmentBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReferenceItems = database.getReference("items")

        //TODO: replace with actual current user
        databaseReferenceUsers = database.getReference("users").child("cathrinsc")

        recyclerViewSaved = binding.recyclerViewSaved
        recyclerViewSaved.layoutManager = LinearLayoutManager(context)


        databaseReferenceUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = dataSnapshot.child("savedItems").childrenCount.toInt()

                adapterSaved = RecyclerViewAdapterSaved(count)
                recyclerViewSaved.adapter = adapterSaved
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })




        binding.backSaved.setOnClickListener{
            val marketFragment = MarketFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, marketFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.clearSaved.setOnClickListener{
            databaseReferenceUsers.child("savedItems").removeValue()
        }

        return binding.root
    }
}
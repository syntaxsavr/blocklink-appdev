package com.groupthree.blocklink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.groupthree.blocklink.Utils.Event
import com.groupthree.blocklink.Utils.Location
import com.groupthree.blocklink.databinding.FragmentCreateEventBinding
import com.groupthree.blocklink.databinding.FragmentEventsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CreateEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateEventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCreateEventBinding
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("events")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        binding.btnCancelCreateEvent.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, EventsFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        binding.btnCreateEventPost.setOnClickListener{
            databaseReference.setValue(Event("Hubi", Location(0.0,0.0), "hallo"))
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, EventsFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding.root
    }
}
package com.groupthree.blocklink.Events

import GPSTracker
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.groupthree.blocklink.Events.Utils.Event
import com.groupthree.blocklink.Events.Utils.Location
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.FragmentCreateEventBinding

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
    lateinit var locfind: GPSTracker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("/")

        locfind = GPSTracker(requireContext(), requireActivity())
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
            if (binding.editTextEventName.text.toString().isEmpty()) {
                binding.editTextEventName.error = "Please enter a name"
                binding.editTextEventName.requestFocus()
                return@setOnClickListener
            }else if (binding.editTextTextMultiLine2.text.toString().isEmpty()) {
                binding.editTextTextMultiLine2.error = "Please enter a description"
                binding.editTextTextMultiLine2.requestFocus()
                return@setOnClickListener
            }
            val key1 = databaseReference.child(locfind.getPostalCode(requireContext()).toString()).push().key

            if (key1 != null) {

                // Check if username is set, if not set take email name as username
                var dname = ""
                if (FirebaseAuth.getInstance().currentUser?.displayName!!.isEmpty())
                    dname = extractUsernameFromEmail(FirebaseAuth.getInstance().currentUser?.email!!)
                else
                    dname = FirebaseAuth.getInstance().currentUser?.displayName.toString()

                // Save information into database
                databaseReference.child(locfind.getPostalCode(requireContext()).toString()).child(key1).setValue(Event(binding.editTextEventName.text.toString(), Location(locfind.getLongitudeRaw(), locfind.getLatitudeRaw()), binding.editTextTextMultiLine2.text.toString(), FirebaseAuth.getInstance().currentUser?.displayName.toString()))
            }
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, EventsFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding.root
    }

    /**
     * Function to extract the username out of an email if not set spcifically
     */
    fun extractUsernameFromEmail(email: String): String {
        val atIndex = email.indexOf("@")
        return if (atIndex != -1) {
            email.substring(0, atIndex)
        } else {
            // Handle the case when "@" is not found in the email (invalid email format)
            // In this case, you can return the entire email or an empty string, depending on your requirements.
            email
        }
    }
}
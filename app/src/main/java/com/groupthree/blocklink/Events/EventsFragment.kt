package com.groupthree.blocklink.Events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.groupthree.blocklink.Events.Utils.Event
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.FragmentEventsBinding

/**

A simple [Fragment] subclass.

Use the [EventsFragment.newInstance] factory method to

create an instance of this fragment.
 */
class EventsFragment : Fragment() {

    private val TAG = "EventsFragment"

    // The Firebase database instance
    private lateinit var database: FirebaseDatabase

    // The Firebase database reference
    private lateinit var databaseReference: DatabaseReference

    // The binding for the fragment
    private lateinit var binding: FragmentEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Firebase database instance
        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        // Initialize the Firebase database reference
        databaseReference = database.getReference("/")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for the fragment
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        binding.btnCreateEvent.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, CreateEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        // Get all events from the database
        getEvents()

        return binding.root
    }

    private fun getEvents() {
        // Create a list to store the events
        val events = mutableListOf<Event>()

        // Get the events from the database
        databaseReference.child("events").get().addOnSuccessListener { snapshot ->
            // If the snapshot exists
            if (snapshot.exists()) {
                // Iterate through the children of the snapshot
                for (data in snapshot.children) {
                    // Get the event data from the child
                    val event = data.getValue(Event::class.java)

                    // If the event data is not null
                    if (event != null) {
                        // Add the event to the list of events
                        events.add(event)
                    }
                }
            }

            // Log the number of events
            Log.d(TAG, "Events retrieved: ${events.size}")

            // Show the events to the user
            showEvents(events)
        }
    }

    private fun showEvents(events: List<Event>) {
        // Log the number of events
        Log.d(TAG, "Showing events: ${events.size}")

        // Get the scroll view from the binding
        val scrollView = binding.scrollView

        // Create a linear layout to hold the events
        val container = LinearLayout(requireContext())
        container.orientation = LinearLayout.VERTICAL

        // Add the linear layout to the scroll view
        scrollView.addView(container)

        // Iterate through the events
        for (event in events) {
            // Inflate the event layout
            val eventView = layoutInflater.inflate(R.layout.fragment_event, container, false)

            // Find views inside the inflated layout
            val textViewName = eventView.findViewById<TextView>(R.id.txteventName)
            val textViewDescription = eventView.findViewById<TextView>(R.id.txtDescription)
            val textViewUsername = eventView.findViewById<TextView>(R.id.txtUsername)

            // Set event details to the views
            textViewName.text = event.name
            textViewDescription.text = event.description
            if (!event.username.isEmpty()) {
                textViewUsername.text = event.username
            } else {
                textViewUsername.text = extractUsernameFromEmail(FirebaseAuth.getInstance().currentUser!!.email.toString())
            }


            // Add the inflated view (eventView) to the container
            container.addView(eventView)
        }
    }
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
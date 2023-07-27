package com.groupthree.blocklink.Events

import GPSTracker
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
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

    // Location Finder Class
    private lateinit var locfind: GPSTracker

    // Zipcode
    private lateinit var zipcode: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Firebase database instance
        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")

        // Initialize the Firebase database reference
        databaseReference = database.getReference("/")

        // Initialize GPS Tracker
        locfind = GPSTracker(requireContext(), requireActivity())

        zipcode = locfind.getPostalCode(requireContext()).toString()
        if (zipcode.equals("null")) zipcode = "9020"
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
        databaseReference.child(zipcode).get().addOnSuccessListener { snapshot ->
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
        val reversedevents = events.reversed()

        // Iterate through the events
        for (event in reversedevents) {
            // Inflate the event layout
            val eventView = layoutInflater.inflate(R.layout.fragment_event, container, false)

            // Find views inside the inflated layout
            val textViewName = eventView.findViewById<TextView>(R.id.txteventName)
            val textViewDescription = eventView.findViewById<TextView>(R.id.txtDescription)
            val textViewUsername = eventView.findViewById<TextView>(R.id.txtUsername)
            val buttonOpenInMaps = eventView.findViewById<Button>(R.id.btnOpenInMaps)
            val buttonExpand = eventView.findViewById<Button>(R.id.btnExpand)

            // Set event details to the views
            textViewName.text = event.name
            textViewDescription.text = event.description

            textViewUsername.text = event.username

            textViewDescription.visibility = View.GONE
            buttonOpenInMaps.visibility = View.GONE

            buttonExpand.setOnClickListener{
                if (buttonOpenInMaps.visibility == View.VISIBLE){
                    buttonOpenInMaps.visibility = View.GONE
                    textViewDescription.visibility = View.GONE
                    buttonExpand.text = "+"
                }else{
                    textViewDescription.visibility = View.VISIBLE
                    buttonExpand.text = "-"

                    if (event.location.latitude != 0.0 && event.location.longitude != 0.0){
                        buttonOpenInMaps.visibility = View.VISIBLE
                        buttonOpenInMaps.setOnClickListener{
                            openGoogleMaps(requireContext(), event.location.latitude, event.location.longitude)
                        }
                    }
                }
            }
            val framecontainer = FrameLayout(requireContext())
            framecontainer.addView(eventView)

            // Add the inflated view (eventView) to the container
            container.addView(framecontainer)
        }
        if (locfind.isGPSEnabled && locfind.isGPSTrackingEnabled)
            binding.textViewLocation.text = locfind.getLocality(view?.context).toString()
        else
            binding.textViewLocation.text = "Could not fetch location"

    }

    /**
     * Function that starts GMaps with a given location
     */
    fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
        // Create a Uri from the latitude and longitude
        val gmmIntentUri = Uri.parse("geo:0,0?q=$latitude,$longitude")

        // Create an Intent from the Uri
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        // Set the package name to Google Maps
        mapIntent.setPackage("com.google.android.apps.maps")

        // Start the activity
        context.startActivity(mapIntent)
    }

}
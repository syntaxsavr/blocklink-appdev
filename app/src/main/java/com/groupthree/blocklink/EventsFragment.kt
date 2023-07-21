package com.groupthree.blocklink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.FirebaseDatabase
import com.groupthree.blocklink.Market.UI.NewItemFragment
import com.groupthree.blocklink.Utils.Event
import com.groupthree.blocklink.Utils.Location
import com.groupthree.blocklink.Utils.User
import com.groupthree.blocklink.databinding.FragmentEventsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventsFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showEvents() {
        var scrollView: ScrollView = view?.findViewById(R.id.scrollView) as ScrollView
        var container: LinearLayout = LinearLayout(this.context)
        container.setOrientation(LinearLayout.VERTICAL)
        scrollView.addView(container);
        val x: kotlin.Double = 0.0
        val y: kotlin.Double = 0.0
        var events: List<Event> = getEventsForUser(User("test", Location(x,y)))

        for (event in events) {
            var eventView: View = layoutInflater.inflate(R.layout.fragment_event, container, false)
            container.addView(eventView)
        }
    }
        private fun getEventsForUser(user: User): List<Event> {
            // Get the user's location.
            val userLocation = user.location

            // Get all events from the database.
            val events = FirebaseDatabase.getInstance().getReference("events").get().getResult() as List<Event>

            return events
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        binding.btnCreateEvent.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, CreateEventFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding.root
    }
}
package com.groupthree.blocklink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import com.google.firebase.database.FirebaseDatabase
import com.groupthree.blocklink.Utils.Event
import com.groupthree.blocklink.Utils.Location
import com.groupthree.blocklink.Utils.User

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
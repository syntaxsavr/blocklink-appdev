package com.groupthree.blocklink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    fun expandFragment(view: View){
        val description = view.findViewById<TextView>(R.id.txtDescription)
        val btnmaps = view.findViewById<Button>(R.id.btnOpenInMaps)

        if (description.visibility == View.GONE){
            description.visibility = View.VISIBLE
            btnmaps.visibility = View.VISIBLE
        } else {
            description.visibility = View.GONE
            btnmaps.visibility = View.GONE
        }
    }
}
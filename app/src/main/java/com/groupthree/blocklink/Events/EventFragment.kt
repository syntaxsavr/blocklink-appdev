package com.groupthree.blocklink.Events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.FragmentEventBinding
import com.groupthree.blocklink.databinding.FragmentEventsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {
    lateinit var binding: FragmentEventBinding
    private var name: String? = null
    private var description: String? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the name, description, and username from the arguments
        name = arguments?.getString("name")
        description = arguments?.getString("description")
        username = arguments?.getString("username")

        // Set the text of the views
        binding.txteventName.text = name
        binding.txtDescription.text = description
        binding.txtUsername.text = username
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val description = binding.txtDescription
        val btnmaps = binding.btnOpenInMaps
        val btnExpand = binding.btnExpand

        // Set the OnClickListener for btnExpand
        btnExpand.setOnClickListener {
            // Handle the logic for expanding/collapsing txtDescription here
            if (binding.txtDescription.visibility == View.GONE) {
                binding.txtDescription.visibility = View.VISIBLE
            } else {
                binding.txtDescription.visibility = View.GONE
            }
        }
        return binding.root
    }

    /**
     * The companion object for the EventFragment class
     */
    companion object {

        // The method to create a new instance of the EventFragment class
        fun newInstance(name: String, description: String, username: String): EventFragment {
            val fragment = EventFragment()

            // Bundle the arguments
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("description", description)
            bundle.putString("username", username)

            // Set the arguments for the fragment
            fragment.arguments = bundle

            return fragment
        }
    }

}
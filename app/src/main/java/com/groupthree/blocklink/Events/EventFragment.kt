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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val description = binding.txtDescription
        val btnmaps = binding.btnOpenInMaps

        binding.btnExpand.setOnClickListener {
            if (description.visibility == View.GONE) {
                Log.d("TAG", "Setting visibility to VISIBLE")
                description.visibility = View.VISIBLE
                btnmaps.visibility = View.VISIBLE
            } else {
                Log.d("TAG", "Setting visibility to GONE")
                description.visibility = View.GONE
                btnmaps.visibility = View.GONE
            }
        }
    }

}
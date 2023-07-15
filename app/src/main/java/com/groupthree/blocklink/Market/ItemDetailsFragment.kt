package com.groupthree.blocklink.Market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.ItemdetailsFragmentBinding
import com.groupthree.blocklink.databinding.MarketFragmentBinding

class ItemDetailsFragment : Fragment(R.layout.itemdetails_fragment) {

    private var _binding: ItemdetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ItemdetailsFragmentBinding.inflate(inflater, container, false)




        binding.backDetails.setOnClickListener{
            val marketFragment = MarketFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,marketFragment )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return binding.root
    }
}
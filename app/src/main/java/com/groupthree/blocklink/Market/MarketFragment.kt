package com.groupthree.blocklink.Market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.MarketFragmentBinding

class MarketFragment : Fragment() {

    private lateinit var binding: MarketFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.market_fragment, container, false)
    }
}
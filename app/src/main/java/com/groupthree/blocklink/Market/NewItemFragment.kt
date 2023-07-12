package com.groupthree.blocklink.Market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.groupthree.blocklink.R

class NewItemFragment: Fragment(R.layout.newitem_fragment) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.newitem_fragment, container, false)






        return rootView
    }

}
package com.groupthree.blocklink.Market

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel: ViewModel() {
    private var name = MutableLiveData<String>()
    private var desciption = MutableLiveData<String>()
    private var price = MutableLiveData<Double>()

}
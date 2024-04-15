package com.home.brewerhelper.ui.carbonation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarbonationViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is carbcalc Fragment"
    }
    val text: LiveData<String> = _text
}
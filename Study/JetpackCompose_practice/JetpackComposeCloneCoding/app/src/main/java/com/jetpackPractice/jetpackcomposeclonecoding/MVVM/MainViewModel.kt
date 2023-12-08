package com.jetpackPractice.jetpackcomposeclonecoding.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _loginData = MutableLiveData<String>()
    val loginData : LiveData<String> = _loginData

    init{
        _loginData.value = "Hello Android"
    }

    fun buttonClick(str : String?){
        _loginData.value = str
    }

}
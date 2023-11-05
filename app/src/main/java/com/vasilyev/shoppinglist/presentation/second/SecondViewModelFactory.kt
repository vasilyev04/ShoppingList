package com.vasilyev.shoppinglist.presentation.second

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SecondViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SecondViewModel::class.java)){
            return SecondViewModel(application) as T
        }else{
            throw RuntimeException("Unknown view model")
        }
    }
}
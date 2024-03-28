package com.vasilyev.shoppinglist.presentation

import android.app.Application
import com.vasilyev.shoppinglist.di.components.DaggerApplicationComponent
class App: Application() {
    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}
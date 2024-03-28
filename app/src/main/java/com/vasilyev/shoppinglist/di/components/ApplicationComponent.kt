package com.vasilyev.shoppinglist.di.components

import android.app.Application
import com.vasilyev.shoppinglist.di.modules.DataModule
import com.vasilyev.shoppinglist.di.modules.ViewModelModule
import com.vasilyev.shoppinglist.di.scopes.ApplicationScope
import com.vasilyev.shoppinglist.presentation.main.MainActivity
import com.vasilyev.shoppinglist.presentation.second.SecondActivity
import com.vasilyev.shoppinglist.presentation.second.SecondFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)

@ApplicationScope
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(secondFragment: SecondFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
package com.vasilyev.shoppinglist.di.modules

import androidx.lifecycle.ViewModel
import com.vasilyev.shoppinglist.di.annotations.ViewModelKey
import com.vasilyev.shoppinglist.presentation.main.MainViewModel
import com.vasilyev.shoppinglist.presentation.second.SecondViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecondViewModel::class)
    fun provideSecondViewModel(impl: SecondViewModel): ViewModel
}
package com.vasilyev.shoppinglist.di.modules

import android.app.Application
import com.vasilyev.shoppinglist.data.repository.ShopListRepositoryImpl
import com.vasilyev.shoppinglist.data.local.LocalDataSource
import com.vasilyev.shoppinglist.data.local.LocalDataSourceImpl
import com.vasilyev.shoppinglist.data.local.room.AppDatabase
import com.vasilyev.shoppinglist.di.scopes.ApplicationScope
import com.vasilyev.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepositoryImpl(impl: ShopListRepositoryImpl): ShopListRepository

    @Binds
    @ApplicationScope
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    companion object{

        @Provides
        @ApplicationScope
        fun provideAppDatabase(application: Application): AppDatabase {
            return AppDatabase.getInstance(application)
        }
    }
}
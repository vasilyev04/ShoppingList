package com.vasilyev.shoppinglist.data.local

import android.adservices.adid.AdId
import androidx.lifecycle.LiveData
import com.vasilyev.shoppinglist.data.local.room.AppDatabase
import com.vasilyev.shoppinglist.data.local.room.ShopItemEntity
import com.vasilyev.shoppinglist.domain.ShopItem

class LocalDataSource(appDatabase: AppDatabase) {
    private val shopListDao = appDatabase.shopListDao()
    fun getShopItemsList(): LiveData<List<ShopItemEntity>> {
        return shopListDao.getShopItemsList()
    }

    suspend fun getShopItem(id: Int): ShopItemEntity {
        return shopListDao.getShopItem(id)
    }

    suspend fun addShopItem(shopItemEntity: ShopItemEntity){
        shopListDao.addShopItem(shopItemEntity)
    }

    suspend fun deleteShopItem(shopItemId: Int){
        shopListDao.deleteShopItem(shopItemId)
    }
}
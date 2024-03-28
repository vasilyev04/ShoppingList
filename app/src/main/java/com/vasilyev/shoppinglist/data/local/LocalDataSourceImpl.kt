package com.vasilyev.shoppinglist.data.local

import androidx.lifecycle.LiveData
import com.vasilyev.shoppinglist.data.local.room.AppDatabase
import com.vasilyev.shoppinglist.data.local.model.ShopItemDbModel
import com.vasilyev.shoppinglist.data.local.room.ShopListDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    appDatabase: AppDatabase
): LocalDataSource {

    private val shopListDao: ShopListDao = appDatabase.shopListDao()

    override fun getShopItemsList(): LiveData<List<ShopItemDbModel>> {
        return shopListDao.getShopItemsList()
    }

    override suspend fun getShopItem(id: Int): ShopItemDbModel {
        return shopListDao.getShopItem(id)
    }

    override suspend fun addShopItem(shopItemDbModel: ShopItemDbModel) {
        shopListDao.addShopItem(shopItemDbModel)
    }

    override suspend fun deleteShopItem(shopItemId: Int) {
        shopListDao.deleteShopItem(shopItemId)
    }
}
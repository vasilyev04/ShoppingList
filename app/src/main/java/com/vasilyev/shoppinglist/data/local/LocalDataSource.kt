package com.vasilyev.shoppinglist.data.local

import androidx.lifecycle.LiveData
import com.vasilyev.shoppinglist.data.local.model.ShopItemDbModel

interface LocalDataSource {
    fun getShopItemsList(): LiveData<List<ShopItemDbModel>>

    suspend fun getShopItem(id: Int): ShopItemDbModel

    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    suspend fun deleteShopItem(shopItemId: Int)
}
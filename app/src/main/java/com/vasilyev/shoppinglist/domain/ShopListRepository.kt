package com.vasilyev.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun deleteShopItem(shopItem: ShopItem)
}
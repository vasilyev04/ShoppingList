package com.vasilyev.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vasilyev.shoppinglist.data.local.LocalDataSource
import com.vasilyev.shoppinglist.data.local.room.AppDatabase
import com.vasilyev.shoppinglist.data.local.room.ShopListMapper
import com.vasilyev.shoppinglist.domain.ShopItem
import com.vasilyev.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application): ShopListRepository {
    private val localDataSource = LocalDataSource(AppDatabase.getInstance(application))
    private val mapper = ShopListMapper()

    /*init {
        for (i in 0..1){
            addShopItem(ShopItem(
                id = i,
                name = "Name $i",
                count = i,
                enabled = Random.nextBoolean()
            ))
        }
    }*/

    override suspend fun addShopItem(shopItem: ShopItem) {
        val shopItemEntity = mapper.mapModelToEntity(shopItem)
        localDataSource.addShopItem(shopItemEntity)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        val shopItemEntity = mapper.mapModelToEntity(shopItem)
        localDataSource.addShopItem(shopItemEntity)
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val shopItemEntity = localDataSource.getShopItem(id)
        return mapper.mapEntityToModel(shopItemEntity)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        val shopItemsEntityList = localDataSource.getShopItemsList()

        return shopItemsEntityList.map { list ->
            mapper.mapListEntityToListModel(list)
        }
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        localDataSource.deleteShopItem(shopItem.id)
    }
}
package com.vasilyev.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vasilyev.shoppinglist.data.local.LocalDataSource
import com.vasilyev.shoppinglist.data.mapper.ShopListMapper
import com.vasilyev.shoppinglist.domain.ShopItem
import com.vasilyev.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val mapper: ShopListMapper
): ShopListRepository {
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
        val shopItemEntity = mapper.mapEntityToDbModel(shopItem)
        localDataSource.addShopItem(shopItemEntity)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        val shopItemEntity = mapper.mapEntityToDbModel(shopItem)
        localDataSource.addShopItem(shopItemEntity)
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val shopItemEntity = localDataSource.getShopItem(id)
        return mapper.mapDbModelToEntity(shopItemEntity)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        val shopItemsEntityList = localDataSource.getShopItemsList()

        return shopItemsEntityList.map { list ->
            mapper.mapListDbModelToEntity(list)
        }
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        localDataSource.deleteShopItem(shopItem.id)
    }
}
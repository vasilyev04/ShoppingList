package com.vasilyev.shoppinglist.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasilyev.shoppinglist.domain.ShopItem

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_items")
    fun getShopItemsList(): LiveData<List<ShopItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemEntity: ShopItemEntity)

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId")
    suspend fun getShopItem(shopItemId: Int): ShopItemEntity

    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)
}
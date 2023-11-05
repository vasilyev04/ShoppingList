package com.vasilyev.shoppinglist.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vasilyev.shoppinglist.domain.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)
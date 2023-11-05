package com.vasilyev.shoppinglist.data.local.room

import com.vasilyev.shoppinglist.domain.ShopItem

class ShopListMapper {
    fun mapEntityToModel(entity: ShopItemEntity): ShopItem{
        return ShopItem(
            id = entity.id,
            name = entity.name,
            count = entity.count,
            enabled = entity.enabled
        )
    }

    fun mapModelToEntity(shopItem: ShopItem): ShopItemEntity{
        return ShopItemEntity(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapListEntityToListModel(list: List<ShopItemEntity>): List<ShopItem>{
        return list.map {
           mapEntityToModel(it)
        }
    }
}
package com.vasilyev.shoppinglist.data.mapper

import com.vasilyev.shoppinglist.data.local.model.ShopItemDbModel
import com.vasilyev.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor(){
    fun mapDbModelToEntity(entity: ShopItemDbModel): ShopItem{
        return ShopItem(
            id = entity.id,
            name = entity.name,
            count = entity.count,
            enabled = entity.enabled
        )
    }

    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapListDbModelToEntity(list: List<ShopItemDbModel>): List<ShopItem>{
        return list.map {
           mapDbModelToEntity(it)
        }
    }
}



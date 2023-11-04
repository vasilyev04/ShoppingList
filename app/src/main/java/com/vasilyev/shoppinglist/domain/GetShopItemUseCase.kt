package com.vasilyev.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository){
    fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}
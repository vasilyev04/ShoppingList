package com.vasilyev.shoppinglist.presentation.main


import ShopListRepositoryImpl
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vasilyev.shoppinglist.domain.AddShopItemUseCase
import com.vasilyev.shoppinglist.domain.DeleteShopItemUseCase
import com.vasilyev.shoppinglist.domain.EditShopItemUseCase
import com.vasilyev.shoppinglist.domain.GetShopListUseCase
import com.vasilyev.shoppinglist.domain.ShopItem

class MainViewModel: ViewModel() {
    private val shopListRepository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopListRepository)

    val list: LiveData<List<ShopItem>> = getShopListUseCase.getShopList()

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }

    fun addToList(shopItem: ShopItem){
        addShopItemUseCase.addShopItem(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }
}
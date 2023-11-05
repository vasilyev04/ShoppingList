package com.vasilyev.shoppinglist.presentation.main


import android.app.Application
import com.vasilyev.shoppinglist.data.ShopListRepositoryImpl
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.shoppinglist.domain.AddShopItemUseCase
import com.vasilyev.shoppinglist.domain.DeleteShopItemUseCase
import com.vasilyev.shoppinglist.domain.EditShopItemUseCase
import com.vasilyev.shoppinglist.domain.GetShopListUseCase
import com.vasilyev.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {
    private val shopListRepository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopListRepository)

    val list: LiveData<List<ShopItem>> = getShopListUseCase.getShopList()

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch{
            editShopItemUseCase.editShopItem(newItem)
        }
    }

    fun addToList(shopItem: ShopItem){
        viewModelScope.launch{
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun deleteShopItem(shopItem: ShopItem){
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }
}
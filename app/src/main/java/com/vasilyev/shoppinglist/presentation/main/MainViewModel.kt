package com.vasilyev.shoppinglist.presentation.main


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.shoppinglist.domain.AddShopItemUseCase
import com.vasilyev.shoppinglist.domain.DeleteShopItemUseCase
import com.vasilyev.shoppinglist.domain.EditShopItemUseCase
import com.vasilyev.shoppinglist.domain.GetShopListUseCase
import com.vasilyev.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getShopListUseCase: GetShopListUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase
): ViewModel() {

    init {
        Log.d("viewModelTest", "$this")
    }

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
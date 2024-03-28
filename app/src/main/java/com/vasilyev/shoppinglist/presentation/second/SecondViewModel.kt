package com.vasilyev.shoppinglist.presentation.second

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.shoppinglist.domain.AddShopItemUseCase
import com.vasilyev.shoppinglist.domain.EditShopItemUseCase
import com.vasilyev.shoppinglist.domain.GetShopItemUseCase
import com.vasilyev.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val addShopItemUseCase: AddShopItemUseCase,
    private val getShopItemUseCase: GetShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
): ViewModel() {

    init {
        Log.d("viewModelTest", "$this")
    }

    private val _errorName: MutableLiveData<Boolean> = MutableLiveData()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorCount: MutableLiveData<Boolean> = MutableLiveData()
    val errorCount: LiveData<Boolean>
        get() = _errorCount

    private val _shopItem: MutableLiveData<ShopItem?> = MutableLiveData()
    val shopItem: LiveData<ShopItem?>
        get() = _shopItem

    private val _shouldCloseScreen: MutableLiveData<Unit> = MutableLiveData()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun addShopItem(name: String?, count: String?){
        val validate = validateFields(parseInputName(name), parseInputCount(count))
        if (validate){
            val item = ShopItem(
                id = ShopItem.UNDEFINED_ID,
                name = parseInputName(name),
                count = parseInputCount(count),
                enabled = true
            )
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(item)
            }

            closeScreen()
        }
    }

    fun getShopItem(id: Int){
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(id)
            _shopItem.value = item
        }
    }

    fun editShopItem(name: String?, count: String?){
        val validate = validateFields(parseInputName(name), parseInputCount(count))
        if (validate){
            _shopItem.value?.let {
                val item = it.copy(
                    name = parseInputName(name),
                    count = parseInputCount(count)
                )

                viewModelScope.launch {
                    editShopItemUseCase.editShopItem(item)
                }

                closeScreen()
            }
        }
    }

    private fun parseInputCount(inputCount: String?): Int{
        return try{
            inputCount?.trim()?.toInt() ?: 0
        }catch (e: NumberFormatException){
            0
        }
    }

    private fun parseInputName(inputName: String?): String{
        return inputName?.trim() ?: ""
    }

    private fun validateFields(name: String, count: Int): Boolean{
        var validated = true
        _errorName.value = false
        _errorCount.value = false

        if (name.isBlank()){
            validated = false
            _errorName.value = true
        }

        if (count == 0){
            validated = false
            _errorCount.value = true
        }

        return validated
    }

    private fun closeScreen(){
        _shouldCloseScreen.value = Unit
    }

    fun resetNameError(){
        _errorName.value = false
    }

    fun resetCountError(){
        _errorCount.value = false
    }
}
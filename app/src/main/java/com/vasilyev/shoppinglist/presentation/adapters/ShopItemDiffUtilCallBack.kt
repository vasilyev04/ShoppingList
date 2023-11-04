package com.vasilyev.shoppinglist.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.vasilyev.shoppinglist.domain.ShopItem

class ShopItemDiffUtilCallBack: DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}
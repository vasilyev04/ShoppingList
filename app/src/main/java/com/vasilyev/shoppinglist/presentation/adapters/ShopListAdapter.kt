package com.vasilyev.shoppinglist.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vasilyev.shoppinglist.R
import com.vasilyev.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter: ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffUtilCallBack()){
    companion object{
        const val ENABLED_TYPE = 0
        const val DISABLED_TYPE = 1
        const val MAX_POOL_SIZE = 20
    }

    private var count = 0

    var onClickListener: ((ShopItem) -> Unit)? = null
    var onLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        Log.d("onCreateViewHolder", "count:${++count}")
        val layout = when(viewType){
            ENABLED_TYPE -> R.layout.item_shop_enabled
            DISABLED_TYPE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return ShopListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        //Log.d("onBindViewHolder", "count:${++count} ")
        val shopItem = getItem(position)
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(shopItem)
        }
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem: ShopItem = getItem(position)
        return if(shopItem.enabled){
            return ENABLED_TYPE
        }else{
            DISABLED_TYPE
        }
    }

    /*var shopList: List<ShopItem> = listOf()
        set(value){
            val callback = ShopListDiffUtilCallBack(shopList, value)
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(this)
            field = value
        }*/
}
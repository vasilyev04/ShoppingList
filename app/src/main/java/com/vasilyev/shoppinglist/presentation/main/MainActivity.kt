package com.vasilyev.shoppinglist.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vasilyev.shoppinglist.presentation.second.SecondFragment
import com.vasilyev.shoppinglist.R
import com.vasilyev.shoppinglist.databinding.ActivityMainBinding
import com.vasilyev.shoppinglist.presentation.adapters.ShopListAdapter
import com.vasilyev.shoppinglist.presentation.second.SecondActivity

class MainActivity : AppCompatActivity(), SecondFragment.OnEditingFinishedListener {
    private val viewModel: MainViewModel by viewModels(){
        MainViewModelFactory(application)
    }
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val shopListAdapter: ShopListAdapter by lazy {
        ShopListAdapter()
    }


    private fun isLandScapeOrientation(): Boolean{
        binding.fragmentContainerView?.let {
            return true
        }
        return false
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setOrientation()

        setupRecyclerView()

        viewModel.list.observe(this){
            Log.d("MainActivityTest", "onCreate: $it")
            shopListAdapter.submitList(it)
        }

        binding.FAB.setOnClickListener {
            if(isLandScapeOrientation()){
                val fragment = SecondFragment.newFragmentAddItem()
                launchFragment(fragment)
            }else{
                val intent = SecondActivity.newIntentAddItem(this@MainActivity)
                startActivity(intent)
            }
        }
    }

    private fun setupRecyclerView(){
        with(binding.recyclerView){
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED_TYPE,
                ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_TYPE,
                ShopListAdapter.MAX_POOL_SIZE)

            setupRecyclerViewListeners()
            setupSwipeCallback()
        }
    }

    private fun setupRecyclerViewListeners(){
        with(shopListAdapter){
            onLongClickListener = {
                viewModel.changeEnableState(it)
            }

            onClickListener = {
                if (isLandScapeOrientation()){
                    val fragment = SecondFragment
                        .newFragmentEditItem(shopItemId = it.id)
                    launchFragment(fragment)
                }else{
                    val intent = SecondActivity.newIntentEditItem(
                        this@MainActivity,
                        it.id
                    )
                    startActivity(intent)
                }
            }
        }
    }


    private fun setupSwipeCallback(){
        val callback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onEditFinished() {
        onBackPressed()
    }

    /*private fun showList(list: List<ShopItem>){
        binding.llShopList.removeAllViews()
        for (i in list.indices){
            val shopItem = list[i]
            val viewId = if (shopItem.enabled){
                R.layout.item_shop_enabled
            }else{
                R.layout.item_shop_disabled
            }
            val view = LayoutInflater.from(this).inflate(viewId, binding.llShopList, false)
            view.setOnLongClickListener{
                viewModel.changeEnableState(shopItem)
                true
            }
            val tvName = view.findViewById<TextView>(R.id.name)
            val tvCount = view.findViewById<TextView>(R.id.count)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            binding.llShopList.addView(view)
        }
    }*/
}
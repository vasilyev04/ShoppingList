package com.vasilyev.shoppinglist.presentation.second

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.vasilyev.shoppinglist.R
import com.vasilyev.shoppinglist.databinding.ActivitySecondBinding
import com.vasilyev.shoppinglist.domain.ShopItem

class SecondActivity : AppCompatActivity(), SecondFragment.OnEditingFinishedListener {
    private var screenMode: String = UNDEFINED_MODE
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    private val binding: ActivitySecondBinding by lazy{
        ActivitySecondBinding.inflate(layoutInflater)
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val UNDEFINED_MODE = "undefined_mode"

        fun newIntentAddItem(context: Context): Intent{
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent{
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }

    private fun parseIntent(intent: Intent){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode!= MODE_EDIT) {
            throw RuntimeException("Param screen mode is incorrect: $mode")
        }
        screenMode = mode

        if (mode == MODE_EDIT){
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        parseIntent(intent)
        if (savedInstanceState == null) {startFragmentInRightMode()}
    }

    private fun startFragmentInRightMode() {
        val fragment: Fragment = when (screenMode) {
            MODE_EDIT -> SecondFragment
                .newFragmentEditItem(shopItemId = shopItemId)
            MODE_ADD -> SecondFragment
                .newFragmentAddItem()
            else -> throw RuntimeException("Param screen mode is absent")
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEditFinished() {
        onBackPressed()
    }
}
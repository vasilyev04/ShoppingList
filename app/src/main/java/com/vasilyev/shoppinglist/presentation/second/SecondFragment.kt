package com.vasilyev.shoppinglist.presentation.second

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vasilyev.shoppinglist.databinding.SecondFragmentBinding
import com.vasilyev.shoppinglist.domain.ShopItem
import com.vasilyev.shoppinglist.domain.ShopItem.Companion.UNDEFINED_ID

class SecondFragment : Fragment() {
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private var screenMode: String = UNDEFINED_MODE
    private var shopItemId: Int = UNDEFINED_ID
    private val viewModel: SecondViewModel by viewModels(){
        SecondViewModelFactory(requireActivity().application)
    }
    private val binding: SecondFragmentBinding by lazy{
        SecondFragmentBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        }else{
            throw RuntimeException("onEditingFinishedListener listener is not realised")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SecondFragment", "onCreate")
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLaunchMode()
        addTextWatchers()
        observeViewModel()
    }


    private fun parseArgs(){
        val args = requireArguments()
        if(!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        screenMode = args.getString(SCREEN_MODE, UNDEFINED_MODE)
        if (screenMode == UNDEFINED_MODE){
            throw RuntimeException("Param screen mode is incorrect: $screenMode")
        }

        if  (screenMode == MODE_EDIT){
            if (!args.containsKey(SHOP_ITEM_ID)){
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }


    companion object{
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val UNDEFINED_MODE = "undefined_mode"

        fun newFragmentAddItem(): SecondFragment{
            val bundle = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
            val fragment = SecondFragment().apply {
                arguments = bundle
            }

            return fragment
        }

        fun newFragmentEditItem(shopItemId: Int): SecondFragment{
            val bundle = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
            }
            val fragment = SecondFragment().apply {
                arguments = bundle
            }

            return fragment
        }
    }

    private fun observeViewModel(){
        viewModel.errorName.observe(viewLifecycleOwner){
            val message = if (it){
                "Некорректное имя"
            }else{
                null
            }
            binding.titleName.error = message
        }

        viewModel.errorCount.observe(viewLifecycleOwner){
            val message = if (it){
                "Некорректное имя"
            }else{
                null
            }
            binding.titleCount.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            onEditingFinishedListener.onEditFinished()
        }
    }


    private fun setLaunchMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }
    private fun addTextWatchers(){
        binding.name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetNameError()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

        binding.count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetCountError()
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })
    }

    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner){
            setTextInEditText(it as ShopItem)
        }

        binding.buttonSave.setOnClickListener {
            editShopItem()
        }
    }

    private fun launchAddMode(){
        binding.buttonSave.setOnClickListener {
            addShopItem()
        }
    }

    private fun addShopItem(){
        val name = binding.name.text.toString()
        val count = binding.count.text.toString()

        viewModel.addShopItem(name, count)
    }

    private fun editShopItem(){
        val name = binding.name.text.toString()
        val count = binding.count.text.toString()

        viewModel.editShopItem(name, count)
    }



    private fun setTextInEditText(shopItem: ShopItem){
        binding.name.setText(shopItem.name)
        binding.count.setText(shopItem.count.toString())
    }


    interface OnEditingFinishedListener{
        fun onEditFinished()
    }
}
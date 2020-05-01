package com.monstercode.campushub.itemlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.monstercode.campushub.R
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.ItemListBinding
import com.monstercode.campushub.repository.ItemRepository

class ItemListFragment : Fragment() {

    private lateinit var itemListViewModel: ItemListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: ItemListBinding = DataBindingUtil.inflate(
            inflater, R.layout.item_list, container, false)

        itemListViewModel = getItemListViewModel()
        binding.itemListViewModel = itemListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.itemList.adapter = ItemListAdapter(itemListClickListener)

        return binding.root
    }

    /**
     * Create and return an instance of ListViewModel
     */
    private fun getItemListViewModel(): ItemListViewModel {
        val activity = requireNotNull(activity)
        val database = getDatabase(activity)
        val repository = ItemRepository(database.itemDao)
        return ViewModelProviders.of(activity, ItemListViewModel.FACTORY(repository))
            .get(ItemListViewModel::class.java)
    }

    private val itemListClickListener =
        ItemListAdapter.OnClickListener {
            Toast.makeText(context, "Item clicked: ${it.name}", Toast.LENGTH_LONG).show()
        }
}


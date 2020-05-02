package com.monstercode.campushub.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.monstercode.campushub.R
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.FragmentItemBinding
import com.monstercode.campushub.domain.Item
import com.monstercode.campushub.repository.ItemRepository
import org.jetbrains.anko.support.v4.toast

class ItemFragment : Fragment() {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_item, container, false)

        itemViewModel = getItemViewModel()
        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        itemViewModel.itemLiveData.observe(this) {
            setAppBarTitle(it)
        }

        binding.pictureList.adapter = PictureListAdapter(pictureClickListener)

        return binding.root
    }

    private val pictureClickListener = PictureListAdapter.OnClickListener {
        toast(it.pictureUrl)
    }

    /**
     * Create and return an instance of ListViewModel
     */
    private fun getItemViewModel() : ItemViewModel {
            val activity = requireNotNull(activity)
            val database = getDatabase(activity)
            val repository = ItemRepository(database.itemDao)
            val itemId = ItemFragmentArgs.fromBundle(arguments!!).itemId
            val itemViewModel = ViewModelProviders.of(activity, ItemViewModel.FACTORY(repository))
                .get(ItemViewModel::class.java)
            itemViewModel.setItemIdLiveData(itemId)
            return itemViewModel
    }

    private fun setAppBarTitle(item: Item) {
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.appbar_title, item.name)
    }

}
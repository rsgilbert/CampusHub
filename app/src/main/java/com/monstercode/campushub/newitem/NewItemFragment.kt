package com.monstercode.campushub.newitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.monstercode.campushub.R
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.FragmentNewItemBinding
import com.monstercode.campushub.newitem.NewItemViewModel.Companion.FACTORY
import com.monstercode.campushub.repository.ItemRepository
import org.jetbrains.anko.support.v4.toast

class NewItemFragment : Fragment() {

    private lateinit var newItemViewModel: NewItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_item, container, false
        )

        newItemViewModel = getNewItemViewModel()
        binding.newItemViewModel = newItemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addButton.setOnClickListener {
            val name = binding.newName.text.toString()
            val price = binding.newPrice.text.toString()

            if (name.isNotBlank() && price.isNotBlank()) {
                newItemViewModel.postNewItem(name, price.toInt())
            } else toast("Enter name and price")
        }

        newItemViewModel.navigateToItemItemIdLiveData.observe(this) {
            it?.let {
                val action = NewItemFragmentDirections.actionNewItemFragmentToItemFragment(it)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    /**
     * Create and return an instance of ListViewModel
     */
    private fun getNewItemViewModel(): NewItemViewModel {
        val database = getDatabase(activity!!)
        val repository = ItemRepository(database.itemDao)
        return ViewModelProviders.of(activity!!, FACTORY(repository))
            .get(NewItemViewModel::class.java)
    }

}
package com.monstercode.campushub.item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.monstercode.campushub.R
import com.monstercode.campushub.Update
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.FragmentItemBinding
import com.monstercode.campushub.dialog.UpdateNameDialogFragment
import com.monstercode.campushub.dialog.UpdateNameListener
import com.monstercode.campushub.domain.Item
import com.monstercode.campushub.repository.ItemRepository
import org.jetbrains.anko.support.v4.toast
import java.io.FileNotFoundException
import java.io.InputStream

class ItemFragment : Fragment(), UpdateNameListener {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_item, container, false
        )

        itemViewModel = getItemViewModel()
        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        itemViewModel.itemLiveData.observe(this) {
            setAppBarTitle(it)
        }

        itemViewModel.popToItemListLiveData.observe(this) {
            if (it == true) {
                val action = ItemFragmentDirections.actionItemFragmentToItemListFragment()
                findNavController().navigate(action)
                itemViewModel.popToItemListComplete()
            }
        }

        itemViewModel.navigateToUpdateLiveData.observe(this) {
            it?.let {
                when (it) {
                    Update.NAME -> startUpdateNameDialog()
                    Update.PRICE -> {
                        toast("Updating price")
                    }
                }
            }
        }

        binding.pictureList.adapter = PictureListAdapter(pictureClickListener)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun startUpdateNameDialog() {
        UpdateNameDialogFragment().show(childFragmentManager, "started name dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_delete -> {
                itemViewModel.deleteItem()
                true
            }
            R.id.option_new_picture -> {
                pickImage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val pictureClickListener = PictureListAdapter.OnClickListener {
        pickImage()
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            try {
                data?.let {
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(it.data!!)
                    inputStream?.let { stream ->
                        itemViewModel.uploadPicture(stream)
                    }

                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
    /**
     * Create and return an instance of ListViewModel
     */
    private fun getItemViewModel(): ItemViewModel {
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

    override fun onSave(name: String) {
        toast("Running save with $name")
        itemViewModel.saveName(name)
    }

    companion object {
        const val PICK_PHOTO = 1
    }
}
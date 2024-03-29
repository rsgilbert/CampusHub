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
import com.monstercode.campushub.dialog.*
import com.monstercode.campushub.domain.Item
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.PICK_PHOTO_REQUEST_CODE
import com.monstercode.campushub.util.startImagePicker
import java.io.FileNotFoundException
import java.io.InputStream

class ItemFragment : Fragment(), UpdateListener, DeleteListener {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_item, container, false)

        itemViewModel = getItemViewModel()
        binding.itemViewModel = itemViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        itemViewModel.itemLiveData.observe(this) {
            it?.let { setAppBarTitle(it) }
        }


        itemViewModel.navigateToUpdateLiveData.observe(this) {
            it?.let {
                when (it) {
                    Update.NAME -> startUpdateNameDialog()
                    Update.PRICE -> startUpdatePriceDialog()
                }
                itemViewModel.navigateToUpdateComplete()
            }
        }

        itemViewModel.navigateToPictureLiveData.observe(this) {
            it?.let { itemId ->
                val action = ItemFragmentDirections.actionItemFragmentToPictureFragment(itemId)
                findNavController().navigate(action)
                itemViewModel.navigateToPictureComplete()
            }
        }

        binding.pictureList.adapter = PictureListAdapter(pictureClickListener)
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.pictureList)

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_delete -> {
                startDeleteDialog()
                true
            }
            R.id.option_new_picture -> {
                startImagePicker()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val pictureClickListener = PictureListAdapter.OnClickListener {
        itemViewModel.navigateToPictureStart()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
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

    override fun onSaveName(name: String) {
        itemViewModel.updateName(name)
    }

    override fun onSavePrice(price: Int) {
        itemViewModel.updatePrice(price)
    }

    override fun onDelete(isDelete: Boolean) {
        if (isDelete) {
            val isDeleted = itemViewModel.deleteItem()
            if (isDeleted) {
                findNavController().popBackStack()
            }
        }
    }

}
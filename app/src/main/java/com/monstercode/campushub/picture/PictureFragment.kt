package com.monstercode.campushub.picture

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.monstercode.campushub.R
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.PictureViewpagerBinding
import com.monstercode.campushub.dialog.DeleteListener
import com.monstercode.campushub.dialog.startDeleteDialog
import com.monstercode.campushub.item.PictureListAdapter
import com.monstercode.campushub.repository.PictureRepository
import com.monstercode.campushub.util.startImagePicker
import org.jetbrains.anko.support.v4.toast

class PictureFragment : Fragment(), DeleteListener {

    private lateinit var pictureViewModel: PictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding: PictureViewpagerBinding =
            DataBindingUtil.inflate(inflater, R.layout.picture_viewpager, container, false)

        pictureViewModel = getPictureViewModel()
        binding.pictureViewModel = pictureViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewPager.adapter = PictureListAdapter(pictureClickListener)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.picture_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_delete -> startDeleteDialog()
            R.id.option_edit -> startImagePicker()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onDelete(isDelete: Boolean) {
        if (isDelete) {
            pictureViewModel.deletePicture()
        }
    }

    private val pictureClickListener = PictureListAdapter.OnClickListener {
//        itemViewModel.navigateToPictureStart(it._id)
        toast("hi")
    }

    /**
     * Create and return an instance of PictureViewModel
     */
    private fun getPictureViewModel(): PictureViewModel {
        val activity = requireNotNull(activity)
        val database = getDatabase(activity)
        val repository = PictureRepository(database.pictureDao)
        val itemId = PictureFragmentArgs.fromBundle(arguments!!).itemId
        val pictureViewModel = ViewModelProviders.of(activity, PictureViewModel.FACTORY(repository))
            .get(PictureViewModel::class.java)
        pictureViewModel.setItemIdLiveData(itemId)
        return pictureViewModel
    }
}
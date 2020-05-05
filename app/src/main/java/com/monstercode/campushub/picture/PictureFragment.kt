package com.monstercode.campushub.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.monstercode.campushub.R
import com.monstercode.campushub.database.getDatabase
import com.monstercode.campushub.databinding.FragmentPictureBinding
import com.monstercode.campushub.repository.PictureRepository

class PictureFragment : Fragment() {

    private lateinit var pictureViewModel: PictureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPictureBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_picture, container, false)
        hideActionBar()
        pictureViewModel = getPictureViewModel()
        binding.pictureViewModel = pictureViewModel

        return binding.root
    }

    private fun hideActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    /**
     * Create and return an instance of PictureViewModel
     */
    private fun getPictureViewModel(): PictureViewModel {
        val activity = requireNotNull(activity)
        val database = getDatabase(activity)
        val repository = PictureRepository(database.pictureDao)
        val pictureId = PictureFragmentArgs.fromBundle(arguments!!).pictureId
        val pictureViewModel = ViewModelProviders.of(activity, PictureViewModel.FACTORY(repository))
            .get(PictureViewModel::class.java)
        pictureViewModel.setPictureIdLiveData(pictureId)
        return pictureViewModel
    }
}
package com.monstercode.campushub.dialog

import androidx.lifecycle.ViewModel
import com.monstercode.campushub.repository.ItemRepository
import com.monstercode.campushub.util.singleArgViewModelFactory

class UpdateDialogViewModel(private val repository: ItemRepository) : ViewModel() {


    companion object {
        val FACTORY = singleArgViewModelFactory(::UpdateDialogViewModel)
    }
}
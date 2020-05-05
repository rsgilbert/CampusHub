package com.monstercode.campushub.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.monstercode.campushub.databinding.UpdatePriceDialogFragmentBinding

class UpdatePriceDialogFragment : UpdateDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = UpdatePriceDialogFragmentBinding.inflate(LayoutInflater.from(context))

            binding.btnSave.setOnClickListener {
                val price = binding.price.text.toString().toInt()
                listener.onSavePrice(price)
                dismiss()
            }

            builder.setView(binding.root)
            return builder.create()
        } ?: throw IllegalStateException("Activity can not be null")
    }

}


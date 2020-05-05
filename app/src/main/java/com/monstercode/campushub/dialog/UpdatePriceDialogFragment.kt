package com.monstercode.campushub.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import com.monstercode.campushub.databinding.UpdatePriceDialogFragmentBinding

class UpdatePriceDialogFragment : UpdateDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = UpdatePriceDialogFragmentBinding.inflate(LayoutInflater.from(context))

            binding.btnSave.setOnClickListener {
                val editable: Editable? = binding.price.text
                if (!editable.isNullOrBlank()) {
                    listener.onSavePrice(editable.toString().toInt())
                }
                dismiss()
            }

            builder.setView(binding.root)
            return builder.create()
        } ?: throw IllegalStateException("Activity can not be null")
    }

}


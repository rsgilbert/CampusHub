package com.monstercode.campushub.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.monstercode.campushub.databinding.DeleteDialogFragmentBinding

open class DeleteDialogFragment : DialogFragment() {

    lateinit var listener: DeleteListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as DeleteListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement UpdateListener")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DeleteDialogFragmentBinding.inflate(LayoutInflater.from(context))
            binding.btnYes.setOnClickListener {
                listener.onDelete(true)
                dismiss()
            }
            binding.btnNo.setOnClickListener {
                listener.onDelete(false)
                dismiss()
            }

            builder.setView(binding.root)
            return builder.create()
        } ?: throw IllegalStateException("Activity can not be null")
    }

}


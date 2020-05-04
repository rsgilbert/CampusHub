package com.monstercode.campushub.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.monstercode.campushub.databinding.UpdateNameDialogFragmentBinding
import timber.log.Timber

class UpdateNameDialogFragment : DialogFragment() {

    private lateinit var listener: UpdateNameListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as UpdateNameListener
        } catch (e: ClassCastException) {
            Timber.e("Error $e")
            throw ClassCastException("$context must implement UpdateNameListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = UpdateNameDialogFragmentBinding.inflate(LayoutInflater.from(context))

            binding.btnSave.setOnClickListener {
                val name = binding.name.text.toString()
                listener.onSave(name)
                dismiss()
            }
            builder.setView(binding.root)
            return builder.create()
        } ?: throw IllegalStateException("Activity can not be null")
    }


}

interface UpdateNameListener {
    fun onSave(name: String)
}
package com.monstercode.campushub.dialog

import android.content.Context
import androidx.fragment.app.DialogFragment

open class UpdateDialogFragment : DialogFragment() {

    lateinit var listener: UpdateListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as UpdateListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement UpdateListener")
        }
    }
}


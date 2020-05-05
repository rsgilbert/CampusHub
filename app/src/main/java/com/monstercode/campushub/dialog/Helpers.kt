package com.monstercode.campushub.dialog

import androidx.fragment.app.Fragment

fun Fragment.startUpdateNameDialog() {
    UpdateNameDialogFragment().show(childFragmentManager, "started name dialog")
}

fun Fragment.startUpdatePriceDialog() {
    UpdatePriceDialogFragment().show(childFragmentManager, "started price dialog")
}

fun Fragment.startDeleteDialog() {
    DeleteDialogFragment().show(childFragmentManager, "started delete dialog")
}
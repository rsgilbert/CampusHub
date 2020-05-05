package com.monstercode.campushub.util

import android.content.Context
import android.content.Intent
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.monstercode.campushub.R

const val PICK_PHOTO_REQUEST_CODE = 1

fun getSharedP(@NonNull context: Context)
        = context.getSharedPreferences(
    context.getString(R.string.preferences_filename), Context.MODE_PRIVATE
)

fun Fragment.startImagePicker() {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE)
}

private fun Fragment.hideActionBar() {
    (activity as AppCompatActivity).supportActionBar?.hide()
}


/**
 * Thrown when there was an error fetching data
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class RefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

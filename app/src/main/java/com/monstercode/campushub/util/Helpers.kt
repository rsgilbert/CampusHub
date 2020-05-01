package com.monstercode.campushub.util

import android.content.Context
import android.view.View
import androidx.annotation.NonNull
import com.monstercode.campushub.R

fun getSharedP(@NonNull context: Context)
        = context.getSharedPreferences(
    context.getString(R.string.preferences_filename), Context.MODE_PRIVATE
)

/**
 * Make view show click animation
 */
fun setClickableAnimation(context: Context, view: View) {
    val attrs = intArrayOf(R.attr.selectableItemBackground)
    val typedArray = context.obtainStyledAttributes(attrs)
    val backgroundResource = typedArray.getResourceId(0, 0)
    view.setBackgroundResource(backgroundResource)
    typedArray.recycle()
}

/**
 * Thrown when there was an error fetching data
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class RefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

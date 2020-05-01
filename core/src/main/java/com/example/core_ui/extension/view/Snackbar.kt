package com.example.core_ui.extension.view

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.core_ui.R
import com.google.android.material.snackbar.Snackbar

private val textColor = R.color.snackbarText
private val backgroundColor = R.color.snackbarBackground
private val snackbarButton = R.color.snackbarButton
private val defaultMsg = R.string.error_message_default

fun View.showSnackbar(
    context: Context,
    message: String?,
    action: View.OnClickListener,
    buttonMsg: String
) {
    context.apply {
        val msg = message ?: getString(defaultMsg)

        Snackbar.make(this@showSnackbar, msg, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, textColor))
            .setBackgroundTint(ContextCompat.getColor(this, backgroundColor))
            .setAction(buttonMsg, action)
            .setActionTextColor(ContextCompat.getColor(this, snackbarButton))
            .run {
                updateParams(view)
                show()
            }
    }
}

private fun updateParams(view: View) {
    view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams)
        .apply {
            setMargins(20, 0, 20, 220)
        }
}
package com.example.core_ui.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

// TODO Henrique - Should I make this a View extended function?

fun clearFocusEditText(parent: View, editTextList: List<EditText>) {
    parent.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            for (et in editTextList) {
                if (et.isFocused) {
                    val outRect = Rect()
                    et.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        et.clearFocus()
                        hideKeyboard(v.context as Activity)
                    }
                    break
                }
            }

        }
        false
    }
}

private fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus

    if (view == null) {
        view = View(activity)
    }

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
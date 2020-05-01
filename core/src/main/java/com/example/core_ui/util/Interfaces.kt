package com.example.core_ui.util

import android.text.Editable
import android.text.TextWatcher

fun newTextWatcher(
    afterTextChange: ((Editable?) -> Unit)? = null,
    beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
): TextWatcher =
    object : TextWatcher {
        override fun afterTextChanged(e: Editable?) {
            afterTextChange?.invoke(e)
        }

        override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(cs, start, count, after)
        }

        override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(cs, start, before, count)
        }
    }
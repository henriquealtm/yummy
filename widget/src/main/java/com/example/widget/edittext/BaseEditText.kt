package com.example.widget.edittext

import android.content.Context
import android.text.InputFilter
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.core_ui.util.handleOptional
import com.santalu.maskedittext.MaskEditText

abstract class BaseEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    childInputType: Int,
    maxLength: Int?,
    digits: String? = null
) : AppCompatEditText(context, attrs) {

    init {
        inputType = childInputType.handleOptional()

        filters = arrayOf(InputFilter.LengthFilter(maxLength.handleOptional()))

        digits?.let {
            keyListener = DigitsKeyListener.getInstance(digits)
        }
    }



}
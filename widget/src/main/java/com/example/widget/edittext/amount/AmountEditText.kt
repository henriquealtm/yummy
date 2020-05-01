package com.example.widget.edittext.amount

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.example.core_ui.extension.string.removeComma
import com.example.core_ui.extension.string.removeDots
import com.example.core_ui.util.newTextWatcher
import com.example.widget.edittext.BaseEditText

private const val maxLength = 5
private const val digits = "0123456789,."

class AmountEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseEditText(
    context,
    attrs,
    InputType.TYPE_CLASS_NUMBER,
    maxLength,
    digits
) {

    private val editTextMaxDigitsLength = 5
    private var formattedValue: String? = null

    private val textWatcher =
        newTextWatcher(onTextChanged = { cs, _, _, _ -> onTextChanged(cs.toString()) })

    init {
        addTextChangedListener(textWatcher)
        setText("0")
    }

    /**
     * On edit text value change:
     * If its length is shorter than 3, fill the value with zeros from the left to the right
     * If its length is greater than 3, remove zeros in the start of the value
     * Else, show the inserted value without modification in the digits
     */
    private fun onTextChanged(newValue: String) {
        val valueWithDigits = newValue.removeComma().removeDots().let { rawValue ->
            val value = if (rawValue.length > editTextMaxDigitsLength) "" else rawValue

            when {
                // Insert 0 in the left side if the value has less than 3 digits
                value.length < 3 -> value.padStart(3, '0')
                // Remove the left zero if the value has more than 3 digits
                value.length > 3 -> if (value.first() == '0') value.removeRange(0, 1) else value
                else -> value
            }
        }

        formattedValue = valueWithDigits.insertDecimalComma().insertDot()

        formattedValue?.takeIf { it.isNotEmpty() }?.let {
            removeTextChangedListener(textWatcher)
            setFormatSelectedValueInEditText()
            addTextChangedListener(textWatcher)
        }
    }

    private fun String.insertDecimalComma(): String = try {
        StringBuilder(this).insert(this.lastIndex - 1, ",").toString()
    } catch (e: Exception) {
        this
    }

    private fun String.insertDot(): String =
        if (this.length > 6) {
            try {
                StringBuilder(this).insert(this.lastIndex - 5, ".").toString()
            } catch (e: Exception) {
                this
            }
        } else {
            this
        }

    private fun setFormatSelectedValueInEditText() {
        formattedValue?.let {
            setText(it)
            setSelection(it.length)
        }
    }

}
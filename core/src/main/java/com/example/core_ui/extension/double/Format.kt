package com.example.core_ui.extension.double

import com.example.core_ui.util.STRING_ZERO_DOUBLE_FORMATTED
import com.example.core_ui.util.decimalFormat

fun Double?.format() = this?.let {
    decimalFormat?.format(this)
} ?: STRING_ZERO_DOUBLE_FORMATTED

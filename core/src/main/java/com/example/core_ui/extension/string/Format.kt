package com.example.core_ui.extension.string

import com.example.core_ui.util.DOUBLE_ZERO
import com.example.core_ui.util.decimalFormat

fun String?.toDouble() = this?.let {
    decimalFormat?.parse(this)?.toDouble()
} ?: DOUBLE_ZERO
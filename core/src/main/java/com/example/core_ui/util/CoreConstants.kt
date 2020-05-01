package com.example.core_ui.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

// Strings
const val STRING_EMPTY = ""
const val STRING_ZERO_DOUBLE_FORMATTED = "0,00"

// Double
const val DOUBLE_ZERO = 0.0

// Locale
private val brLocale = Locale("pt", "BR")

// Decimal Format
val decimalFormat: NumberFormat? = DecimalFormat.getInstance(brLocale)
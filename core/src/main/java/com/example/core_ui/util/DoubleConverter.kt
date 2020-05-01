package com.example.core_ui.util

import androidx.databinding.InverseMethod
import com.example.core_ui.extension.double.format
import com.example.core_ui.extension.string.toDouble

object DoubleConverter {

    @InverseMethod("stringToDouble")
    @JvmStatic
    fun doubleToString(
        value: Double?
    ): String = value.format()

    @JvmStatic
    fun stringToDouble(
        value: String?
    ) = value?.toDouble()

}


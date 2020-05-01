package com.example.core_ui.extension.util

private const val MINIMUM_NUMBER_VALUE = 0

fun Boolean?.handleOptional() = this ?: false

fun Int?.handleOptional() = this ?: MINIMUM_NUMBER_VALUE

fun Double?.handleOptional() = this ?: MINIMUM_NUMBER_VALUE.toDouble()

fun String?.handleOptional() = this ?: ""

fun <T> List<T>?.handleOptional() = this ?: listOf()
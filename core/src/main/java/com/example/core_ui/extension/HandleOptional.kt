package com.example.core_ui.extension

fun Boolean?.handleOptional() = this ?: false

fun Int?.handleOptional() = this ?: 0

fun Double?.handleOptional() = this ?: 0.toDouble()

fun String?.handleOptional() = this ?: ""

fun <T> List<T>?.handleOptional() = this ?: listOf()
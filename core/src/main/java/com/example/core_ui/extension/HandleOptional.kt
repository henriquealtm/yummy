package com.example.core_ui.extension

fun Boolean?.handleOptional() = this ?: false

fun Int?.handleOptional() = this ?: 0
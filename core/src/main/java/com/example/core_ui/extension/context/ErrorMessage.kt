package com.example.core_ui.extension.context

import android.content.Context
import android.view.View
import com.example.core_ui.R
import com.example.core_ui.extension.view.showSnackbar
import com.example.network.NetworkError

private val connectionMsg = R.string.error_message_connection
private val timeoutFailureMsg = R.string.error_message_timeout_failure
private val buttonMsg = R.string.error_message_try_again

fun Context.showErrorMessage(
    error: NetworkError,
    view: View,
    action: () -> Unit
) {
    val message = when (error) {
        is NetworkError.ConnectionError -> getString(connectionMsg)
        is NetworkError.ResponseError -> error.error
        else -> getString(timeoutFailureMsg)
    }

    val listener = View.OnClickListener { action.invoke() }

    view.showSnackbar(this, message, listener, getString(buttonMsg))
}
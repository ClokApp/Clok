package com.kingfu.clok.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import com.kingfu.clok.variable.Variable.showSnackBar

suspend fun showSnackBar(
    message: String,
    actionLabel: String?,
    duration: SnackbarDuration,
    scaffoldState: ScaffoldState,
    action: () -> Unit,
    dismiss: () -> Unit
) {

    if (showSnackBar) {
        val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration
        )

        when (snackBarResult) {
            SnackbarResult.Dismissed -> {
                dismiss()
            }
            SnackbarResult.ActionPerformed -> {
                action()
            }
        }
        showSnackBar = false
    }
}
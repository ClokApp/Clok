package com.kingfu.clok.bottomBar

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import com.kingfu.clok.variable.Variable.showSnackbar

suspend fun showSnackbar(
    message: String,
    actionLabel: String?,
    duration: SnackbarDuration,
    scaffoldState: ScaffoldState,
    action: () -> Unit,
    dismiss: () -> Unit
) {
    if (showSnackbar) {
        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration
        )

        when (snackbarResult) {
            SnackbarResult.Dismissed -> {
                dismiss()
            }
            SnackbarResult.ActionPerformed -> {
                action()
            }
        }
        showSnackbar = false
    }
}
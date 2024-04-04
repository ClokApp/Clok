package com.kingfu.clok.ui.util

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.kingfu.clok.util.Variable.isShowSnackbar
import com.kingfu.clok.util.Variable.resetCustomSnackbarStates
import com.kingfu.clok.util.Variable.snackbarAction
import com.kingfu.clok.util.Variable.snackbarDismiss
import com.kingfu.clok.util.Variable.snackbarDuration
import com.kingfu.clok.util.Variable.snackbarIsWithDismissAction
import com.kingfu.clok.util.Variable.snackbarLabelAction
import com.kingfu.clok.util.Variable.snackbarMessage


suspend fun showSnackBar(
    snackbarHostState: SnackbarHostState,
) {

    if (isShowSnackbar) {

        val snackbarResult = snackbarHostState.showSnackbar(
            message = snackbarMessage,
            actionLabel = snackbarLabelAction,
            withDismissAction = snackbarIsWithDismissAction,
            duration = snackbarDuration
        )

        when (snackbarResult) {
            SnackbarResult.ActionPerformed -> {
                snackbarAction()
            }
            SnackbarResult.Dismissed ->{
                snackbarDismiss()
            }
        }
        resetCustomSnackbarStates()
    }
}
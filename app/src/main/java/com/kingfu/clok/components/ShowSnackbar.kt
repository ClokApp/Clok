package com.kingfu.clok.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.kingfu.clok.variable.Variable.isShowSnackbar
import com.kingfu.clok.variable.Variable.resetCustomSnackbarStates
import com.kingfu.clok.variable.Variable.snackbarAction
import com.kingfu.clok.variable.Variable.snackbarDismiss
import com.kingfu.clok.variable.Variable.snackbarDuration
import com.kingfu.clok.variable.Variable.snackbarIsWithDismissAction
import com.kingfu.clok.variable.Variable.snackbarLabelAction
import com.kingfu.clok.variable.Variable.snackbarMessage


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
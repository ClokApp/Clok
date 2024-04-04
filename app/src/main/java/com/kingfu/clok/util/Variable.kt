package com.kingfu.clok.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Variable {
    var isShowTimerNotification by mutableStateOf(value = false)
    var startDestination by mutableStateOf<String?>(value = null)
    var navigateToStartScreen by mutableStateOf( value = true)

    var isShowSnackbar by mutableStateOf(value = false)
    var snackbarMessage by mutableStateOf(value = "")
    var snackbarIsWithDismissAction by mutableStateOf(value = true)
    var snackbarLabelAction: String? by mutableStateOf(value = null)
    var snackbarDuration: SnackbarDuration by mutableStateOf(value = SnackbarDuration.Short)
    var snackbarAction: () -> Unit = {}
    var snackbarDismiss: () -> Unit = {}
    fun resetCustomSnackbarStates(){
        isShowSnackbar = false
        snackbarMessage = ""
        snackbarIsWithDismissAction = true
        snackbarLabelAction = null
        snackbarDuration = SnackbarDuration.Short
    }

}
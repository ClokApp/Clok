package com.kingfu.clok.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.core.formatTimeTimer
import com.kingfu.clok.ui.theme.ClokThemePreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
    modifier: Modifier = Modifier,
    isFinished: Boolean,
    cancel: () -> Unit,
    time: Long
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (isFinished) {
        ModalBottomSheet(
            onDismissRequest = { cancel() },
            sheetState = sheetState
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Timer is finished",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "-${time.formatTimeTimer()}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.outline
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .clickable {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) cancel()
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

//            }
        }
    }
}

@Preview
@Composable
fun BottomSheetPreview() {
    ClokThemePreview {
        ModalBottomSheet(
            time = 3000000,
            cancel = { },
            isFinished = true
        )
    }
}
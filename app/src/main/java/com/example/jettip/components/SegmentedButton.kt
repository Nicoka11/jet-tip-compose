package com.example.jettip.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedButton(
    modifier: Modifier = Modifier,
    content: MutableState<Int>,
    leftButton: @Composable (() -> Unit)?,
    leftAction: () -> Unit,
    rightButton: @Composable (() -> Unit)?,
    rightAction: () -> Unit,
) {

    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    ) {
        Row(
            modifier = Modifier
                .border(
                    color = MaterialTheme.colorScheme.primary,
                    width = 2.dp,
                )
                .height(36.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = leftAction,
                modifier = Modifier
                    .width(48.dp)
            ) {
                leftButton?.invoke()
            }
            Box(
                modifier = Modifier
                    .border(
                        color = MaterialTheme.colorScheme.primary,
                        width = 2.dp,
                    )
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = content.value.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            TextButton(
                onClick = rightAction,
                modifier = Modifier
                    .width(48.dp)
            ) {
                rightButton?.invoke()
            }
        }
    }
}
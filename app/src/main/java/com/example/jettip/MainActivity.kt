package com.example.jettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.jettip.components.InputField
import com.example.jettip.components.SegmentedButton
import com.example.jettip.ui.theme.JetTipTheme
import kotlin.math.ceil

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            App {}
        }
    }
}

@Composable
fun App(children: @Composable () -> Unit) {
    val totalPerPerson = remember {
        mutableStateOf(0F)
    }
    JetTipTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderCard(title = "Total Per Person :", value = totalPerPerson.value)
                MainContainer(totalPerPerson = totalPerPerson)
            }
            children()
        }
    }
}

@Composable
fun MainContainer(totalPerPerson: MutableState<Float>) {
    BillForm(totalPerPerson = totalPerPerson) {
        Log.d(
            "Value",
            "MainContainer: $it"
        )
    }
}

@Composable
fun HeaderCard(title: String = "Total per person", value: Float = 12.0F) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "$$value",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun BillForm(
    totalPerPerson: MutableState<Float>,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {

    fun calculateSplitTip(total: Float, split: Int, percentage: Float): Float {
        val percent = percentage / 100.0F
        return ((total + (total * percent))) / split
    }

    val totalBillState = remember {
        mutableStateOf(value = "")
    }

    val splitState = remember {
        mutableStateOf(value = 1)
    }

    val tipPercentage = remember {
        mutableStateOf("25.0")
    }

    val isValidState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = modifier,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                valueState = totalBillState,
                label = "Enter bill",
                enabled = true,
                isSingleLine = true,
                onValueChange = {
                    totalPerPerson.value = calculateSplitTip(
                        total = totalBillState.value.toFloat(),
                        split = splitState.value,
                        percentage = tipPercentage.value.toFloat()
                    )
                },
                onAction = KeyboardActions {
                    if (!isValidState) return@KeyboardActions
                    onValueChange(totalBillState.value.trim())
                    keyboardController?.hide()
                    totalPerPerson.value = calculateSplitTip(
                        total = totalBillState.value.toFloat(),
                        split = splitState.value,
                        percentage = tipPercentage.value.toFloat()
                    )
                }
            )
            AnimatedVisibility(
                visible = isValidState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(all = 3.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Split")
                        SegmentedButton(
                            content = splitState,
                            leftButton = {
                                Icon(
                                    imageVector = Icons.Rounded.Remove,
                                    contentDescription = "Remove Icon"
                                )
                            },
                            leftAction = {
                                if (splitState.value > 1) splitState.value -= 1
                                totalPerPerson.value = calculateSplitTip(
                                    total = totalBillState.value.toFloat(),
                                    split = splitState.value,
                                    percentage = tipPercentage.value.toFloat()
                                )
                            },
                            rightButton = {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Add Icon"
                                )
                            },
                            rightAction = {
                                splitState.value += 1
                                totalPerPerson.value = calculateSplitTip(
                                    total = totalBillState.value.toFloat(),
                                    split = splitState.value,
                                    percentage = tipPercentage.value.toFloat()
                                )
                            })
                    }
                    Row(
                        modifier = Modifier
                            .padding(all = 3.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Tip")
                        AssistChip(onClick = { /*NOTHING TODO HERE*/ },
                            label = { Text(text = "${totalBillState.value.toFloat() * (tipPercentage.value.toFloat() / 100)}") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.AttachMoney,
                                    contentDescription = "Add Icon"
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(all = 3.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Total with tip")
                        AssistChip(onClick = { /*NOTHING TODO HERE*/ },
                            label = { Text(text = "${(totalBillState.value.toFloat() * (tipPercentage.value.toFloat() / 100)) + totalBillState.value.toFloat()}") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.AttachMoney,
                                    contentDescription = "Add Icon"
                                )
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(all = 3.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Slider(
                            modifier = Modifier
                                .width(230.dp),
                            value = tipPercentage.value.toFloat(),
                            onValueChange = {
                                tipPercentage.value = ceil(it).toString()
                                totalPerPerson.value = calculateSplitTip(
                                    total = totalBillState.value.toFloat(),
                                    split = splitState.value,
                                    percentage = tipPercentage.value.toFloat()
                                )
                            },
                            steps = 99,
                            valueRange = 0F..100F
                        )
                        Text(
                            text = "%${tipPercentage.value.toFloat().toInt()}",
                            modifier = Modifier.width(70.dp),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        )
                    }
                }
            }
        }
    }
}
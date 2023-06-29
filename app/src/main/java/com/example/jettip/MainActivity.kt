package com.example.jettip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettip.ui.theme.JetTipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(modifier = Modifier.padding(all = 16.dp)) {
                        HeaderCard(title = "Total Per Person :", value = 66.0F)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HeaderCard(title: String = "Total per person", value: Float = 12.0F) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min), color = MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.large) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 24.dp)) {
            Text(text = title, style = TextStyle(color = MaterialTheme.colorScheme.onSecondary), fontSize = 22.sp)
            Text(text = "$$value", style = TextStyle(color = MaterialTheme.colorScheme.onSecondary), fontSize = 32.sp)
        }
    }
}

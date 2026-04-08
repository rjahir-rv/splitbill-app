package com.splitbill.kmp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.splitbill.kmp.ui.theme.`Split-billTheme`
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.splitbill.kmp.components.MainCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Splitbill", fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            ContentHome(modifier = Modifier)
        }
    }
}


@Composable
fun ContentHome(modifier: Modifier) {
    var amount by rememberSaveable { mutableStateOf("") }
  Column(modifier) {
      MainCard(title = "Total Bill"){
          OutlinedTextField(value = amount, onValueChange = {amount = it},
              label = {Text("Enter amount")},
          modifier = Modifier.fillMaxWidth())
      }
  }
}

@Preview
@Composable
fun ContentHomePreview() {
    `Split-billTheme` {
        ContentHome(modifier = Modifier)
    }
}

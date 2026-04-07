package com.splitbill.kmp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.splitbill.kmp.screens.HomeScreen
import com.splitbill.kmp.ui.theme.`Split-billTheme`

@Composable
fun App() {
    `Split-billTheme` {
        HomeScreen()
    }
}



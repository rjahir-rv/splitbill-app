package com.splitbill.kmp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainIcon(desc: String, icon: DrawableResource, onClick: () -> Unit){
    IconButton(
        onClick,
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = desc
        )
    }
}
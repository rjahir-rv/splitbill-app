package com.splitbill.kmp.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splitbill.kmp.components.MainCard
import com.splitbill.kmp.components.MainIcon
import com.splitbill.kmp.components.MainRow
import splitbill.kmp.generated.resources.Res
import splitbill.kmp.generated.resources.ic_arrow_circleup
import splitbill.kmp.generated.resources.ic_arrow_drop_down

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
    val options = listOf(0, 10, 15, 25, 30)
    var selectedOption by remember { mutableStateOf(options[0]) }
    var numberPeople by rememberSaveable { mutableStateOf(1) }
    var totalTip by remember { mutableStateOf(0.0) }
    var totalPerPerson by remember { mutableStateOf(0.0) }
    var total by remember { mutableStateOf(0.0) }
    val focusManager = LocalFocusManager.current


    Column(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MainCard(title = "Total Bill") {
            OutlinedTextField(
                value = amount, onValueChange = { amount = it },
                label = { Text("Enter amount") },
                leadingIcon = {
                    Text(
                        "$",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEach { option ->
                    FilterChip(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedLabelColor = MaterialTheme.colorScheme.primary,
                            selectedContainerColor = MaterialTheme.colorScheme.secondary
                        ),
                        label = { Text("$option%") }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Number of people", style = MaterialTheme.typography.bodyLarge)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MainIcon("Icon down", Res.drawable.ic_arrow_drop_down) {
                        if (numberPeople > 1) numberPeople--
                    }
                    Text(
                        text = numberPeople.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    MainIcon("Icon up", Res.drawable.ic_arrow_circleup) {
                        numberPeople++
                    }
                }
            }

            val validAmount = amount.toDoubleOrNull() ?: 0.0

            Button(
                onClick = {
                    if (validAmount > 0) {
                        totalTip = validAmount * (selectedOption.toDouble() / 100)
                        total = validAmount + totalTip
                        totalPerPerson = calculateTotal(validAmount, selectedOption.toDouble(), numberPeople)
                    } else {
                        println("Please enter a valid amount")
                    }
                    focusManager.clearFocus()
                },
                enabled = validAmount > 0,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Split the bill", modifier = Modifier.padding(vertical = 8.dp), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        MainCard(title = "Summary") {
            MainRow(title = "Tip Amount", total = totalTip.round())
            MainRow(title = "Total", total = total.round())
            Surface(
                color = Color(0xFFE5F4F9),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Total per person is $${totalPerPerson.round()}", color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )

            }
        }

    }
}

fun calculateTotal(amount: Double, tip: Double, numberPeople: Int): Double{
    val tipAmount = amount * (tip / 100)
    val total = amount + tipAmount
    return total / numberPeople
}

fun Double.round(): Double {
    return kotlin.math.round(this * 100) / 100
}

@Preview
@Composable
fun ContentHomePreview() {
    `Split-billTheme` {
        ContentHome(modifier = Modifier)
    }
}

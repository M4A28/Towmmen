package com.mohmmed.mosa.eg.towmmen.presenter.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.OutlinedExposedDropdownMenu
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewProductScreen(
    modifier: Modifier = Modifier,
    onAddClick: (Product) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stockQuantity by remember { mutableStateOf("") }
    var manfDateState = rememberDatePickerState()
    var manfDate by remember { mutableStateOf(Date()) }
    var expDateState = rememberDatePickerState()
    var expDate by remember { mutableStateOf(Date()) }
    var showManfDatePicker by remember { mutableStateOf(false) }
    var showExpDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val options = listOf("حبوب","معلبات", "مواد غذائية", "منظفات")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                Text(text = "Add New Product")
            })
        }
    ) { padding ->
        val topPadding = padding.calculateTopPadding()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = topPadding, end = 4.dp, start = 4.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name") },
                leadingIcon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Product Name") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.description), contentDescription = "Description") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = price,
                onValueChange = { price = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                label = { Text("Price") },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.money), contentDescription = "Price") }
            )


            OutlinedExposedDropdownMenu(
                options = options,
                selectedOption = options[0],
                label = "Category",
                onValueChangeEvent = {
                    category = it
                })

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                value = stockQuantity,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { stockQuantity = it },
                label = { Text("Stock Quantity") },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.quantity), contentDescription = "Stock Quantity") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                value = dateFormatter.format(manfDate),
                onValueChange = { },
                label = { Text("Manufacturing Date") },
                trailingIcon = {
                    IconButton(onClick = { showManfDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Select date")
                    }
                },
                readOnly = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                value = dateFormatter.format(expDate),
                onValueChange = { },
                label = { Text("Expiration Date") },
                trailingIcon = {
                    IconButton(onClick = { showExpDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Select date")
                    }
                },
                readOnly = true
            )

            if (showManfDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showManfDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showManfDatePicker = false }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showManfDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = manfDateState)
                    manfDate = manfDateState.selectedDateMillis?.let { Date(it) } ?: Date()

                }
            }

            if (showExpDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showExpDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showExpDatePicker = false }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showExpDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = expDateState
                    )
                    expDate = expDateState.selectedDateMillis?.let { Date(it) } ?: Date()

                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if(name.isNotEmpty()){
                        onAddClick(Product(
                            name = name.trim(),
                            description = description.trim(),
                            price = price.toDoubleOrNull() ?: 0.0,
                            category = category,
                            manufactureDate = manfDate,
                            expireDate = expDate,
                            stockQuantity = stockQuantity.toIntOrNull() ?: 0,
                            createdAt = Date(),
                        ))
                        // clearData
                        name = ""
                        description = ""
                        price = ""
                        category = ""
                        stockQuantity = ""
                    }
                },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Product"
                )
                Text(
                    text = "Add Product",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }

}
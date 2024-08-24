package com.mohmmed.mosa.eg.towmmen.presenter.product


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.TextFiledExposedDropdownMenu
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.category.CategoryViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen
import com.mohmmed.mosa.eg.towmmen.util.PRODUCT_KEY
import com.mohmmed.mosa.eg.towmmen.util.SCANNED_BARCODE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditProductScreen(navController: NavHostController){
    val productViewModel: ProductViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val categoryList by categoryViewModel.getAllCategory().collectAsState(initial = emptyList())
    var code by remember { mutableStateOf("") }
    navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String?>(SCANNED_BARCODE)?.let {
            code = it
        }

    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Product?>(PRODUCT_KEY)?.let{ product ->
        EditProductContent(
            editedProduct = product,
            onEditClick = {
                productViewModel.updateProduct(it)
                navController.popBackStack() },
            onBackClick = { navController.popBackStack() },
            options = categoryList.map{ it.category},
            barCode = code,
            onBarcodeClick = { navigateToScreen(navController, Route.BarcodeScannerScreen.route) }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductContent(
    modifier: Modifier = Modifier,
    editedProduct: Product?,
    options: List<String>,
    barCode: String?,
    onEditClick: (Product) -> Unit,
    onBackClick: () -> Unit = {},
    onBarcodeClick: () -> Unit = {}
) {

    var name by rememberSaveable { mutableStateOf(editedProduct?.name ?:"") }
    var barcode by remember { mutableStateOf(barCode ?: editedProduct?.barcode) }
    var nameError by remember { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf(editedProduct?.description?: "") }
    var cost by rememberSaveable { mutableStateOf(editedProduct?.cost.toString()) }
    var price by rememberSaveable { mutableStateOf(editedProduct?.price.toString()) }
    var priceError by remember { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(editedProduct?.category ?:"") }
    var stockQuantity by rememberSaveable { mutableStateOf(editedProduct?.stockQuantity.toString()) }
    var unit by rememberSaveable { mutableStateOf(editedProduct?.unit.toString()) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(Uri.parse(editedProduct?.imagePath)) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri ->
            uri?.let{
                imageUri = it
            }
        }
    )
    var stockQuantityError by remember { mutableStateOf("") }
    val manfDateState = rememberDatePickerState()
    var manfDate by rememberSaveable { mutableStateOf(editedProduct?.manufactureDate) }
    val expDateState = rememberDatePickerState()
    var expDate by rememberSaveable { mutableStateOf(editedProduct?.expireDate) }
    var showManfDatePicker by remember { mutableStateOf(false) }
    var showExpDatePicker by remember { mutableStateOf(false) }

    val dateFormatter =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.edit_product))
                    }

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

            AsyncImage(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .clickable { galleryLauncher.launch("image/*") }
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .error(R.drawable.image)
                    .placeholder(R.drawable.image)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.shopping),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.product_name_))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = if(barcode == null || barcode!!.isEmpty()) editedProduct?.barcode!! else barcode!!,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { barcode = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.barcode),
                        contentDescription = null ,
                        modifier = Modifier
                            .clickable { onBarcodeClick() }
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.producat_barcode))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.description),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.description))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cost,
                    onValueChange = { cost = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.money),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.cost_))
                    }
                )

            Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = price,
                    onValueChange = { price = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.cash),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.price))
                    }
                )



            Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stockQuantity,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { stockQuantity = it },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.quantity),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.stock_quantity))
                    }
                )

            Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = unit,
                    onValueChange = { unit = it },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.balance),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.measurement_unit))
                    }
                )


            Spacer(modifier = Modifier.height(10.dp))

            TextFiledExposedDropdownMenu(
                options = options,
                selectedOption = if(options.isEmpty()) "" else options[0],
                readOnly = true,
                label = stringResource(id = R.string.category),
                onValueChangeEvent = {
                    category = it
                },
                leadingIcon = R.drawable.category
            )




            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = dateFormatter.format(manfDate),
                onValueChange = { },
                readOnly = true,
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.calendar_month),
                        contentDescription = null ,
                        modifier = Modifier
                            .clickable { showManfDatePicker = true }
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.manf_date))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = dateFormatter.format(expDate),
                onValueChange = { },
                readOnly = true,
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.date),
                        contentDescription = null ,
                        modifier = Modifier
                            .clickable { showExpDatePicker = true }
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.exp_date))
                }
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
                    nameError =  if (name.isBlank()) "Name is required" else ""
                    priceError =  if (price.isBlank()) "Price is required" else ""

                    stockQuantityError =  if (stockQuantity.isBlank()) "stockQuantity is required" else ""

                    if(name.isNotEmpty() &&
                        price.isNotEmpty() &&
                        stockQuantity.isNotEmpty() &&
                        cost.isNotEmpty()){
                        onEditClick(
                            Product(
                                productId = editedProduct?.productId!!,
                                name = name.trim(),
                                description = description.trim(),
                                cost = cost.toDoubleOrNull() ?: 0.0,
                                price = price.toDoubleOrNull() ?: 0.0,
                                category = category,
                                imagePath = imageUri?.toString()?: "",
                                manufactureDate = manfDate!!,
                                expireDate = expDate!!,
                                stockQuantity = stockQuantity.toIntOrNull() ?: 0,
                                createdAt = Date(),
                                unit = unit.trim(),
                                barcode = if(barcode.isNullOrEmpty()) editedProduct.barcode else barcode!!
                            )
                        )
                        // clearData
                        name = ""
                        description = ""
                        price = ""
                        unit = ""
                        cost = ""
                        category = ""
                        barcode = ""
                        imageUri = null
                        stockQuantity = ""
                        expDate = Date()
                        manfDate = Date()
                    }
                },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Product"
                )
                Text(
                    text = stringResource(id = R.string.save_edit),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }

}
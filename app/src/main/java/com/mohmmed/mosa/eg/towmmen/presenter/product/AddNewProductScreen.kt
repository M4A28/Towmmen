package com.mohmmed.mosa.eg.towmmen.presenter.product

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.mohmmed.mosa.eg.towmmen.util.SCANNED_BARCODE
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddNewProductScreen(navController: NavHostController){
    val productViewModel: ProductViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val categoryList by categoryViewModel.getAllCategory().collectAsState(initial = emptyList())
    var code by remember { mutableStateOf("") }
    navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String?>(SCANNED_BARCODE)?.let {
            code = it
        }

    AddNewProductContent(
        onAddClick = {product ->  productViewModel.addNewProduct(product) },
        onBackClick = { navController.popBackStack() },
        options = categoryList.map{ it.category},
        barCode = code,
        onBarcodeClick = { navigateToScreen(navController, Route.BarcodeScannerScreen.route) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewProductContent(
    modifier: Modifier = Modifier,
    options: List<String>,
    barCode: String? = "",
    onAddClick: (Product) -> Unit,
    onBackClick: () -> Unit = {},
    onBarcodeClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var name by rememberSaveable { mutableStateOf("") }
    var barcode by remember { mutableStateOf(barCode?:"") }
    var description by rememberSaveable { mutableStateOf("") }
    var cost by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var stockQuantity by rememberSaveable { mutableStateOf("") }
    var unit by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri ->
            uri?.let{
                imageUri = it
            }
        }
    )
    val successMassage = stringResource(id = R.string.producte_add_success)
    val snackbarHostState = remember { SnackbarHostState() }
    val manfDateState = rememberDatePickerState()
    var manfDate by rememberSaveable { mutableStateOf(Date()) }
    val expDateState = rememberDatePickerState()
    var expDate by rememberSaveable { mutableStateOf(Date()) }
    var showManfDatePicker by remember { mutableStateOf(false) }
    var showExpDatePicker by remember { mutableStateOf(false) }
    val dateFormatter =  SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                          Text(text = stringResource(id = R.string.add_new_product))
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
                    .placeholder(R.drawable.image)
                    .error(R.drawable.image)
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
                value = barcode,
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
                            Text(stringResource(id = R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showManfDatePicker = false }) {
                            Text(stringResource(id = R.string.cancel))
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
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if(name.isNotBlank() &&
                        price.isNotBlank() &&
                        stockQuantity.isNotBlank() &&
                        barcode.isNotBlank() &&
                        cost.isNotBlank()){
                        val product = Product(
                            name = name, barcode = barcode,
                            cost = cost.toDoubleOrNull()?: 0.0,
                            price = price.toDoubleOrNull()?: 0.0,
                            description = description,
                            imagePath = imageUri.toString(),
                            category = category,
                            stockQuantity = stockQuantity.toIntOrNull()?: 0,
                            unit = unit,
                            manufactureDate = manfDate, expireDate = expDate,
                            createdAt = Date(), updatedAt = Date()
                        )
                        onAddClick(product)
                        scope.launch {
                            snackbarHostState.showSnackbar(successMassage)
                        }
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
                    text = stringResource(id = R.string.add_product),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

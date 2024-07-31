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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomExposedDropdownMenu
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomTextFiled
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.category.CategoryViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen
import com.mohmmed.mosa.eg.towmmen.util.SCANNED_BARCODE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewProductScreen(navController: NavHostController){
    val productViewModel: ProductViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    //val viewModel: BarcodeScannerViewModel = hiltViewModel()
    //val barcode by viewModel.barcode.collectAsState()
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

    var name by rememberSaveable { mutableStateOf("") }
    var barcode by remember { mutableStateOf(barCode?:"") }
    var nameError by remember { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var priceError by remember { mutableStateOf("") }
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
    var stockQuantityError by remember { mutableStateOf("") }
    val manfDateState = rememberDatePickerState()
    var manfDate by rememberSaveable { mutableStateOf(Date()) }
    val expDateState = rememberDatePickerState()
    var expDate by rememberSaveable { mutableStateOf(Date()) }
    var showManfDatePicker by remember { mutableStateOf(false) }
    var showExpDatePicker by remember { mutableStateOf(false) }

    val dateFormatter =  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
          topBar = {
              TopAppBar(
                  colors = TopAppBarDefaults.topAppBarColors(
                      containerColor = MaterialTheme.colorScheme.primary,
                      titleContentColor = MaterialTheme.colorScheme.onPrimary
                  ),
                  navigationIcon = {
                      IconButton(onClick = {
                          onBackClick()
                      }){
                          Icon(
                              tint = MaterialTheme.colorScheme.onPrimary,
                              imageVector = Icons.Default.ArrowBack,
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
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .placeholder(R.drawable.image)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(6.dp))

            CustomTextFiled(
                value = name,
                onValueChange = { name = it },
                label = stringResource(id = R.string.product_name_),
                leadingIcon = R.drawable.shopping
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = barcode,
                onValueChange = {
                    barcode = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onIconClick = { onBarcodeClick() },
                label = stringResource(R.string.producat_barcode),
                leadingIcon = R.drawable.barcode
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = description,
                onValueChange = {
                    description = it
                },
                label = stringResource(id = R.string.description),
                leadingIcon = R.drawable.description
            )

            Spacer(modifier = Modifier.height(4.dp))


            CustomTextFiled(
                value = price,
                onValueChange = {
                    price = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                label = stringResource(id = R.string.price),
                leadingIcon = R.drawable.money
            )

            Spacer(modifier = Modifier.height(4.dp))


            CustomTextFiled(
                value = stockQuantity,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { stockQuantity = it },
                label = stringResource(id = R.string.stock_quantity),
                leadingIcon = R.drawable.quantity
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(

                value = unit,
                onValueChange = { unit = it },
                label = stringResource(id = R.string.measurement_unit),
                leadingIcon = R.drawable.balance
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomExposedDropdownMenu(
                options = options,
                selectedOption = if(options.isEmpty()) "" else options[0],
                readOnly = true,
                label = stringResource(id = R.string.category),
                onValueChangeEvent = {
                    category = it
                },
                leadingIcon = R.drawable.category
            )



      /*      CustomTextFiled(
                value = category,
                onValueChange = { category = it },
                label = stringResource(id = R.string.category),
                leadingIcon = R.drawable.category
            )*/

            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = dateFormatter.format(manfDate),
                onValueChange = { },
                onIconClick = {showManfDatePicker = true},
                label = stringResource(id = R.string.manf_date),
                leadingIcon = R.drawable.calendar_month,
                readOnly = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = dateFormatter.format(expDate),
                onValueChange = { },
                onIconClick = {showExpDatePicker = true},
                label = stringResource(id = R.string.exp_date),
                leadingIcon = R.drawable.date,
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
                    nameError =  if (name.isBlank()) "Name is required" else ""
                    priceError =  if (price.isBlank()) "Price is required" else ""

                    stockQuantityError =  if (stockQuantity.isBlank()) "stockQuantity is required" else ""

                    if(name.isNotEmpty() && price.isNotEmpty() && stockQuantity.isNotEmpty()){
                        onAddClick(
                            Product(
                            name = name.trim(),
                            description = description.trim(),
                            price = price.toDoubleOrNull() ?: 0.0,
                            category = category,
                            imagePath = imageUri?.toString()?: "",
                            manufactureDate = manfDate,
                            expireDate = expDate,
                            stockQuantity = stockQuantity.toIntOrNull() ?: 0,
                            createdAt = Date(),
                            unit = unit.trim(),
                            barcode = barcode
                        )
                        )
                        // clearData
                        name = ""
                        description = ""
                        price = ""
                        unit = ""
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
            Spacer(modifier = Modifier.height(6.dp))
        }
    }

}
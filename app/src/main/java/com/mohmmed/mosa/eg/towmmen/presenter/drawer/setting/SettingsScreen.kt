package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.exportCustomersToCsv
import com.mohmmed.mosa.eg.towmmen.util.exportProductsToCsv
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val scope = rememberCoroutineScope()

    val productViewModel: ProductViewModel = hiltViewModel()
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val appThemeViewModel: AppThemeViewModel = hiltViewModel()
    val currTheme by appThemeViewModel.theme.collectAsState()
    var darkMode by remember { mutableStateOf(currTheme == AppTheme.DARK) }
    var storagePermissionGranted  by remember { mutableStateOf(false) }
    val storagePermissionState = rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts
        .RequestPermission()) { storagePermissionGranted = it }
    val products by productViewModel.products.collectAsState(initial = emptyList())
    val customers by customerViewModel.getAllCustomer().collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
/*    val permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    } else {
        emptyArray()
    }*/


    LaunchedEffect(storagePermissionState) {
        if(!storagePermissionState.status.isGranted
            && storagePermissionState.status.shouldShowRationale){
            // todo enhance this
        }else{
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

/*
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        if (permissionsResult.all { it.value }) {

        } else {
            // Handle permission denial
            // Show a message or disable related features
        }
    }
*/

        Scaffold(
            topBar ={
                    TopAppBar(title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(id = R.string.app_setting))
                        }

                    })
            },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                AnimatedSettingItem(
                    icon = if(darkMode)  painterResource(id = R.drawable.dark_mode) else painterResource(id = R.drawable.light_mode),
                    title = if(darkMode) stringResource(R.string.dark_mode) else  stringResource(R.string.light_mode) ,
                    subtitle = stringResource(R.string.exchange_dark_light),
                    control = {
                        Switch(
                            checked = darkMode,
                            onCheckedChange = {
                                darkMode = it
                                if(currTheme.name == AppTheme.LIGHT.name) {
                                    appThemeViewModel.setTheme(AppTheme.DARK)
                                } else{
                                    appThemeViewModel.setTheme(AppTheme.LIGHT)
                                }
                            }
                        )
                    }
                )



                AnimatedSettingItem(
                    icon = painterResource(id = R.drawable.export),
                    title = stringResource(R.string.export_to_csv),
                    subtitle = stringResource(R.string.save_data_as_csv),
                    control = {
                        val expMsg = stringResource(R.string.data_exported)
                        Button(
                            onClick = {
                                scope.launch {
                                    if(storagePermissionGranted){
                                        exportProductsToCsv(products)
                                        exportCustomersToCsv(customers)
                                        snackbarHostState.showSnackbar(expMsg)
                                    }else{
                                        snackbarHostState.showSnackbar("You need to accept permission")
                                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    }
                                }
                            },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(stringResource(R.string.export))
                        }
                    }
                )


                AnimatedSettingItem(
                    icon = painterResource(id = R.drawable.delete),
                    title = stringResource(R.string.delete_app_data),
                    subtitle = stringResource(R.string.delete_app_data_wring),
                    control = {
                        val msg = stringResource(id = R.string.delete_data_msg)
                        Button(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(msg)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(stringResource(R.string.delete_data))
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AnimatedSettingItem(
    icon: Painter,
    title: String,
    subtitle: String,
    control: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 4.dp)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

    ) {
        Column(
            modifier = Modifier.padding(6.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
                control()
            }
        }
    }
}
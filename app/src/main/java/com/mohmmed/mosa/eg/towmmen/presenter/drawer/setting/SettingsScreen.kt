package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.exportCustomersToCsv
import com.mohmmed.mosa.eg.towmmen.util.exportProductsToCsv
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var appColor by remember { mutableStateOf(Color(0xFF6200EE)) }
    val scope = rememberCoroutineScope()
    val productViewModel: ProductViewModel = hiltViewModel()
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val products by productViewModel.products.collectAsState(initial = emptyList())
    val custmers by customerViewModel.getAllCustomer().collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    } else {
        emptyArray()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        if (permissionsResult.all { it.value }) {
            scope.launch {
                exportProductsToCsv(products)
                exportCustomersToCsv(custmers)
            }
        } else {
            // Handle permission denial
            // Show a message or disable related features
        }
    }

        Scaffold(
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
                Text(
                    text = stringResource(R.string.app_setting),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                AnimatedSettingItem(
                    icon = painterResource(id = R.drawable.dark_mode),
                    title = stringResource(R.string.dark_mode),
                    subtitle = stringResource(R.string.exchange_dark_light),
                    control = {
                        Switch(
                            checked = darkMode,
                            onCheckedChange = { darkMode = it }
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

                                    permissionLauncher.launch(permissions)
                                    snackbarHostState.showSnackbar(expMsg)
                                }
                            },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(stringResource(R.string.export))
                        }
                    }
                )

                AnimatedSettingItem(
                    icon = painterResource(id = R.drawable.palette),
                    title = stringResource(R.string.app_color),
                    subtitle = stringResource(R.string.choice_app_main_color),
                    control = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(appColor)
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                    CircleShape
                                )
                                .clickable {
                                    // Here you would typically open a color picker
                                    appColor = Color(
                                        (0..255).random(),
                                        (0..255).random(),
                                        (0..255).random()
                                    )
                                }
                        )
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded }
   /*         .shadow(
                elevation = animateDpAsState(
                    if (isExpanded) 8.dp else 2.dp,
                    animationSpec = spring(stiffness = Spring.StiffnessLow)
                ).value,
                shape = RoundedCornerShape(16.dp)
            )*/
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
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



/*
@Composable
fun ExportDatabaseScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Only request the WRITE_EXTERNAL_STORAGE permission if the device is running Android 9 or lower
    val permissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    } else {
        emptyArray()
    }



    Column {
        Text("Export Database to CSV")
        Button(onClick = {
            permissionLauncher.launch(permissions)
        }) {
            Text("Export")
        }
    }
}
*/

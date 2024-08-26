package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.componet.AdvancedAlertDialog
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.componet.CategoryHeader
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.componet.SettingItem
import com.mohmmed.mosa.eg.towmmen.util.backupDatabase
import com.mohmmed.mosa.eg.towmmen.util.exportAllDate
import com.mohmmed.mosa.eg.towmmen.util.restoreDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    val settingViewModel: SettingViewModel = hiltViewModel()


    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var filePath by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedUri = it
            filePath = it.path?: ""
        }
    }
    val scope = rememberCoroutineScope()
    val currTheme by settingViewModel.theme.collectAsState()
    val canSaveSell  by settingViewModel.canSaveSellToDb.collectAsState()
    val canSaveBuy  by settingViewModel.canSaveBuyToDb.collectAsState()
    val canSaveExpanse  by settingViewModel.canSaveExpanseToDb.collectAsState()

    var saveSell by remember { mutableStateOf(canSaveSell)}
    var saveBuy by remember { mutableStateOf(canSaveBuy)}
    var saveExpanse by remember { mutableStateOf(canSaveExpanse)}

    var darkMode by remember { mutableStateOf(currTheme == AppTheme.DARK) }
    var storagePermissionGranted by remember { mutableStateOf(false) }
    val storagePermissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .RequestPermission()
    ) { storagePermissionGranted = it }
    val context = LocalContext.current
    val products by settingViewModel.products.collectAsState()
    val customers by settingViewModel.customers.collectAsState()
    val invoices by settingViewModel.invoices.collectAsState()
    val category by settingViewModel.categorys.collectAsState()
    val dealers by settingViewModel.dealers.collectAsState()
    val notes by settingViewModel.notes.collectAsState()
    val lockers by settingViewModel.lockers.collectAsState()
    val expanse by settingViewModel.expanses.collectAsState(initial = emptyList())
    val snackBarHostState = remember { SnackbarHostState() }

    var showDeleteDataDialog by remember { mutableStateOf(false) }
    var showDeleteProductDialog by remember { mutableStateOf(false) }
    var showDeleteCustomerDialog by remember { mutableStateOf(false) }
    var showDeleteNoteDialog by remember { mutableStateOf(false) }
    var showDeleteDealersDialog by remember { mutableStateOf(false) }
    var showDeleteCategoryDialog by remember { mutableStateOf(false) }
    var showDeleteInvoiceDialog by remember { mutableStateOf(false) }
    var showDeleteExpanseDialog by remember { mutableStateOf(false) }
    var showDeleteLockerDialog by remember { mutableStateOf(false) }

    if(showDeleteDataDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_app_date),
            onDismiss = { showDeleteDataDialog = false },
            onConfirm = {
                settingViewModel.clearProducts()
                settingViewModel.clearCustomer()
                settingViewModel.clearNotes()
                settingViewModel.clearDealers()
                settingViewModel.clearCategorys()
                settingViewModel.clearLocker()
                settingViewModel.clearInvoice()
                settingViewModel.clearExpanses()
                showDeleteDataDialog = false
            }
        )
    }

    if(showDeleteExpanseDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_Expanse_date),
            onDismiss = { showDeleteExpanseDialog = false },
            onConfirm = {
                settingViewModel.clearExpanses()
                showDeleteExpanseDialog = false
            }
        )
    }

    if(showDeleteLockerDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_locker_date),
            onDismiss = { showDeleteLockerDialog = false },
            onConfirm = {
                settingViewModel.clearLocker()
                showDeleteLockerDialog = false
            }
        )
    }

    if(showDeleteInvoiceDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_Invoice_date),
            onDismiss = { showDeleteInvoiceDialog = false },
            onConfirm = {
                settingViewModel.clearInvoice()
                showDeleteInvoiceDialog = false
            }
        )
    }

    if(showDeleteCategoryDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_catagory_date),
            onDismiss = { showDeleteCategoryDialog = false },
            onConfirm = {
                settingViewModel.clearCategorys()
                showDeleteCategoryDialog = false
            }
        )
    }

    if(showDeleteDealersDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_dealers_date),
            onDismiss = { showDeleteDealersDialog = false },
            onConfirm = {
                settingViewModel.clearDealers()
                showDeleteDealersDialog = false
            }
        )
    }

    if(showDeleteNoteDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_note_date),

            onDismiss = { showDeleteNoteDialog = false },
            onConfirm = {
                settingViewModel.clearNotes()
                showDeleteNoteDialog = false
            }
        )
    }

    if(showDeleteCustomerDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_custoer_date),
            onDismiss = { showDeleteCustomerDialog = false },
            onConfirm = {
                settingViewModel.clearCustomer()
                showDeleteCustomerDialog = false
            }
        )
    }

    if(showDeleteProductDialog){
        AdvancedAlertDialog(
            title = stringResource(R.string.are_you_sure_to_delete_all_product_date),

            onDismiss = { showDeleteProductDialog = false },
            onConfirm = {
                settingViewModel.clearProducts()
                showDeleteProductDialog = false
            }
        )
    }





    LaunchedEffect(storagePermissionState) {
        if (!storagePermissionState.status.isGranted
            && storagePermissionState.status.shouldShowRationale
        ) {
            // todo enhance this
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.app_setting))
                }

            })
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            item {
                CategoryHeader(stringResource(R.string.display))
            }
            item {
                SettingItem(
                    icon = if(currTheme.name == AppTheme.DARK.name)
                        R.drawable.dark_mode
                    else R.drawable.light_mode,
                    title = stringResource(R.string.dark_theme),
                    subtitle = if(currTheme.name == AppTheme.DARK.name) stringResource(R.string.on) else stringResource(R.string.off),
                    onClick = { },
                    trailing = {
                        Switch(
                            checked = darkMode,
                            onCheckedChange = {
                                darkMode = it
                                settingViewModel.setTheme(currTheme.revers())
                            }
                        )
                    }
                )
            }
            // todo enhanc this
            item {
                CategoryHeader(stringResource(R.string.locker_setting))
            }
            item {
                SettingItem(
                    icon = R.drawable.invoice_paper,
                    title = stringResource(R.string.add_sell_invoice_to_locker),
                    onClick = { },
                    trailing = {
                        Switch(
                            checked = saveSell,
                            onCheckedChange = {
                                saveSell = it
                                settingViewModel.setCanSaveSellToDb(it)
                            }
                        )
                    }
                )
            }

            item {
                SettingItem(
                    icon = R.drawable.box,
                    title = stringResource(R.string.add_buy_invoice_to_locker),
                    onClick = { },
                    trailing = {
                        Switch(
                            checked = saveBuy,
                            onCheckedChange = {
                                saveBuy = it
                                settingViewModel.setCanSaveBuyToDb(it)

                            }
                        )
                    }
                )
            }

            item {
                SettingItem(
                    icon = R.drawable.cash,
                    title = stringResource(R.string.add_expanse_to_locker),
                    onClick = { },
                    trailing = {
                        Switch(
                            checked = saveExpanse,
                            onCheckedChange = {
                                saveExpanse = it
                                settingViewModel.setCanSaveExpanseToDb(it)
                            }
                        )
                    }
                )
            }

            item {
                CategoryHeader(stringResource(R.string.data_management))
            }
            item {
                SettingItem(
                    icon = R.drawable.invoice_paper,
                    title = stringResource(R.string.delete_all_invoices),
                    onClick = { showDeleteInvoiceDialog = true }
                )
            }
            item {
                SettingItem(
                    icon = R.drawable.person,
                    title = stringResource(R.string.delete_all_customers),
                    onClick = { showDeleteCustomerDialog = true }
                )
            }

            item {
                SettingItem(
                    icon = R.drawable.company,
                    title = stringResource(R.string.delete_all_dealers),
                    onClick = { showDeleteDealersDialog = true }
                )
            }
            item {
                SettingItem(
                    icon = R.drawable.box,
                    title = stringResource(R.string.delete_all_products),
                    onClick = { showDeleteProductDialog = true }
                )
            }

            item {
                SettingItem(
                    icon = R.drawable.locker,
                    title = stringResource(R.string.delete_all_locker_item),
                    onClick = { showDeleteLockerDialog = true}
                )
            }
            item {
                SettingItem(
                    icon = R.drawable.delete,
                    title = stringResource(R.string.delete_all_app_data),
                    onClick = { showDeleteDataDialog = true }
                )
            }

            item {
                CategoryHeader(stringResource(R.string.backup_restore))
            }
            item {
                SettingItem(
                    icon = R.drawable.csv,
                    title = stringResource(R.string.export_app_data_to_csv),
                    onClick = {
                        scope.launch(Dispatchers.IO){
                            exportAllDate(
                                categorys = category,
                                customers = customers,
                                dealers = dealers,
                                invoices = invoices,
                                notes = notes,
                                products = products,
                                expanses = expanse,
                                lockers = lockers
                            )
                            snackBarHostState.showSnackbar(message = context.getString(R.string.all_data_exported))
                        }

                    }
                )
            }
            item {
                SettingItem(
                    icon = R.drawable.backup_cloud,
                    title = stringResource(R.string.backup_database),
                    onClick = { try{
                        scope.launch {
                            backupDatabase(context)
                            snackBarHostState.showSnackbar(context.getString(R.string.database_has_saved))
                        }

                    }catch (e: Exception){
                        scope.launch {
                            snackBarHostState.showSnackbar(e.toString())
                        }
                    } }
                )
            }
            item {
                SettingItem(
                    icon = R.drawable.restore,
                    title = stringResource(R.string.restore_data_from_csv),
                    onClick = {
                        launcher.launch("*/*")
                            scope.launch {
                                if(filePath.isNotEmpty()){
                                    restoreDatabase(context, filePath)
                                }
                            }
                    },
                    trailing = {
                        Button(
                            enabled = filePath.isNotEmpty(),
                            onClick = {
                                scope.launch {
                                    if(filePath.isNotEmpty()){
                                        restoreDatabase(context, filePath)
                                        Log.d("PATH", filePath)
                                        snackBarHostState.showSnackbar(context.getString(R.string.database_restored))
                                        filePath = ""
                                    }
                                }
                        }) {
                            Text(text = stringResource(id = R.string.restore))
                        }
                    }
                )

            }
        }
    }
}

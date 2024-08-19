package com.mohmmed.mosa.eg.towmmen.presenter.barcode

import android.Manifest
import android.media.MediaPlayer
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.barcode.BarcodeAnalyzer
import com.mohmmed.mosa.eg.towmmen.util.SCANNED_BARCODE
import kotlinx.coroutines.delay


@Composable
fun BarcodeScannerScreen(navController: NavController) {
    val viewModel: BarcodeScannerViewModel = hiltViewModel()
    val barcode by viewModel.barcode.collectAsState()
    val context = LocalContext.current
    var qrCode by remember { mutableStateOf<String?>(null) }


    // Create and remember MediaPlayer
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.scanner_beep)
    }

    // Clean up MediaPlayer when leaving the composition
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    LaunchedEffect(barcode) {
        if (barcode != null) {
            try{
                mediaPlayer.start()
                delay(1000L)
                // Return to the previous screen with the barcode value
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(SCANNED_BARCODE, barcode)
                navController.popBackStack()

            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Scan BarCode",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                BarcodeScanner(onBarcodeDetected = {
                    viewModel.onBarcodeDetected(it)
                })

                ScannerOverlay()
            }
        }
    }

/*    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Scan BarCode",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))


            BarcodeScanner(onBarcodeDetected = {
                viewModel.onBarcodeDetected(it)
            })

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            ScannerOverlay()
        }
    }*/
}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScanner(
    onBarcodeDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var cameraPermissionGranted by remember { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts
        .RequestPermission()) { cameraPermissionGranted = it }
    val lifecycleOwner = LocalLifecycleOwner.current


    LaunchedEffect(cameraPermissionState) {
        if(!cameraPermissionState.status.isGranted
            && cameraPermissionState.status.shouldShowRationale){

        }else{
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
       /* cameraPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED*/
    }

    if (cameraPermissionGranted) {
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                val executor = ContextCompat.getMainExecutor(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(executor, BarcodeAnalyzer(onBarcodeDetected))
                        }
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalyzer
                        )
                    } catch (exc: Exception) {
                        Log.e("BarcodeScanner", "Use case binding failed", exc)
                    }
                }, executor)
                previewView
            },
            modifier = modifier
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.camera_permission),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


@Composable
fun ScannerOverlay() {
    val animatedValue = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // Draw transparent background
        //drawRect(Color(0x80000000))  // 50% transparent black
        
        // Draw pulsing line in center
        val lineY = canvasHeight / 2
        val lineAlpha = 0.3f + (animatedValue.value * 0.7f)  // Pulse between 30% and 100% opacity
        drawLine(
            color = Color.Red.copy(alpha = lineAlpha),
            start = Offset(0f, lineY),
            end = Offset(canvasWidth, lineY),
            strokeWidth = 4f
        )
        
        // Draw corner frame
        val frameWidth = canvasWidth * 0.8f
        val frameHeight = canvasHeight * 0.8f
        val cornerLength = frameWidth * 0.1f
        val frameOffset = Offset((canvasWidth - frameWidth) / 2, (canvasHeight - frameHeight) / 2)
        
        listOf(
            Offset(0f, 0f) to Offset(cornerLength, 0f),
            Offset(0f, 0f) to Offset(0f, cornerLength),
            Offset(frameWidth - cornerLength, 0f) to Offset(frameWidth, 0f),
            Offset(frameWidth, 0f) to Offset(frameWidth, cornerLength),
            Offset(0f, frameHeight - cornerLength) to Offset(0f, frameHeight),
            Offset(0f, frameHeight) to Offset(cornerLength, frameHeight),
            Offset(frameWidth, frameHeight - cornerLength) to Offset(frameWidth, frameHeight),
            Offset(frameWidth - cornerLength, frameHeight) to Offset(frameWidth, frameHeight)
        ).forEach { (start, end) ->
            drawLine(
                color = Color.White,
                start = start + frameOffset,
                end = end + frameOffset,
                strokeWidth = 4f
            )
        }
    }
}

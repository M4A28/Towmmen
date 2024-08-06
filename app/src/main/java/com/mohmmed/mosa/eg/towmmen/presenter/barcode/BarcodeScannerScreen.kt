package com.mohmmed.mosa.eg.towmmen.presenter.barcode

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            mediaPlayer.start()
            delay(1000L)
            // Return to the previous screen with the barcode value
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(SCANNED_BARCODE, barcode)

            navController.popBackStack()
            Log.d("code", barcode!!)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Scan BarCode",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
        ) {

            BarcodeScanner(onBarcodeDetected = {
                viewModel.onBarcodeDetected(it)
            })

            ScannerOverlay()
        }

    }
}



@Composable
fun BarcodeScanner(
    onBarcodeDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraPermissionGranted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cameraPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
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
fun ScannerOverlay2() {
    val infiniteTransition = rememberInfiniteTransition()
    val scanLinePosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        val windowWidth = canvasWidth * 0.8f
        val windowHeight = canvasHeight * 0.3f
        val windowLeft = (canvasWidth - windowWidth) / 2
        val windowTop = (canvasHeight - windowHeight) / 2
        
        // Draw semi-transparent black overlay
        drawRect(Color(0x80000000))
        
        // Clear the scanning window area
        drawRect(
            color = Color.Transparent,
            topLeft = Offset(windowLeft, windowTop),
            size = Size(windowWidth, windowHeight),
            blendMode = BlendMode.Clear
        )
        
        // Draw scanning line
        val lineY = windowTop + (windowHeight * scanLinePosition)
        drawLine(
            color = Color.Red,
            start = Offset(windowLeft, lineY),
            end = Offset(windowLeft + windowWidth, lineY),
            strokeWidth = 4f
        )
        
        val cornerLength = windowWidth * 0.1f
        val cornerStrokeWidth = 4f
        
        // Draw corners
        listOf(
            Offset(windowLeft, windowTop) to Offset(windowLeft + cornerLength, windowTop),
            Offset(windowLeft, windowTop) to Offset(windowLeft, windowTop + cornerLength),
            Offset(windowLeft + windowWidth - cornerLength, windowTop) to Offset(windowLeft + windowWidth, windowTop),
            Offset(windowLeft + windowWidth, windowTop) to Offset(windowLeft + windowWidth, windowTop + cornerLength),
            Offset(windowLeft, windowTop + windowHeight - cornerLength) to Offset(windowLeft, windowTop + windowHeight),
            Offset(windowLeft, windowTop + windowHeight) to Offset(windowLeft + cornerLength, windowTop + windowHeight),
            Offset(windowLeft + windowWidth, windowTop + windowHeight - cornerLength) to Offset(windowLeft + windowWidth, windowTop + windowHeight),
            Offset(windowLeft + windowWidth - cornerLength, windowTop + windowHeight) to Offset(windowLeft + windowWidth, windowTop + windowHeight)
        ).forEach { (start, end) ->
            drawLine(
                color = Color.White,
                start = start,
                end = end,
                strokeWidth = cornerStrokeWidth
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

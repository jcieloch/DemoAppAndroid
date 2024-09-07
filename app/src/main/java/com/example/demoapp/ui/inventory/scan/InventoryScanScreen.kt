package com.example.demoapp.ui.inventory.scan

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoapp.ui.components.BackTopAppBar
import com.example.demoapp.ui.user_details.UserDetailsViewModel
import android.Manifest
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material.Button
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

@Composable
fun InventoryScanScreen(
    onBack: () -> Unit,
    onBarcodeScanned: (String) -> Unit,
    onAddManually: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
//    var scannedCode by remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackTopAppBar(
                onBack = onBack,
                text = "Scan qr code"
            )
        }
    ) { paddingValues ->
        paddingValues
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            if (scannedCode.isNotEmpty()) {
//                Text("Scanned QR Code: $scannedCode")
//            }

            RequestCameraPermission {
                CameraPreview(
//                    onBarcodeScanned = { code -> scannedCode = code },
                    onBarcodeScanned = onBarcodeScanned,
                    onAddManually = onAddManually
                )
            }
        }
    }
}

@Composable
fun RequestCameraPermission(
    onPermissionGranted: @Composable () -> Unit
) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionGranted = granted
        }
    )

    LaunchedEffect(Unit) {
        when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
            PackageManager.PERMISSION_GRANTED -> permissionGranted = true
            else -> permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (permissionGranted) {
        onPermissionGranted()
    } else {
        Text("Permission to use camera is required.")
    }
}

@Composable
fun CameraPreview(
    onBarcodeScanned: (String) -> Unit,
    onAddManually: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val preview = Preview.Builder().build()

    var previewView by remember { mutableStateOf<androidx.camera.view.PreviewView?>(null) }

    val barcodeScanner = remember { ScannerAnalyzer(context, onBarcodeScanned) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                androidx.camera.view.PreviewView(ctx).also {
                    previewView = it
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                val cameraProvider = cameraProviderFuture.get()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                val cameraExecutor = Executors.newSingleThreadExecutor()

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(cameraExecutor, barcodeScanner)

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )

                preview.setSurfaceProvider(view.surfaceProvider)
            }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            )  {
                Button(onClick = onAddManually) {
                    Text(text = "Add manually")
                }
            }
        }
    }
}
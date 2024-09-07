package com.example.demoapp.ui.inventory.scan

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class ScannerAnalyzer(
    private val context: Context,
    private val onBarcodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {
                                val qrCode = barcode.displayValue ?: ""
                                Log.d("QRScanner", "Scanned QR Code: $qrCode")
                                onBarcodeScanned(qrCode)
                                break
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("QRScanner", "Error scanning QR Code", it)
                }
                .addOnCompleteListener {
                    imageProxy.close() // Zamknij obraz po przetworzeniu
                }
        } else {
            imageProxy.close()
        }
    }
}

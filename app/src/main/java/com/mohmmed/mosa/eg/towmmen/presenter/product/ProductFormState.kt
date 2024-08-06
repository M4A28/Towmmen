package com.mohmmed.mosa.eg.towmmen.presenter.product

import android.net.Uri
import java.util.Date

data class ProductFormState(
    var name: String = "",
    var barcode: String = "",
    var description: String = "",
    var cost: String = "",
    var price: String = "",
    var category: String = "",
    var stockQuantity: String = "",
    var unit: String = "",
    var imageUri: Uri? = null,
    var manfDate: Date = Date(),
    var expDate: Date = Date()
)
package com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser


data class AppEntryUseCases(
    val saveAppEntry: SaveAppEntry,
    val readAppEntry: ReadAppEntry,
    var saveLastInvoiceNumber: SaveLastInvoiceNumber,
    val readLastInvoiceNumber: ReadLastInvoiceNumber
)

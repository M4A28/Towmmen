package com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser

import com.mohmmed.mosa.eg.towmmen.domin.localusermanger.LocalUserManger
import kotlinx.coroutines.flow.Flow

class ReadLastInvoiceNumber (
    private val localUserManger: LocalUserManger
) {
    operator fun invoke(): Flow<Int> {
        return localUserManger.readLastInvoiceNumber()
    }
}
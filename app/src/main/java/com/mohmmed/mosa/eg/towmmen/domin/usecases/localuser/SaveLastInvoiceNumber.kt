package com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser

import com.mohmmed.mosa.eg.towmmen.domin.localusermanger.LocalUserManger

class SaveLastInvoiceNumber (
    private val localUserManger: LocalUserManger
) {
    suspend operator fun invoke(last: Int) {
        localUserManger.saveLastInvoiceNumber(last)
    }
}
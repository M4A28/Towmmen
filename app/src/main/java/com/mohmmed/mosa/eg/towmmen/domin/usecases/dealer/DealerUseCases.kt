package com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer

data class DealerUseCases(
    val upsertDealer: UpsertDealer,
    val deleteDealer: DeleteDealer,
    val getAllDealers: GetAllDealers,
    val getDealerById: GetDealerById,
) {
}
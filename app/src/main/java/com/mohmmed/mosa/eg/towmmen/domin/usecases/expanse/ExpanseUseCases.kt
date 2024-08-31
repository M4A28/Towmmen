package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

data class ExpanseUseCases(
    val upsertExpanse: UpsertExpanse,
    val deleteExpanse: DeleteExpanse,
    val getAllExpanse: GetAllExpanse,
    val getExpanseInRange: GetExpanseInRange,
    val getExpansePerMonth: GetExpansePerMonth,
    val getAvgExpanse: GetAvgExpanse,
    val getAvgExpansePerMonth: GetAvgExpansePerMonth,
    val clearExpanseData: ClearExpanseData,
    val getExpansePerWeek: GetExpansePerWeek,
    val getExpansePerDay: GetExpansePerDay
)
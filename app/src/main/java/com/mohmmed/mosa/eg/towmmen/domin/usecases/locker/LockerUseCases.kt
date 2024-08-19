package com.mohmmed.mosa.eg.towmmen.domin.usecases.locker

data class LockerUseCases(
    val upsertLocker: UpsertLocker,
    val deleteLocker: DeleteLocker,
    val getAllLocker: GetAllLocker,
    val getSubLocker: GetSubLocker,
    val getAddLocker: GetAddLocker
)
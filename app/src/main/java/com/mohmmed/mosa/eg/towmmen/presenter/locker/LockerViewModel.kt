package com.mohmmed.mosa.eg.towmmen.presenter.locker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.LockerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockerViewModel @Inject constructor(
    private val lockerUseCases: LockerUseCases
) : ViewModel() {

    fun upsertLockerTransaction(locker: Locker){
        viewModelScope.launch(Dispatchers.IO) {
            lockerUseCases.upsertLocker(locker)
        }

    }
    fun deleteLockerTransaction(locker: Locker){
        viewModelScope.launch(Dispatchers.IO) {
            lockerUseCases.deleteLocker(locker)
        }
    }


    val lockersTransaction: StateFlow<List<Locker>> =
        lockerUseCases.getAllLocker()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    // todo
/*    fun getSubLockerTransactions(): Flow<List<Locker>>{

    }

    fun getADDLockerTransactions(): Flow<List<Locker>>{

    }*/
}
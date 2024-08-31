package com.mohmmed.mosa.eg.towmmen.presenter.expanse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerPeriod
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.ExpanseUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.LockerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.LockerSettingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class ExpanseViewModel @Inject constructor(
    private val expanseUseCases: ExpanseUseCases,
    private val lockerSetting: LockerSettingUseCases,
    private val lockerUseCases: LockerUseCases
): ViewModel(){


    val canSaveExpanseToDb: StateFlow<Boolean> = lockerSetting.getCanSaveExpanseToDb()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun upsertLockerTransaction(locker: Locker){
        viewModelScope.launch(Dispatchers.IO) {
            lockerUseCases.upsertLocker(locker)
        }

    }


    fun upsertExpanse(expanse: Expanse){
        viewModelScope.launch(Dispatchers.IO) {
            expanseUseCases.upsertExpanse(expanse)
        }
    }

    fun deleteExpanse(expanse: Expanse){
        viewModelScope.launch(Dispatchers.IO) {
            expanseUseCases.deleteExpanse(expanse)
        }
    }


    fun getAllExpanse(): Flow<List<Expanse>>{
        return expanseUseCases.getAllExpanse()
    }


    val expanseAvg: StateFlow<Double?> = expanseUseCases.getAvgExpanse()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun getExpanseInRange(start: Date, end: Date): Flow<List<Expanse>>{
        return expanseUseCases.getExpanseInRange(start, end)
    }


    fun getExpansePerMonth(): Flow<List<ExpansePerPeriod>>{
        return expanseUseCases.getExpansePerMonth()
    }

    val avgExpansePerMonth: StateFlow<Double?> = expanseUseCases
        .getAvgExpansePerMonth()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun clearExpanseData(){
        viewModelScope.launch (Dispatchers.IO){
            expanseUseCases.clearExpanseData()
        }
    }


}
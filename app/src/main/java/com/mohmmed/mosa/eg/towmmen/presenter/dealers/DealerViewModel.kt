package com.mohmmed.mosa.eg.towmmen.presenter.dealers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.DealerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealerViewModel @Inject constructor(
    private val dealerUseCase: DealerUseCases
) : ViewModel(){

    fun upsertDealer(dealer: Dealer){
        viewModelScope.launch(Dispatchers.IO) {
            dealerUseCase.upsertDealer(dealer)
        }
    }

    fun getDealerById(dealerId: Int): Flow<Dealer> {
        return dealerUseCase.getDealerById(dealerId)

    }


    fun deleteDealer(dealer: Dealer){
        viewModelScope.launch(Dispatchers.IO) {
            dealerUseCase.deleteDealer(dealer)
        }
    }


    val allDealers : StateFlow<List<Dealer>> = dealerUseCase
        .getAllDealers()
        .stateIn(viewModelScope,
            SharingStarted.Eagerly, emptyList())

}
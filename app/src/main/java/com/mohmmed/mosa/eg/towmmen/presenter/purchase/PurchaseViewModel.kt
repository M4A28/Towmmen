package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import androidx.lifecycle.ViewModel
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.PurchaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
        private val purchaseUseCases: PurchaseUseCases
    ): ViewModel() {


}
package com.mohmmed.mosa.eg.towmmen.presenter.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val productRepositoryImp: ProductRepositoryImp
): ViewModel() {

    fun insertNewProduct(product: Product){
        viewModelScope.launch {
            productRepositoryImp.addNewProduct(product)
        }
    }

    val allProduct = productRepositoryImp.getAllProducts()
}
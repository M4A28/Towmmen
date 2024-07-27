package com.mohmmed.mosa.eg.towmmen.presenter.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
  private val productUseCase: ProductUseCases
) : ViewModel() {

    val products = productUseCase.getALlProducts()


    fun getAllProductCost(): Flow<Double?>{
        return productUseCase.totalProductsCost()
    }

    fun addNewProduct(product: Product){
        viewModelScope.launch {
            productUseCase.addNewProduct(product)
        }

    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            productUseCase.deleteProduct(product)
        }

    }

    fun updateProduct(product: Product){
        viewModelScope.launch {
            productUseCase.updateProduct(product)
        }
    }

    fun searchProduct(name: String): Flow<List<Product>> {
        return productUseCase.getProduct(name)
    }

    fun getProductsCount(): Flow<Int?>{
        return productUseCase.getProductsCount()
    }



}
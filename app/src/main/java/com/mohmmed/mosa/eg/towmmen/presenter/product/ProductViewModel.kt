package com.mohmmed.mosa.eg.towmmen.presenter.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.CategoryCount
import com.mohmmed.mosa.eg.towmmen.data.module.MonthlyAvgPrice
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
  private val productUseCase: ProductUseCases
) : ViewModel() {

    private val _expiringProducts = MutableStateFlow<List<Product>>(emptyList())
    val expiringProducts: StateFlow<List<Product>> = _expiringProducts

    val products = productUseCase.getALlProducts()


    fun getAllProductCost(): Flow<Double?>{
        return productUseCase.totalProductsCost()
    }


    fun getProductExpiredIn2Week(){
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val today = calendar.time
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            val oneWeekLater = calendar.time

            getProductsExpiringBetween(today, oneWeekLater).collect { products ->
                _expiringProducts.value = products
            }

        }

    }

    fun getProductsExpiringBetween(startDate: Date, endDate: Date): Flow<List<Product>>{
        return productUseCase.getProductsExpiringBetween(startDate, endDate)
    }

    fun addNewProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.addNewProduct(product)
        }

    }

    fun deleteProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
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

    fun getProductByBarcode(barcode: String):Flow<Product>{
        return productUseCase.getProductByBarcode(barcode)
    }

    fun getProductsCount(): Flow<Int?>{
        return productUseCase.getProductsCount()
    }

    fun getAveragePrice(): Flow<Double?> {
        return productUseCase.getAveragePrice()
    }

    fun getTotalStock(): Flow<Int?> {
        return productUseCase.getTotalStock()
    }

    fun getProductCountByCategory(): Flow<List<CategoryCount>> {
        return productUseCase.getProductCountByCategory()
    }

    fun getMaxPrice(): Flow<Double?> {
        return productUseCase.getMaxPrice()
    }

    fun getMinPrice(): Flow<Double?> {
        return productUseCase.getMinPrice()
    }

    fun getAveragePriceByMonth(): Flow<List<MonthlyAvgPrice>> {
        return productUseCase.getAveragePriceByMonth()
    }


}
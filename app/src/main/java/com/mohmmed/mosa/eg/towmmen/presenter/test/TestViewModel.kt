package com.mohmmed.mosa.eg.towmmen.presenter.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.javafaker.Faker
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TestViewModel @Inject constructor(
    private val productRepositoryImp: ProductRepositoryImp,
    private val customerRepositoryImp: CustomerRepositoryImp,
    private val invoiceRepositoeyImp: InvoiceRepositoryImp
): ViewModel() {

    fun upsertInvoice(invoice: Invoice){
        viewModelScope.launch {
            invoiceRepositoeyImp.upsertInvoice(invoice)
        }
    }

    fun getProducatById(id: String): Flow<Product>{
        return productRepositoryImp.getProductByBarcode(id)
    }
    fun getAllCustomres(): Flow<List<Customer>>{
        return customerRepositoryImp.getAllCustomers()
    }


    fun updateProducat(product: Product){
        viewModelScope.launch {
            productRepositoryImp.updateProduct(product)
        }
    }

    fun insertFullInvoice(invoice: Invoice, items: List<InvoiceItem>){
        viewModelScope.launch {
            invoiceRepositoeyImp.upsertInvoice(invoice)
            invoiceRepositoeyImp.upsertInvoiceItems(items)
        }
    }

    fun insertNewProduct(product: Product){
        viewModelScope.launch {
            productRepositoryImp.addNewProduct(product)
        }
    }

    val allProduct = productRepositoryImp.getAllProducts()
    fun makeFakeProduct(count: Int){
        val fake = Faker()
        for(i in 0..count){
            viewModelScope.launch {
            val cost =Random.nextDouble(1.0, 100.0)
                productRepositoryImp.addNewProduct(Product(
                    name = fake.commerce().productName(),
                    barcode = fake.code().isbn10(),
                    price = cost * 1.15,
                    cost = cost,
                    description = fake.company().profession(),
                    imagePath = "",
                    category = fake.company().profession(),
                    stockQuantity = fake.number().numberBetween(1, 100),
                    unit = "unit",
                    manufactureDate = fake.date().past(100, TimeUnit.DAYS),
                    expireDate = fake.date().future(400, TimeUnit.DAYS),
                    createdAt = Date(),
                    updatedAt = Date()

                ))
            }
        }
    }
    fun makeFakeCustomer(count: Int){
        val fake = Faker()
        for(i in 0..count){
            viewModelScope.launch {
                customerRepositoryImp.addNewCustomer(
                    Customer(
                        name = fake.name().fullName(),
                        email = fake.internet().emailAddress(),
                        phone = fake.phoneNumber().phoneNumber(),
                        address = fake.address().fullAddress(),
                        registrationDate = Date() ,
                        lastPurchaseDate = Date()
                    )
                )
            }
        }
    }



}
package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

data class ProductUseCases(
    val deleteProduct: DeleteProduct,
    val addNewProduct: AddNewProduct,
    val updateProduct: UpdateProduct,
    val upsertProduct: UpsertProduct,
    val getALlProducts: GetAllProducts,
    val getProduct: GetProduct,
    val productBarcode: ProductBarcode,
    val getProductsCount: ProductsCount,
    val totalProductsCost:TotalProductsCost,
    val getProductsExpiringBetween: GetProductsExpiringBetween,
    val getProductByBarcode: GetProductByBarcode
)


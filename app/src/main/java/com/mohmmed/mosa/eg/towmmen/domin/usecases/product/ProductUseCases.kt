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
    val getProductByBarcode: GetProductByBarcode,
    val getAveragePrice: GetAveragePrice,
    val getTotalStock: GetTotalStock,
    val getProductCountByCategory: GetProductCountByCategory,
    val getMinPrice: GetMinPrice,
    val getMaxPrice: GetMaxPrice,
    val getAveragePriceByMonth: GetAveragePriceByMonth,
    val getProductById: GetProductById
)


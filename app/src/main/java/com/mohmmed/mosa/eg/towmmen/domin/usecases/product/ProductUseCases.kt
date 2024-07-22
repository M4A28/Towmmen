package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

data class ProductUseCases(
    val deleteProduct: DeleteProduct,
    val addNewProduct: AddNewProduct,
    val updateProduct: UpdateProduct,
    val getALlProducts: GetAllProducts,
    val getProduct: GetProduct,
    val getProductsCount: ProductsCount
)


package com.mohmmed.mosa.eg.towmmen.presenter.profit

import androidx.annotation.StringRes
import com.mohmmed.mosa.eg.towmmen.R

enum class Tabs(
    @StringRes
    val  text: Int,
    val icon: Int
) {
    InvoiceScreen(text = R.string.invoice, icon = R.drawable.description),

    TopSellingScreen(text = R.string.top_selling, icon = R.drawable.shopping),

    ExpanseScreen(text = R.string.expanse, icon = R.drawable.payment),



}
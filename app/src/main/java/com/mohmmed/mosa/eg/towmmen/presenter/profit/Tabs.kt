package com.mohmmed.mosa.eg.towmmen.presenter.profit

import androidx.annotation.StringRes
import com.mohmmed.mosa.eg.towmmen.R

enum class Tabs(
    @StringRes
    val  text: Int
) {
    InvoiceScreen(text = R.string.invoice),

    TopSellingScreen(text = R.string.top_selling),

    ProfitScreen(text = R.string.profit),

    StatisticScreen(text = R.string.statistic),


}
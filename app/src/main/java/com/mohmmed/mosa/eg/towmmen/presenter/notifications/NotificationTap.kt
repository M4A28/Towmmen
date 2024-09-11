package com.mohmmed.mosa.eg.towmmen.presenter.notifications

import androidx.annotation.StringRes
import com.mohmmed.mosa.eg.towmmen.R

enum class NotificationTap(@StringRes val  text: Int,
                               val icon: Int
) {
    ExpProductScreen(text = R.string.expierd_screen, icon = R.drawable.notifications),

    OutOfStockScreen(text = R.string.out_of_stock, icon = R.drawable.box),

}
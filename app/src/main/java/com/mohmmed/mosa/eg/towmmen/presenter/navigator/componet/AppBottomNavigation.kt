package com.mohmmed.mosa.eg.towmmen.presenter.navigator.componet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.ui.theme.IconSize1
import com.mohmmed.mosa.eg.towmmen.ui.theme.MediumPadding3
import com.mohmmed.mosa.eg.towmmen.ui.theme.SmallPadding
import com.mohmmed.mosa.eg.towmmen.ui.theme.TowmmenTheme

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    @StringRes val text: Int
)

@Composable
fun  AppBottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MediumPadding3,
                start = SmallPadding,
                end = SmallPadding
            )
            .clip(RoundedCornerShape(MediumPadding3)),
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(selected = index == selected,
                onClick = { onClick(index) }, icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(IconSize1)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.text),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.primaryContainer),
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun BottomParagraphPreview() {
    TowmmenTheme {

        AppBottomNavigation(
            items =    listOf(
                BottomNavigationItem(icon = R.drawable.home, text = R.string.home),
                BottomNavigationItem(icon = R.drawable.person, text = R.string.customers),
                BottomNavigationItem(icon = R.drawable.shopping_cart, text = R.string.products),
                BottomNavigationItem(icon = R.drawable.notes, text = R.string.notes),
            ),
            selected = 0,
            onClick = {}
        )
    }

}
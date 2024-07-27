package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Customer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.TextWithIcon
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString

@Composable
fun FullCustomerInfoScreen(navController: NavHostController) {
    val customerViewModel: CustomerViewModel = hiltViewModel()
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let {customer ->
            FullCustomerInfoContent(
                customer = customer,
                onClickDeleteClick = {
                    customerViewModel.deleteCustomer(customer)
                    navController.popBackStack()
                },
                onClickEditClick = {

                }
            )
        }

}

@Composable
fun FullCustomerInfoContent(
    modifier: Modifier = Modifier,
    customer: Customer,
    onClickDeleteClick:  (Customer)  -> Unit,
    onClickEditClick: (Customer)  -> Unit
) {

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp)
            .background(MaterialTheme.colorScheme.surface),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ){

            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 16.dp)
            ){
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { onClickEditClick(customer) }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        painter = painterResource(id = R.drawable.edit ),
                        contentDescription = null
                    )
                }

                Spacer(Modifier.width(8.dp))

                // delete
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = { onClickDeleteClick(customer) }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onError,
                        painter = painterResource(id = R.drawable.delete ),
                        contentDescription = null
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
        ) {

            TextWithIcon(
                icon = R.drawable.person,
                text = String.format(stringResource(R.string.name), customer.name),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.email,
                text = String.format(stringResource(R.string.email_2), customer.email),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.phone,
                text = String.format(stringResource(id = R.string.phone_2), customer.phone),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.location_pin,
                text = String.format(stringResource(id = R.string.address_2), customer.address),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.calendar_month,
                text = String.format(
                    stringResource(id = R.string.reg_data),
                    dateToString(customer.registrationDate, "yyyy/MM/dd")
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

        }


    }
}

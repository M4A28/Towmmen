package com.mohmmed.mosa.eg.towmmen.presenter.dealers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import java.util.Date

@Composable
fun AddDealerDialog(onAddClick:(Dealer)-> Unit,
                    showDialog:(Boolean) -> Unit
) {
    var dealerName by remember { mutableStateOf("") }
    var dealerPhone by remember { mutableStateOf("") }
    var dealerAddres by remember { mutableStateOf("") }
    var dealerNote by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Text(
                            text = stringResource(id = R.string.add_new_dealer),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Icon(imageVector = Icons.Filled.Close,
                            contentDescription = null ,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    showDialog(false)
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dealerName,
                        onValueChange = { dealerName = it },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.person),
                                contentDescription = null ,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.dealer_name))
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dealerPhone,
                        onValueChange = { dealerPhone = it },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.phone),
                                contentDescription = null ,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        placeholder = {
                            Text(text = stringResource(id = R.string.phone))
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dealerAddres,
                        onValueChange = { dealerAddres = it },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.location_pin),
                                contentDescription = null ,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.address))
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dealerNote,
                        onValueChange = { dealerNote = it },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.notes),
                                contentDescription = null ,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.notes))
                        }
                    )

                    Box(modifier = Modifier.padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Button(onClick = {

                            if(dealerName.isNotEmpty() &&
                                dealerPhone.isNotEmpty() &&
                                dealerAddres.isNotEmpty()){

                                onAddClick(Dealer(
                                    dealerId = 0,
                                    dealerName = dealerName,
                                    createDate = Date(),
                                    dealerPhoneNumber = dealerPhone,
                                    dealerAddress = dealerAddres,
                                    dealerNote = dealerNote
                                ))
                                showDialog(false)
                            }
                        }) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add "
                            )

                            Text(
                                text = stringResource(id = R.string.add),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}
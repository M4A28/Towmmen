package com.mohmmed.mosa.eg.towmmen.presenter.expanse

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DeleteConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.SaveEditDropDownMenu
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToTab
import com.mohmmed.mosa.eg.towmmen.util.EXPANSE_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency
import java.util.Date
import java.util.Locale


@Composable
fun ExpanseScreen(navController: NavHostController){
    val expanseViewModel: ExpanseViewModel = hiltViewModel()
    val expanseItems by expanseViewModel
        .getAllExpanse()
        .collectAsState(initial = emptyList())
    val context = LocalContext.current

    ExpanseContent(
        expanseItem = expanseItems,
        onAddFapClick = { navigateToTab(navController, Route.AddExpanseScreen.route) },
        onEditClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(EXPANSE_KEY, it)
            navController.navigate(Route.EditExpanseScreen.route)
        },
        onDeleteClick = {
            expanseViewModel.deleteExpanse(it)
            Toast.makeText(context,
                context.getString(R.string.expanse_deleted),
                Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun ExpanseContent(
    expanseItem: List<Expanse>,
    onAddFapClick:() -> Unit,
    onEditClick: (Expanse) -> Unit,
    onDeleteClick: (Expanse) -> Unit
){
    var showDeleteDialog by remember{ mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddFapClick() }) {
                Icon(painter = painterResource(id = R.drawable.add),
                    contentDescription = null)
            }
        }
    ) { paddingValue ->
        if(expanseItem.isNotEmpty()){
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValue.calculateTopPadding(), end = 8.dp, start = 8.dp)
            ) {

                items(expanseItem, key = {it.expanseId}){ expanse ->
                    ExpanseItemCard(
                        expanse = expanse,
                        onDeleteClick = {showDeleteDialog = !showDeleteDialog },
                        onEditClick = {onEditClick(expanse)}
                    )
                    if(showDeleteDialog){
                        DeleteConfirmationDialog(
                            title = stringResource(R.string.delete_expanse),
                            massage = stringResource(R.string.warring_delete_expanse),
                            onConfirm = {
                                onDeleteClick(expanse)
                                showDeleteDialog = !showDeleteDialog
                            },
                            onDismiss = {showDeleteDialog = !showDeleteDialog }
                        )
                    }

                }
            }
        } else{
            EmptyScreen(massage = stringResource(id = R.string.no_expanse),
                icon = R.drawable.cash, alphaAnim = 0.3f)
        }

    }
}


@Composable
fun ExpanseItemCard(
    expanse: Expanse,
    onDeleteClick:(Expanse) -> Unit,
    onEditClick :(Expanse) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = expanse.expanse, style = MaterialTheme.typography.titleMedium)
                Text(text = formatCurrency(expanse.amount), style = MaterialTheme.typography.titleLarge)
                SaveEditDropDownMenu(deleteMessage = stringResource(id = R.string.expanse_deleted),
                    onDelete = {onDeleteClick(expanse)},
                    onEdit = {onEditClick(expanse)})
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = dateToString(expanse.payDate,
                "yyyy MMMM dd",
                        locale = Locale.getDefault()), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExpansePreview(){
    ExpanseItemCard(expanse = Expanse(
        expanseId = 0,
        expanse = "Eele",
        payDate = Date(),
        amount = 120.0
    ), onDeleteClick = {}, onEditClick = {})
}





package com.mohmmed.mosa.eg.towmmen.presenter.dealers

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DealerCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBarWithSuggestions
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.util.DEALER_KEY


@Composable
fun DealersScreen(navHostController: NavHostController) {
    val dealerViewModel: DealerViewModel = hiltViewModel()
    val dealers by dealerViewModel.allDealers.collectAsState()
    var showAddDealerDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    DealersContent(
        dealers = dealers,
        onFapClick = {showAddDealerDialog = !showAddDealerDialog},
        onCallClick = {
            val phoneUri = Uri.fromParts("tel", it.dealerPhoneNumber, null)
            val intent = Intent(Intent.ACTION_DIAL, phoneUri)
            intent.also{
                if(it.resolveActivity(context.packageManager) != null){
                    context.startActivity(it)
                }
            }
        },
        onDealerClick = {
            navHostController.currentBackStackEntry?.savedStateHandle?.set(DEALER_KEY, it)
            navHostController.navigate(Route.DealerFullInfoScreen.route)
        }

    )
    if(showAddDealerDialog){
        AddDealerDialog(
            onAddClick = {
                dealerViewModel.upsertDealer(it)
                Toast.makeText(context,
                    context.getString(R.string.dealer_has_been_add), Toast.LENGTH_LONG).show()

            },
            showDialog = {showAddDealerDialog = it})

    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealersContent(
    dealers: List<Dealer>,
    onFapClick: () -> Unit,
    onCallClick: (Dealer) -> Unit,
    onDealerClick: (Dealer) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.dealers))
                }

            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onFapClick() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { paddingValue ->
        
        LazyColumn(modifier = Modifier.padding(top = paddingValue.calculateTopPadding())) {
            
            item{
                // search bar
                ModernSearchBarWithSuggestions(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    suggestions = dealers.map { it.dealerName }.filter{ it.contains(searchQuery, ignoreCase = true) },
                    onSuggestionSelected = {searchQuery = it },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )

            }
            items(dealers.filter {
                it.dealerName.contains(searchQuery, ignoreCase = true)
            }, key = {
                it.dealerId
            }){
                if(dealers.isNotEmpty()){
                    DealerCard(
                        dealer = it,
                        onPhoneClick = {onCallClick(it)},
                        onClick = {onDealerClick(it)}
                    )
                }else{
                    EmptyScreen(massage = stringResource(R.string.no_dealers_to_show),
                        icon = R.drawable.delars,
                        alphaAnim = 0.3f)
                }

            }


        }
    }


}
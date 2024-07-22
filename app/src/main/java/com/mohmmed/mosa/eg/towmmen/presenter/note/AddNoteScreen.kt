package com.mohmmed.mosa.eg.towmmen.presenter.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Note
import com.mohmmed.mosa.eg.towmmen.presenter.comman.OutlinedExposedDropdownMenu
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    modifier: Modifier = Modifier,
    onAddClick: (Note) -> Unit
) {
    var titel by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var importenc by remember { mutableStateOf("") }
    val options = listOf("High", "Mid", "Low")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(text = "Add New Note")
                })
        }
    ) { padding ->
        val topPadding = padding.calculateTopPadding()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = topPadding, end = 4.dp, start = 4.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = titel,
                onValueChange = { titel = it },
                label = { Text("titel") },
                leadingIcon = { Icon(Icons.Filled.Create, contentDescription = "Product Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.description), contentDescription = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 8.dp)
            )

            OutlinedExposedDropdownMenu(
                options = options,
                selectedOption = options[0],
                label = "importenc",
                onValueChangeEvent = {
                    importenc = it
                })
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if(titel.isNotEmpty()){
                        onAddClick(Note(
                                title = titel,
                                note = note,
                                date = Date(),
                                importance = getImportance(importenc) ,
                                lastModified = Date()
                            )
                        )
                        // clearData
                        titel = ""
                        note = ""
                        importenc = ""
                    }
                },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Note"
                )
                Text(
                    text = "Add Note",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }

}


fun getImportance(importance: String): Int{
    return when(importance){
        "Low" -> 3
        "Mid" -> 2
        "High" -> 1
        else -> 0
    }
}
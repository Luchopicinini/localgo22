package com.localgo.localgo2.ui.sitio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.localgo.localgo2.ui.viewmodel.SitioTuristicoViewModel
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SitioListScreen(viewModel: SitioTuristicoViewModel = viewModel()) {
    val sitios by viewModel.sitios.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Sitios Turísticos") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn {
                items(sitios) { sitio ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(sitio.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(sitio.descripcion, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

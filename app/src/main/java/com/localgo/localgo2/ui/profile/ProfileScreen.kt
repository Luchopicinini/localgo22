package com.localgo.localgo2.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.localgo.localgo2.data.UserData
import com.localgo.localgo2.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    vm: ProfileViewModel = viewModel()
) {
    // estado global que viene del ViewModel (DataStore + memoria)
    val userData by vm.userState.collectAsState()

    // estados locales editables en la UI
    var editMode by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(userData.nombre) }
    var email by remember { mutableStateOf(userData.email) }
    var ubicacion by remember { mutableStateOf(userData.ubicacion) }
    var fotoUri by remember { mutableStateOf(userData.fotoUri) }

    // picker de imagen (galería)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { picked: Uri? ->
        if (picked != null) {
            fotoUri = picked.toString()
        }
    }

    // si cambian los datos globales (ej: después de guardar), refrescamos campos
    LaunchedEffect(userData) {
        if (!editMode) {
            nombre = userData.nombre
            email = userData.email
            ubicacion = userData.ubicacion
            fotoUri = userData.fotoUri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // FOTO DE PERFIL
        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            if (fotoUri.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(fotoUri),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Sin foto",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Cambiar foto
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cambiar foto de perfil")
        }

        Spacer(Modifier.height(24.dp))

        // CAMPOS
        ProfileField(
            label = "Nombre",
            value = nombre,
            enabled = editMode,
            onChange = { nombre = it }
        )

        Spacer(Modifier.height(12.dp))

        ProfileField(
            label = "Correo",
            value = email,
            enabled = editMode,
            onChange = { email = it }
        )

        Spacer(Modifier.height(12.dp))

        ProfileField(
            label = "Ubicación",
            value = ubicacion,
            enabled = editMode,
            onChange = { ubicacion = it }
        )

        Spacer(Modifier.height(24.dp))

        // BOTONES (Editar / Guardar)
        if (editMode) {
            Button(
                onClick = {
                    // Guardar en DataStore
                    vm.guardarCambios(
                        UserData(
                            nombre = nombre,
                            email = email,
                            ubicacion = ubicacion,
                            fotoUri = fotoUri
                        )
                    )
                    editMode = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
        } else {
            Button(
                onClick = { editMode = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar perfil")
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { onLogout() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar sesión", fontWeight = FontWeight.Bold)
        }
    }
}

// Composable reutilizable para cada campo
@Composable
private fun ProfileField(
    label: String,
    value: String,
    enabled: Boolean,
    onChange: (String) -> Unit
) {
    if (enabled) {
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

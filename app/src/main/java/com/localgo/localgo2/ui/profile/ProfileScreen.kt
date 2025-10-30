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
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Datos editables
    var nombre by remember { mutableStateOf("Luciano Picinini") }
    var email by remember { mutableStateOf("luciano@example.com") }
    var ubicacion by remember { mutableStateOf("Santiago, Chile") }
    var editMode by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // 🟣 Foto de perfil
        Surface(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            color = Color.Transparent
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
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
                    Text("Sin foto", color = Color.White)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 🧍 Nombre editable
        if (editMode) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        // 📨 Email editable
        if (editMode) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(email, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(8.dp))

        // 📍 Ubicación editable
        if (editMode) {
            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("Ubicación") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(ubicacion, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(24.dp))

        // 🧩 Botones
        if (editMode) {
            Button(
                onClick = { editMode = false },
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

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cambiar foto de perfil")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { onLogout() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar sesión")
        }
    }
}

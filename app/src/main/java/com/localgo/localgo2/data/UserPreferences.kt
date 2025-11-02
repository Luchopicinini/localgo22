package com.localgo.localgo2.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Creamos una extensión del Context para acceder al DataStore
val Context.userDataStore by preferencesDataStore(name = "user_prefs")

// 2. Claves que vamos a guardar
private object UserKeys {
    val NOMBRE = stringPreferencesKey("nombre")
    val EMAIL = stringPreferencesKey("email")
    val UBICACION = stringPreferencesKey("ubicacion")
    val FOTO_URI = stringPreferencesKey("foto_uri")
}

// 3. Clase que expone funciones para leer y guardar
class UserPreferences(private val context: Context) {

    // Leer TODOS los datos del usuario como un data class
    val userData: Flow<UserData> = context.userDataStore.data.map { prefs: Preferences ->
        UserData(
            nombre = prefs[UserKeys.NOMBRE] ?: "Luciano Picinini",
            email = prefs[UserKeys.EMAIL] ?: "luciano@example.com",
            ubicacion = prefs[UserKeys.UBICACION] ?: "Santiago, Chile",
            fotoUri = prefs[UserKeys.FOTO_URI] ?: "" // string vacío si no hay foto
        )
    }

    // Guardar datos editados
    suspend fun saveUserData(data: UserData) {
        context.userDataStore.edit { prefs ->
            prefs[UserKeys.NOMBRE] = data.nombre
            prefs[UserKeys.EMAIL] = data.email
            prefs[UserKeys.UBICACION] = data.ubicacion
            prefs[UserKeys.FOTO_URI] = data.fotoUri
        }
    }
}

// 4. Este data class representa al usuario actual en la app
data class UserData(
    val nombre: String,
    val email: String,
    val ubicacion: String,
    val fotoUri: String
)

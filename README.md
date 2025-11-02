1.  Caso elegido y alcance
   
Caso: Plataforma de turismo local (basado en los casos “TravelGo”

Alcance EP3: Diseño/UI, validaciones, navegación, estado, persistencia, recursos nativos (ubicación y foto), animaciones y consumo de API real.

Descripción:
LocalGo2 es una aplicación móvil desarrollada con Kotlin + Jetpack Compose, que permite al usuario explorar lugares turísticos, visualizar su ubicación en un mapa interactivo y consultar información en tiempo real como el clima actual.
Incluye un sistema de login validado, almacenamiento local de perfil con DataStore y manejo de permisos de ubicación.


2.  Requisitos y ejecución
Stack principal:
- Android Studio Giraffe / Koala 🧩
- Kotlin (Jetpack Compose, ViewModel, Coroutines)
- Retrofit (API REST Open-Meteo / Xano)
- Google Maps Compose
-DataStore Preferences

Instalación:
git clone https://github.com/lucianopicinini/LocalGo2.git
cd LocalGo2

Ejecución:
- Abrir en Android Studio
- Sincronizar Gradle
- Ejecutar en emulador o dispositivo físico (Android 8+).
- Permitir acceso a ubicación cuando se solicite.

3. Arquitectura y flujo
Patrón: MVVM (Model - View - ViewModel)

ui/
 ├── home/ Pantalla principal (clima + lugares)
 ├── map/ Mapa interactivo con marcadores
 ├── profile/ Perfil de usuario (DataStore)
 ├── screens.login/ Pantalla de login validada
 ├── sitio/ Listado de sitios turísticos
 ├── tips/ Consejos y recomendaciones
 ├── theme/ Colores, tipografía y estilos globales
 ├── viewmodel/ Controladores (Weather, Perfil, etc.)
 └── RootScaffold.kt Contenedor principal con navegación inferior

 Gestión de estado:
- Estados locales (remember, mutableStateOf) para formularios y selección de lugares.
- Estados globales gestionados con ViewModel (WeatherViewModel, ProfileViewModel).
- Sincronización UI ↔ Data con StateFlow y collectAsState().

Navegación:
- Implementada con NavHost y NavController.
- Estructura tipo Bottom Navigation: Home, Map, Tips, Profile.
- RootScaffold.kt maneja la barra inferior y el contenido activo.

4. Funcionalidades
Navegación
Flujo completo entre login, home, mapa, tips y perfil.
`AppNavGraph.kt`, `RootScaffold.kt`
      
 Formulario validado**       
 Login con verificación de “@” en email y mínimo 4 caracteres en contraseña.      
 `LoginScreen.kt`    
 
Gestión de estado     
Estados de carga, éxito y error sincronizados con la UI.                         
`WeatherViewModel.kt`                       

Persistencia local (CRUD) 
Guarda y carga datos del usuario con DataStore (nombre, email, ubicación, foto). 
`UserPreferences.kt`, `ProfileViewModel.kt` 

Recursos nativos         
Permisos de ubicación + (foto de perfil por galería).                            
`MapScreen.kt`, `ProfileScreen.kt`          

Animaciones con propósito
Transiciones en login con `AnimatedVisibility` y `fadeIn`.                      
`LoginScreen.kt`                            

Consumo de API real
API de clima (Open-Meteo) con Retrofit, manejo de error/red y estado.         
`WeatherRepository.kt`, `WeatherApi.kt`     

5. Endpoints
https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

Método
Ruta
Descripción                  
Requiere token                 

| ------ | ------------------------- | ---------------------------- | -------------------------------------- |
| POST   | `/auth/signup`            | Registro de usuario          | ❌                                      |
| POST   | `/auth/login`             | Inicio de sesión             | ❌                                      |
| GET    | `/auth/me`                | Obtiene usuario autenticado  | ✅ Header Authorization: Bearer <token> |
| GET    | `/weather` *(Open Meteo)* | Clima actual por coordenadas | ❌                                      |



6. User flows
Inicio de sesión

- Usuario ingresa su email y contraseña.

- Validación local (email con “@”, contraseña >4).

- Si es válido → navega a HomeScreen.

- Si no → muestra mensaje de error.

 Home

- Carga el clima de Santiago usando la API Open-Meteo.

- Muestra temperatura, velocidad del viento y estado de carga/error.

- Lista lugares turísticos destacados.

Mapa

- Solicita permiso de ubicación (ACCESS_FINE_LOCATION).

- Centra la cámara en tu posición.

- Muestra marcadores con lugares turísticos (LatLng + descripción).

- Al tocar un marcador → muestra Card con datos.

Perfil

- Carga datos guardados en DataStore (nombre, email, ubicación, foto).

- Permite seleccionar una foto desde la galería.

- Guarda los datos localmente (persistentes tras reinicio).

 Tips

- Sección de recomendaciones y consejos útiles.

- Interfaz visual con Card y LazyColumn.


7.  Manejo de errores y estados

- Estados: isLoading, error, weather sincronizados con when en Compose.

- Errores de red muestran mensaje en rojo (Text("Error al cargar clima ❌")).

- Permisos denegados → PermissionRequestDialog() personalizado.

- Uso de try-catch y suspendCancellableCoroutine en la obtención de ubicación.

8.  Recursos nativos
- Recurso	Uso	Permisos	Fallback
  -  Ubicación (GPS)	Centra mapa y muestra posición actual.	ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION	Diálogo de solicitud
- Galería (imagen perfil)	Seleccionar foto para perfil.	READ_EXTERNAL_STORAGE	Usa ícono por defecto si se deniega


9.  Animaciones con propósito

- AnimatedVisibility + fadeIn() + slideInVertically() en LoginScreen.kt.

- Transición suave al mostrar formulario (mejor feedback al usuario).

10. Persistencia local

Implementada con DataStore Preferences.

Guarda: nombre, email, ubicacion, fotoUri.

Persistente tras reinicio.

Reacción inmediata con Flows (collectAsState()).

11. Conclusión técnica

LocalGo2 demuestra un flujo completo de aplicación móvil moderna:
✔ UI coherente y adaptable (Material 3).
✔ Validaciones completas.
✔ Navegación estructurada con NavController.
✔ Persistencia local con DataStore.
✔ API real consumida con Retrofit.
✔ Recursos nativos (ubicación + galería).
✔ Animaciones funcionales.
✔ Arquitectura MVVM limpia y mantenible.

12. Autor

Nombre: Luciano Picinini
Institución: DUOC UC
Asignatura: DSY1105 – Desarrollo de Aplicaciones Móviles
Sección: EA2
Fecha: Noviembre 2025



















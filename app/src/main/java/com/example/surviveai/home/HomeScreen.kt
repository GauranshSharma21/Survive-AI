    package com.example.surviveai.home

    import androidx.compose.foundation.layout.Arrangement // Controls arrangement
    import androidx.compose.foundation.layout.Column // Vertical container
    import androidx.compose.foundation.layout.fillMaxSize // Fills available-space
    import androidx.compose.foundation.layout.fillMaxWidth // occupies the whole width of the screen
    import androidx.compose.foundation.layout.padding // Adds spacing
    import androidx.compose.foundation.rememberScrollState //
    import androidx.compose.foundation.verticalScroll //

    import androidx.compose.material.icons.Icons // Provides icons
    import androidx.compose.material.icons.filled.Settings // Provides Settings-icon

    import androidx.compose.material3.ExperimentalMaterial3Api // Enables experimental-APIs
    import androidx.compose.material3.Icon // Displays icons

    import androidx.compose.material3.IconButton // Creates clickable-button(contains icons)
    import androidx.compose.material3.MaterialTheme // Provides app theme
    import androidx.compose.material3.Scaffold // Provides screen-structure
    import androidx.compose.material3.Text // Displays text
    import androidx.compose.material3.TopAppBar // Creates top-bar


    import androidx.compose.runtime.Composable // Marks composable

    import androidx.compose.ui.Alignment // Controls alignment
    import androidx.compose.ui.Modifier // Modifies UI
    import androidx.compose.ui.unit.dp // Used for UI dimensions and spacing(e.g. control padding)

    import android.content.pm.PackageManager // checks whether a permission is granted

    import androidx.compose.runtime.getValue //allows reading compose state
    import androidx.compose.runtime.mutableStateOf //Creates compose state
    import androidx.compose.runtime.remember //Remembers states across recompositions
    import androidx.compose.runtime.setValue //Allows changing compose state

    import androidx.compose.ui.platform.LocalContext //Provides the current android context

    import androidx.core.content.ContextCompat //checks Android permissions

    import com.google.android.gms.location.FusedLocationProviderClient // Provides location services
    import com.google.android.gms.location.LocationServices // Provides the fused location provider

    import android.Manifest // provides android permission constants

    import androidx.activity.compose.rememberLauncherForActivityResult //creates a permission launcher
    import androidx.activity.result.contract.ActivityResultContracts //provides permission request  contracts


    import com.google.android.gms.location.Priority //Provides location accuracy priorities
    import com.google.android.gms.tasks.CancellationTokenSource //Creates a cancellation token



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SurviveAiHomeScreen(){

        val context = LocalContext.current // gives compose code access to location environment

        var locationPermissionGranted by remember{
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||

                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                )
        }
        val fusedLocationClient = remember{
            LocationServices.getFusedLocationProviderClient(context)
        }

        var locationStatus by remember{
            mutableStateOf("Location Unavailable")
        }

        var locationText by remember{
            mutableStateOf("")
        }

        fun getCurrentLocation() {

            // Check whether precise location permission is granted
            val fineGranted =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

            // Check whether approximate location permission is granted
            val coarseGranted =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

            // Stop if location permission is not available
            if (!fineGranted && !coarseGranted) {

                locationPermissionGranted = false

                locationStatus = "Permission denied"

                locationText = "Location permission required"

                return
            }

            // Tell the UI that we are getting the location
            locationStatus = "Getting Location..."

            locationText = "Getting your current location..."

            // Creates a cancellation token
            val cancellationTokenSource =
                CancellationTokenSource()

            // Requests the current device location
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
                .addOnSuccessListener { location ->

                    if (location != null) {

                        locationPermissionGranted = true

                        locationStatus = "Location Found"

                        locationText =
                            "Latitude: ${location.latitude}\n" +
                                    "Longitude: ${location.longitude}"

                    } else {

                        locationStatus = "Location Unavailable"

                        locationText =
                            "Unable to get current location"
                    }
                }
                .addOnFailureListener {

                    locationStatus = "Location Unavailable"

                    locationText =
                        "Unable to get current location"
                }
        }

        // Creates launcher that requests location permissions
        val locationPermissionLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->

                //check whether precise location permission was granted
                val fineLocationGranted =
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

                //check whether approximate location permission was granted
                val coarseLocationGranted =
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true


                if (fineLocationGranted || coarseLocationGranted) {

                    locationPermissionGranted = true

                    getCurrentLocation()

                } else {

                    locationPermissionGranted = false

                    locationStatus = "Permission denied"

                    locationText = "Location permission required"
                }
            }


        Scaffold( // Provides screen structure

            topBar = { // Defines top bar

                TopAppBar( // Creates top app bar

                    title = {

                        Column {

                            Text(
                                text = "SURVIVE AI",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = "Your intelligent survival companion",
                                style = MaterialTheme.typography.bodyMedium

                            )
                        }
                    },

                    actions = { // Adds action buttons

                        IconButton( // Creates clickable icon
                            onClick = { // Defines click action
                                // Settings will be added later
                            }
                        ) {

                            Icon( // Displays an icon
                                imageVector = Icons.Default.Settings,
                                // Specifies icon image

                                contentDescription = "Settings"
                                // Provides accessibility description
                            )
                        }
                    }
                )
            },



        ) { innerPadding ->

            Column(
                modifier = Modifier // Modifies UI element
                    .fillMaxSize() // Fills available space
                    .padding(innerPadding) // Applies inner padding
                    .verticalScroll(rememberScrollState()), //Enables Vertical Scroll

                horizontalAlignment = Alignment.CenterHorizontally // Centers horizontally

            ) {
                //LOCATION CARD IS STARTING HERE ->
                LocationCard(

                    locationStatus = locationStatus,
                     locationText = locationText,
                    locationPermissionGranted = locationPermissionGranted,

                    onEnableLocation = {

                        if (locationPermissionGranted) {

                            // Permission already exists, so get the location
                            getCurrentLocation()

                        } else {

                            // Permission does not exist, so request it
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    }
                )

            // DISASTER MODE  CARD ->
                DisasterModeCard(
                    onActivate = {
                        //Disaster mode functionality will be added here
                    }
                )

                //QUICK ACTIONS CARD ->
                QuickActions(
                    onSosClick = {
                        //SOS functionality will be added later
                    },

                    onFlashlightClick = {
                        //Flashlight functionality will be added later
                    },

                    onSirenClick = {
                        //Siren functionality will be added later
                    },

                    onMapsClick = {
                        //Maps functionality will be added later
                    }

                )

                //OFFLINE AI CARD ->
                OfflineAiCard(
                    onOpenAiClick = {
                        //offline Ai functionality will be added later
                    }
                )



        }


        }
    }



package com.example.jetpack.domain.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapManager
@Inject
constructor(
    @ApplicationContext val context: Context,
) {

    private var _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> = _latitude.asStateFlow()


    private var _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> = _longitude.asStateFlow()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {

                _longitude.value = location.longitude
                _latitude.value = location.latitude

                stopLocationUpdates()
                break
            }
        }
    }

    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()

    /*************************************************
     * Get the last known location - https://developer.android.com/develop/sensors-and-location/location/retrieve-current#last-known
     * The location object may be null in the following situations:
     *
     * + Location is turned off in the device settings. The result could be null even if the last location was
     * previously retrieved because disabling location also clears the cache.
     *
     * + The device never recorded its location, which could be the case of a new device or a device that has been restored to factory settings.
     *
     * + Google Play services on the device has restarted, and there is no active Fused Location Provider client that has requested
     * location after the services restarted. To avoid this situation you can create a new client and request location updates yourself.
     * For more information, see Receiving Location Updates
     */
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastKnownLocation(activity: Activity, gpsLauncher: ActivityResultLauncher<IntentSenderRequest>) {
        val disableFineLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
        val disableCoarseLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (disableFineLocation && disableCoarseLocation) return

        fusedLocationClient
            .lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location == null) {
                    Log.d("PHONG", "MapManger - getLastKnownLocation - addOnSuccessListener - location is null")
                    enableGPS(activity, gpsLauncher)
                } else {
                    Log.d("PHONG", "MapManger - getLastKnownLocation - addOnSuccessListener - latitude ${location.latitude} & longitude ${location.longitude}")
                    _longitude.value = location.longitude
                    _latitude.value = location.latitude
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                //Log.d("PHONG", "MapManger - getLastKnownLocation - addOnFailureListener: ${it.message} ")
            }
    }

    fun startLocationUpdates() {
        val disableFineLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
        val disableCoarseLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (disableFineLocation && disableCoarseLocation) return

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun stopLocationUpdates() {
        val disableFineLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
        val disableCoarseLocation = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED

        if (disableFineLocation && disableCoarseLocation) return

        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun enableGPS(activity: Activity, gpsLauncher: ActivityResultLauncher<IntentSenderRequest>) {
        Log.d("PHONG", "MapManger - enableGPS")
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            Log.d("PHONG", "MapManger - users have turned on GPS already")
            //receiveLocationUpdates()
        }

        task.addOnFailureListener { exception ->
            Log.d("PHONG", "MapManger - users have denied GPS already")
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    gpsLauncher.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
}
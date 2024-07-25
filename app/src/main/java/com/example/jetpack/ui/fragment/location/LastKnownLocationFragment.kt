package com.example.jetpack.ui.fragment.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager.TAG
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.lifecycleobserver.LocationPermissionLifecycleObserver
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class LastKnownLocationFragment : CoreFragment() {
    private lateinit var locationPermissionObserver: LocationPermissionLifecycleObserver
    private val viewModel: LocationViewModel by viewModels()

    private var showGPSDialog: Boolean by mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLocationObserver()
    }

    /*************************************************
     * Stop location updates - https://developer.android.com/develop/sensors-and-location/location/request-updates#stop-updates
     */
    override fun onPause() {
        super.onPause()
        viewModel.mapManager.stopLocationUpdates()
    }

    /*************************************************
     * setupLocationObserver
     */
    private fun setupLocationObserver() {
        locationPermissionObserver = LocationPermissionLifecycleObserver(
            activity = requireActivity(),
            registry = requireActivity().activityResultRegistry,
            callback = callback
        )
        lifecycle.addObserver(locationPermissionObserver)
    }

    /*************************************************
     * for request multiple permissions
     */
    private var showLocationDialog: Boolean by mutableStateOf(false)
    private val callback = object : LocationPermissionLifecycleObserver.Callback {
        override fun onOpenRationaleDialog() {
            showLocationDialog = true
        }

        override fun onRequestPermissionsOneMoreTime() {
            requestLocationPermissions()
        }

        override fun onEnabledAllPermissions() {
            enableGPSBeforeGoNextScreen()
        }
    }

    /*************************************************
     * request Location Permissions
     */
    private fun requestLocationPermissions() {
        val enableAllPermissions = PermissionUtil.hasPermissions(
            context = requireContext(),
            permissions = LocationPermissionLifecycleObserver.mandatoryPermissions
        )

        if (enableAllPermissions) {
            locationPermissionObserver.setAllPermissionsEnabled()
            enableGPSBeforeGoNextScreen()
            return
        } else {
            locationPermissionObserver.launcher.launch(LocationPermissionLifecycleObserver.mandatoryPermissions)
        }
    }

    /*************************************************
     * for enable GPS
     */
    private fun enableGPSBeforeGoNextScreen() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        viewModel.mapManager.getLastKnownLocation(activity = requireActivity(), gpsLauncher = gpsLauncher)
        showToast("Latitude: ${viewModel.latitude.value} - Longitude: ${viewModel.longitude.value}")
    }

    /*************************************************
     * check GPS turn on or turn off
     */
    @SuppressLint("RestrictedApi")
    private val gpsLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // The user has agreed to change the location settings
                Log.d(TAG, "MapManager - gpsLauncher - users have just turned on GPS")
                viewModel.mapManager.startLocationUpdates()

            } else {
                // The user has not agreed to change the location settings
                Log.d(TAG, "MapManager - gpsLauncher - users have denied GPS")
                showGPSDialog = true
            }
        }

    /*************************************************
     * goto Next Screen
     */
    private fun gotoNextScreen() {
        Toast.makeText(requireContext(), "gotoNextScreen", Toast.LENGTH_SHORT).show()
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        LocationLayout(
            onAllow = { requestLocationPermissions() },
            onManual = { gotoNextScreen() },
        )
    }
}

@Composable
fun LocationLayout(
    onAllow: () -> Unit = {},
    onManual: () -> Unit = {},
) {
    CoreLayout(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color(0xFF2770FF),
                            0.5f to Color(0xFF90C6FF),
                            1f to Color(0xFFFFFFFF)
                        )
                    )
                    .statusBarsPadding()
            ) {
                // WORLD MAP
                Image(
                    painter = painterResource(id = R.drawable.img_world_map),
                    contentDescription = stringResource(id = R.string.icon),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4 / 3f)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                )

                // LOCATION ACCESS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 5.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.location_access).uppercase(),
                        style = customizedTextStyle(
                            fontWeight = 800,
                            fontSize = 24,
                            color = Color.White
                        )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                Text(
                    text = stringResource(R.string.to_receive_personalized),
                    textAlign = TextAlign.Center,
                    style = customizedTextStyle(
                        fontWeight = 300, fontSize = 14,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5F)
                )


                // BUTTON ALLOW
                Button(
                    onClick = onAllow,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1165FF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    content = {
                        Text(
                            text = stringResource(id = R.string.allow),
                            style = customizedTextStyle(
                                fontWeight = 600,
                                fontSize = 16,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                )

                Text(
                    text = stringResource(R.string.or)
                        .replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(locale = Locale.getDefault())
                            else it.toString()
                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1165FF),
                    style = customizedTextStyle(fontWeight = 300, fontSize = 16)
                )

                // BUTTON MANUAL
                OutlinedButton(
                    onClick = onManual,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                    border = BorderStroke(width = 1.dp, color = Color(0xFF1165FF)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    content = {
                        Text(
                            text = stringResource(R.string.manual),
                            style = customizedTextStyle(
                                fontWeight = 600,
                                fontSize = 16
                            ),
                            color = Color(0xFF1165FF),
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5F)
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewLocationLayout() {
    LocationLayout()
}
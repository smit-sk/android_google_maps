    package com.example.workingwithgooglemaps

    import android.content.pm.PackageManager
    import androidx.fragment.app.Fragment
    import android.Manifest
    import android.content.IntentSender

    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.core.app.ActivityCompat
    import androidx.core.content.ContextCompat
    import com.google.android.gms.common.api.ResolvableApiException
    import com.google.android.gms.location.FusedLocationProviderClient
    import com.google.android.gms.location.LocationRequest
    import com.google.android.gms.location.LocationServices
    import com.google.android.gms.location.LocationSettingsRequest

    import com.google.android.gms.maps.CameraUpdateFactory
    import com.google.android.gms.maps.GoogleMap
    import com.google.android.gms.maps.MapView
    import com.google.android.gms.maps.OnMapReadyCallback
    import com.google.android.gms.maps.SupportMapFragment
    import com.google.android.gms.maps.model.LatLng
    import com.google.android.gms.maps.model.MarkerOptions

    class MapsFragment : Fragment(), OnMapReadyCallback {
        private lateinit var mapView: MapView
        private lateinit var googleMap: GoogleMap
        private lateinit var fusedLocationClient: FusedLocationProviderClient


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_maps, container, false)

            // Initialize the MapView
            mapView = view.findViewById(R.id.mapView)
            mapView.onCreate(savedInstanceState)

            mapView.getMapAsync(this)

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


            return view
        }



            override fun onMapReady(map: GoogleMap) {
                googleMap = map

                // Check for location permission
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.isMyLocationEnabled = true

                    // Get current location
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            val currentLatLng = LatLng(it.latitude, it.longitude)
                            val markerOptions = MarkerOptions().position(currentLatLng).title("Current Location")
                            googleMap.addMarker(markerOptions)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                        }
                    }
                } else {
                    // Request location permission
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
                val client = LocationServices.getSettingsClient(requireActivity())
                val task = client.checkLocationSettings(builder.build())

                task.addOnSuccessListener {
                    // Location services are enabled, perform your operations here
                }

                task.addOnFailureListener { exception ->
                    if (exception is ResolvableApiException) {
                        try {
                            // Prompt the user to enable location services
                            exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                        } catch (sendEx: IntentSender.SendIntentException) {
                            // Error handling
                        }
                    }
                }
            }



        override fun onResume() {
            super.onResume()
            mapView.onResume()
        }

        override fun onPause() {
            super.onPause()
            mapView.onPause()
        }

        override fun onDestroy() {
            super.onDestroy()
            mapView.onDestroy()
        }

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            mapView.onSaveInstanceState(outState)
        }
        companion object {
            private const val LOCATION_PERMISSION_REQUEST_CODE = 1
            private const val REQUEST_CHECK_SETTINGS = 2
        }
    }
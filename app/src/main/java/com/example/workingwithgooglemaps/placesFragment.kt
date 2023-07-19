package com.example.workingwithgooglemaps
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient


class PlacesFragment : Fragment(){
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var placesListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_places, container, false)
        placesListView = rootView.findViewById(R.id.placesListView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        Places.initialize(requireContext(), "AIzaSyA2brZrarINPiWqnag0spT1P1nwD_klcF4")
        placesClient = Places.createClient(requireContext())
        getCurrentLocationAndFetchPlaces()
    }

    private fun getCurrentLocationAndFetchPlaces() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS)
                    val request = FindCurrentPlaceRequest.newInstance(placeFields)
                    val placeResponse = placesClient.findCurrentPlace(request)
                    placeResponse.addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            val placesList = task.result?.placeLikelihoods?.map { it.place }
                            displayPlaces(placesList)
println(placesList.toString())
                            Log.d("MD", placesList.toString())
                        }
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    private fun displayPlaces(placesList: List<Place>?) {
        val placeNames = placesList?.mapNotNull { it.name } ?: emptyList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, placeNames)
        placesListView.adapter = adapter
    }



    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 100
    }

}


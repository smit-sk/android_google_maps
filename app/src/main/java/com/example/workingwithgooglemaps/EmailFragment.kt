package com.example.workingwithgooglemaps

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class EmailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailAddressEditText = view.findViewById<EditText>(R.id.edt_Email)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val storedEmailAddress = sharedPreferences.getString("Email", null)

        if (!storedEmailAddress.isNullOrEmpty()) {
            emailAddressEditText.setText(storedEmailAddress)
        }

        view.findViewById<Button>(R.id.sendEmailButton).setOnClickListener {
            val emailAddress = emailAddressEditText.text.toString().trim()

            if (isValidEmail(emailAddress)) {
                val subject = "Current Location Details"
                val body = emailBody()

                val editor = sharedPreferences.edit()
                editor.putString("Email", emailAddress)
                Log.d("Email", emailAddress)
                editor.apply()

                sendEmail(emailAddress, subject, body)
            } else {
                Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.backButton_email).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()

        }
    }


    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return regex.matches(email)
    }

    private fun emailBody(): String {
        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val latitude = sharedPreferences.getString("Lat", "Default Value")
        val longitude = sharedPreferences.getString("Lng", "Default Value")
        val address = sharedPreferences.getString("Add", "Default Value")

        return "Latitude: $latitude\nLongitude: $longitude\nAddress: $address"

    }

    private fun sendEmail(emailAddress: String, subject: String, body: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)

        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)

        startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }

}
package com.example.workingwithgooglemaps


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            replaceFragment(MapsFragment())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       return when (item.itemId) {
            R.id.menu_item1 -> {
                replaceFragment(MapsFragment())
                 true
            }
            R.id.menu_item2 -> {
                // Handle Item 2 click
                 true
            }
            R.id.menu_item3 -> {
                // Handle Item 3 click
                 true
            }
            R.id.menu_item4 -> {
                // Handle Item 4 click
                 true
            }
            else ->  false
        }

    }



    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().
        replace(R.id.fragmentContiner, fragment)
            .commit();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                replaceFragment(MapsFragment())
                //aaa bako chhe, marker add nathi thatu
            } else {
                // Location services are still not enabled, handle accordingly
            }
        }
    }


}
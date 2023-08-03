package com.example.workingwithgooglemaps


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            replaceFragment(MapsFragment())
        }
        replaceFragment(MapsFragment())

        val fragmentA = MapsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContiner, fragmentA)
            .addToBackStack(null)
            .commit()
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
                replaceFragment(PlacesFragment())
                 true
            }
            R.id.menu_item3 -> {
                replaceFragment(EmailFragment())
                 true
            }
            R.id.menu_item4 -> {
                replaceFragment(AboutFragment())
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
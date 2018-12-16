package com.ahmednmahran.egoshopping.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.view.fragment.MapFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(){



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadMap()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_checkout -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun loadMap(){
        val mapFragment = MapFragment.newInstance("", "")
        val fragment = supportFragmentManager.findFragmentByTag("MapFragment")
        if(fragment != null)
            supportFragmentManager.beginTransaction().remove(fragment)
        supportFragmentManager.beginTransaction().add(R.id.homeContainer, mapFragment,"MapFragment").commit()
    }

}

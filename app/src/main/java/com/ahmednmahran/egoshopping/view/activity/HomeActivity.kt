package com.ahmednmahran.egoshopping.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ahmednmahran.egoshopping.R
import com.ahmednmahran.egoshopping.controller.navigation.loadCheckout
import com.ahmednmahran.egoshopping.controller.navigation.loadMap
import com.ahmednmahran.egoshopping.controller.navigation.loadPayment
import com.ahmednmahran.egoshopping.controller.settings.AppPreference
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadPayment(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadMap(this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_checkout -> {
                loadCheckout(this)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        if (AppPreference.getInstance().localeLanguage.contains(AppPreference.EN, true))
            menu.findItem(R.id.en).isChecked = true
        else
            menu.findItem(R.id.ar).isChecked = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ar -> {

                reopenActivity(AppPreference.AR)

            }
            R.id.en ->
                reopenActivity(AppPreference.EN)
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reopenActivity(language: String) {
        AppPreference.getInstance().setLocale(language)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreference.getInstance().updateUIperLanguage(this)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.findViewById<View>(R.id.navigation_map).performClick()

    }

}

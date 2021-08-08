package com.disabella.actionbar_and_viewpager_hw11

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.IllegalArgumentException
import com.disabella.actionbar_and_viewpager_hw11.SettingsActivity as SettingsActivity1

class MainActivity : AppCompatActivity() {


    lateinit var viewPager: ViewPager2
    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        val textOfTab1 = findViewById<TextView>(R.id.text_Tab1)
        if (sharedPrefs.contains("signature1")) {
            textOfTab1.text = sharedPrefs.getString("signature1", "your text for tab1")
        }

        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigation.selectedItemId = when (position) {
                    0 -> R.id.page1
                    1 -> R.id.page2
                    else -> throw IllegalArgumentException("Only 2 tabs")
                }
            }
        })


        bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.page1 -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.page2 -> {
                    viewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity1::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Tab1Fragment()
            1 -> Tab2Fragment()
            else -> throw IllegalArgumentException("Only 2 tabs")
        }
    }

}
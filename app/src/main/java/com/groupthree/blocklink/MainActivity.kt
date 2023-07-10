package com.groupthree.blocklink

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.groupthree.blocklink.Market.MarketFragment
import com.groupthree.blocklink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()



        //open market fragment
        val marketFragment = MarketFragment()
        fragmentTransaction.add(R.id.frame_layout, marketFragment)
        fragmentTransaction.commit()
        //

        binding = ActivityMainBinding.inflate(layoutInflater)

    }
}
package com.groupthree.blocklink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.groupthree.blocklink.Market.UI.MarketFragment
import com.groupthree.blocklink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReferenceItems: DatabaseReference
    private lateinit var databaseReferenceUsers: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReferenceItems = database.getReference("items")
        databaseReferenceUsers = database.getReference("users")


        //open market fragment
        val marketFragment = MarketFragment()
        fragmentTransaction.add(R.id.frame_layout, marketFragment)
        fragmentTransaction.commit()
        //

        binding = ActivityMainBinding.inflate(layoutInflater)

    }
}
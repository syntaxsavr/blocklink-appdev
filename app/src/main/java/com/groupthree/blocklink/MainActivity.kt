package com.groupthree.blocklink

import android.content.ContentValues
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.groupthree.blocklink.Market.MarketFragment
import com.groupthree.blocklink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReferenceItems: DatabaseReference
    private lateinit var databaseReferenceUsers: DatabaseReference

    object CurrentUser {
        var username: String = ""

        fun getUser():String {
            return username
        }

        fun setUser(username:String){
            this.username = username
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        database = FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReferenceItems = database.getReference("items")
        databaseReferenceUsers = database.getReference("users")

        databaseReferenceUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //TODO: set CurrentUser.username to logged-in user
                CurrentUser.setUser(dataSnapshot.child("u01").child("username").getValue().toString())
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })



        //open market fragment
        val marketFragment = MarketFragment()
        fragmentTransaction.add(R.id.frame_layout, marketFragment)
        fragmentTransaction.commit()
        //

        binding = ActivityMainBinding.inflate(layoutInflater)

    }
}
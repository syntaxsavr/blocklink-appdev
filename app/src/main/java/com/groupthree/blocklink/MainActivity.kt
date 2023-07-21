package com.groupthree.blocklink

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.*
import com.groupthree.blocklink.Login.LoginActivity
import com.groupthree.blocklink.Market.UI.MarketFragment

/**
 * This class is the main activity of the app. It is the first screen that the user sees when they open the app.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var alertDialog: AlertDialog
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReferenceItems: DatabaseReference
    private lateinit var databaseReferenceUsers: DatabaseReference
    // initialize fragment manager and transaction
    val fragmentManager: FragmentManager = supportFragmentManager
    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()


    override fun onCreate(savedInstanceState: Bundle?) {
        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReferenceItems = database.getReference("items")
        databaseReferenceUsers = database.getReference("users")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase App
        FirebaseApp.initializeApp(this)

        // Get the firebase auth instance
        auth = Firebase.auth

        // Check if the user is logged in
        if (auth.currentUser == null) {
            // The user is not logged in, so open the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    /**
     * Loads the fragment_chat.xml layout into the fragmentContainer
     */
    fun loadFragmentEvents(view: View) {
        var eventsfragment: EventsFragment = EventsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, eventsfragment)
            .commit()
    }

    fun loadFragmentMarket(view: View) {
        var marketFragment: MarketFragment = MarketFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, marketFragment)
            .commit()
    }


    /**
     * Loads an AlertDialog that asks the user if they want to logout
     */
    fun logout(view: View) {
        // Create an AlertDialog.Builder object
        val builder = AlertDialog.Builder(this)

        // Set the title
        builder.setTitle("Logout")

        // Set the message
        builder.setMessage("Are you sure you want to logout?")

        // Set the positive button
        builder.setPositiveButton("Yes") { _, _ ->
            // Logout the user
            auth.signOut()
            Intent (this, LoginActivity::class.java).also {
                startActivity(it)
            }
            finish()
            alertDialog.dismiss()
        }

        // Set the negative button
        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing
            dialog.dismiss()
        }

        // Show the dialog
        alertDialog = builder.create()
        alertDialog.show()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
            }
}
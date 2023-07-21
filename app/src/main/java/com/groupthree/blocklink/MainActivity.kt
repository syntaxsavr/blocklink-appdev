package com.groupthree.blocklink

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * This class is the main activity of the app. It is the first screen that the user sees when they open the app.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Loads the fragment_chat.xml layout into the fragmentContainer
     */
    fun loadFramentChat(view: View) {
        // initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val eventsFragment = EventsFragment()

        // replace the fragment_container with the fragment on click
        fragmentTransaction.replace(R.id.fragmentContainer, eventsFragment)
        fragmentTransaction.commit()
    }

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
            //TODO: Implement logout functionality
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
    }
}
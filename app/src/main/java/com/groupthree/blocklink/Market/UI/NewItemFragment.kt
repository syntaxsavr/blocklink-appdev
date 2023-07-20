package com.groupthree.blocklink.Market.UI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.NewitemFragmentBinding
import java.util.*

class NewItemFragment : Fragment(R.layout.newitem_fragment) {

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var _binding: NewitemFragmentBinding? = null
    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 1
    private val storage = Firebase.storage("gs://group3-appdev-2023.appspot.com")
    private val storageReference = storage.reference
    private var selectedImageUri: Uri? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = NewitemFragmentBinding.inflate(inflater, container, false)


        database =
            FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("items")


        binding.add.setOnClickListener() {
            if (binding.nameText.text.isNullOrBlank() || binding.descriptionText.text.isNullOrBlank() || binding.priceText.text.isNullOrBlank()) {
                val toast = Toast.makeText(
                    requireContext(),
                    "Please enter Name, Description and Price.",
                    Toast.LENGTH_LONG
                )
                toast.show()

            } else {
                var priceDouble = 0.00
                val priceString = binding.priceText.text.toString()
                if (!isDoubleFormat(priceString)) {
                    val toast = Toast.makeText(
                        requireContext(),
                        "Please enter price in following format: \"xxx.xx\".",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                } else {
                    priceDouble = priceString.toDouble()

                    //TODO: replace empty string with current user
                    var item = MarketItem(
                        binding.nameText.text.toString(),
                        binding.descriptionText.text.toString(),
                        priceDouble,
                        ""
                    )

                    var count = 0
                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            count = dataSnapshot.childrenCount.toInt()
                            databaseReference.child("i$count").setValue(item)
                            selectedImageUri?.let { it1 ->
                                storageReference.child("i$count.png").putFile(
                                    it1
                                )
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.i("Firebase", "addListenerForSingleValueEvent: cancelled")
                        }
                    })


                    val marketFragment = MarketFragment()
                    val fragmentTransaction = requireFragmentManager().beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, marketFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }

        binding.cancel.setOnClickListener {
            val marketFragment = MarketFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, marketFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.uploadImage.setOnClickListener {
            openGallery()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.imagePreview.setImageURI(selectedImageUri)
        }
    }

    private fun isDoubleFormat(str: String): Boolean {
        return try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


}
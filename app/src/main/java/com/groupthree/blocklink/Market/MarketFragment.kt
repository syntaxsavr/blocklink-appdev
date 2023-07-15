package com.groupthree.blocklink.Market

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.MarketFragmentBinding
import com.groupthree.blocklink.databinding.NewitemFragmentBinding


class MarketFragment : Fragment(R.layout.market_fragment) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapterMarket
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var _binding: MarketFragmentBinding? = null
    private val binding get() = _binding!!

    var count: Int = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = MarketFragmentBinding.inflate(inflater, container, false)


        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        database = FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("items")
        addItems()


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                count = dataSnapshot.childrenCount.toInt()
                adapter = RecyclerViewAdapterMarket(databaseReference,count)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        databaseReference.addValueEventListener(valueEventListener)

        binding.additem.setOnClickListener{
            val newitemFragment = NewItemFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, newitemFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun addItems() {
        /*var item = MarketItem("Vans", "Farbe: schwarz\nZustand: neuwertig\nGröße: 38", 60.00)
        databaseReference.child("i00").setValue(item)
        item = MarketItem("Jeans", "Farbe: grau\nGröße: 34\nZustand: gebraucht", 69.99)
        databaseReference.child("i01").setValue(item)
        item = MarketItem("iPhone X", "Farbe: schwarz\n128GB\nZustand: gebraucht", 650.00)
        databaseReference.child("i02").setValue(item)
        item = MarketItem("Pullover", "Marke: Adidas\nFarbe: blau\nGröße: 36\nZustand: neuwertig", 49.90)
        databaseReference.child("i03").setValue(item)
        item = MarketItem("Couch", "Farbe: weiß\nMaterial: Textil\nMaße: 200x300x100cm\nZustand: neuwertig", 449.99)
        databaseReference.child("i04").setValue(item)
        item = MarketItem("Fernseher", "Marke: Samsung\nGröße: 55 Zoll\nZustand: gebraucht", 200.99)
        databaseReference.child("i05").setValue(item)
        item = MarketItem("T-Shirt", "Marke: Puma\nGröße: 40\nZustand: neuwertig", 10.50)
        databaseReference.child("i06").setValue(item)
        item = MarketItem("Dell Inspiron 14", "Größe: 14 Zoll\nProzessor: 11th Gen Intel(R) Core(TM) i7-11390H\nRAM: 16GB\nZustand: gebraucht", 500.99)
        databaseReference.child("i07").setValue(item)
        item = MarketItem("Gießkanne", "Farbe: rot\nGröße: 40cm\nZustand: neuwertig", 5.00)
        databaseReference.child("i08").setValue(item)
        item = MarketItem("Lampe", "Marke: Ikea\nGröße: 150cm\nZustand: gebraucht", 200.00)
        databaseReference.child("i09").setValue(item)*/

    }

}
package com.groupthree.blocklink.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.groupthree.blocklink.R


class MarketFragment : Fragment(R.layout.market_fragment) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapterMarket
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.market_fragment, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        database = FirebaseDatabase.getInstance("https://group3-appdev-2023-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.getReference("items")
        addItem()

        adapter = RecyclerViewAdapterMarket(databaseReference)
        recyclerView.adapter = adapter


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun addItem() {
        var item = MarketItem("Vans", "Farbe: schwarz\nZustand: neuwertig\nGröße: 38", 60.00)
        databaseReference.child("items").child("i0").setValue(item)
        item = MarketItem("Jeans", "Farbe: grau\nGröße: 34\nZustand: gebraucht", 69.99)
        databaseReference.child("items").child("i1").setValue(item)
        item = MarketItem("iPhone X", "Farbe: schwarz\n128GB\nZustand: gebraucht", 650.00)
        databaseReference.child("items").child("i2").setValue(item)
        item = MarketItem("Pullover", "Marke: Adidas\nFarbe: blau\nGröße: 36\nZustand: neuwertig", 650.00)
        databaseReference.child("items").child("i3").setValue(item)
        item = MarketItem("Couch", "Farbe: weiß\nMaterial: Textil\nMaße: 200x300x100cm\nZustand: neuwertig", 449.99)
        databaseReference.child("items").child("i4").setValue(item)
        item = MarketItem("Fernseher", "Marke: Samsung\nGröße: 55 Zoll\nZustand: gebraucht", 200.00)
        databaseReference.child("items").child("i5").setValue(item)
        item = MarketItem("T-Shirt", "Marke: Puma\nGröße: 40\nZustand: neuwertig", 10.50)
        databaseReference.child("items").child("i6").setValue(item)
        item = MarketItem("Dell Inspiron 14", "Größe: 14 Zoll\nProzessor: 11th Gen Intel(R) Core(TM) i7-11390H\nRAM: 16GB\nZustand: gebraucht", 500.99)
        databaseReference.child("items").child("i7").setValue(item)
        item = MarketItem("Gießkanne", "Farbe: rot\nGröße: 40cm\nZustand: neuwertig", 5.00)
        databaseReference.child("items").child("i8").setValue(item)
        item = MarketItem("Lampe", "Marke: Ikea\nGröße: 150cm\nZustand: gebraucht", 200.00)
        databaseReference.child("items").child("i9").setValue(item)

    }

}
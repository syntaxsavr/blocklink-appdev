package com.groupthree.blocklink.Market.UI

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.groupthree.blocklink.Market.Logic.RecyclerViewAdapterMarket
import com.groupthree.blocklink.R
import com.groupthree.blocklink.databinding.MarketFragmentBinding


class MarketFragment : Fragment(R.layout.market_fragment) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapterMarket
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReferenceItems: DatabaseReference
    private lateinit var gestureDetector: GestureDetector

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
        databaseReferenceItems = database.getReference("items")
        addItems()


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                count = dataSnapshot.childrenCount.toInt()
                adapter = RecyclerViewAdapterMarket(databaseReferenceItems,count)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        databaseReferenceItems.addValueEventListener(valueEventListener)

        binding.additem.setOnClickListener{
            val newitemFragment = NewItemFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, newitemFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.saved.setOnClickListener{
            val savedFragment = SavedFragment()
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, savedFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val childView = binding.recyclerView.findChildViewUnder(e.x, e.y)
                val position = childView?.let { binding.recyclerView.getChildAdapterPosition(it) }
                if (position != RecyclerView.NO_POSITION) {
                val detailsFragment = ItemDetailsFragment(position)
                val fragmentTransaction = requireFragmentManager().beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, detailsFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                }
                return true
            }
        })

        binding.recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })



        return binding.root
    }


    private fun addItems() {
        var item = MarketItem(
            "Vans", "Farbe: schwarz\nGröße: 38\n" +
                    "Zustand: neuwertig\nMaterial: Leder/Textil", 60.00, "cathrinsc"
        )
        databaseReferenceItems.child("i00").setValue(item)
        item = MarketItem(
            "Jeans",
            "Marke: Levis \nFarbe: grau\nGröße: 34\nSchnitt: Super Skinny / High Waist\nZustand: gebraucht",
            69.99,
            "cathrinsc"
        )
        databaseReferenceItems.child("i01").setValue(item)
        item = MarketItem("iPhone X", "Farbe: schwarz\nSpeicher: 128GB\nZustand: gebraucht", 650.00,"cathrinsc")
        databaseReferenceItems.child("i02").setValue(item)
        item = MarketItem(
            "Pullover",
            "Marke: Adidas\nFarbe: blau\nGröße: 36\nMaterial: Baumwolle\nZustand: neuwertig",
            49.90,
            "cathrinsc"
        )
        databaseReferenceItems.child("i03").setValue(item)
        item = MarketItem(
            "Couch",
            "Farbe: weiß/beige\nMaterial: Textil\nMaße: 200x300x100cm\nZustand: neuwertig",
            449.99,
            "cathrinsc"
        )
        databaseReferenceItems.child("i04").setValue(item)
        item = MarketItem(
            "Fernseher",
            "Marke: Samsung\nModell: Neo QLED 8K QN700C (2023)\nGröße: 65 Zoll\nZustand: gebraucht",
            700.99,
            "cathrinsc"
        )
        databaseReferenceItems.child("i05").setValue(item)
        item = MarketItem(
            "Top",
            "Marke: Puma\nFarbe: blau\nMaterial: Polyester/Viskose\nGröße: 40\nZustand: neuwertig",
            10.50,
            "cathrinsc"
        )
        databaseReferenceItems.child("i06").setValue(item)
        item = MarketItem(
            "Dell Inspiron 14",
            "Display: 14 Zoll\nProzessor: 11th Gen Intel(R) Core(TM) i7-11390H @ 3.40GHz 2.92 GHz\nRAM: 16GB\nSSD: 1TB\nBetriebssystem: Windows 11 Pro\nZustand: gebraucht",
            500.99,
            "cathrinsc"
        )
        databaseReferenceItems.child("i07").setValue(item)
        item = MarketItem(
            "Gießkanne",
            "Farbe: rot\nGröße: 40cm\nMaterial: Plastik\nZustand: neuwertig",
            5.00,
            "cathrinsc"
        )
        databaseReferenceItems.child("i08").setValue(item)
        item = MarketItem(
            "Stehlampe",
            "Marke: Ikea\nGröße: 150cm\nFarbe: Gold/Schwarz\nLampenschirm: Metall\nZustand: gebraucht",
            200.00,
            "cathrinsc"
        )
        databaseReferenceItems.child("i09").setValue(item)

    }

}
package com.adams.travelmantics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adams.travelmantics.models.UserData
import com.adams.travelmantics.models.UserTravels
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_admin.view.*
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.list_items.*
import kotlinx.android.synthetic.main.list_items.view.*
import kotlinx.android.synthetic.main.list_items.view.deal_place_name
import java.util.zip.Inflater

class User : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar!!.title = "Deals"

         fetchData()


}
    fun fetchData(){
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        val uid = FirebaseAuth.getInstance().uid
        val storage = FirebaseDatabase.getInstance().getReference("travels")
        val firebaseRecyclerAdapter = FirebaseRecyclerOptions.Builder<UserTravels>()
            .setQuery(storage,UserTravels::class.java)
            .setLifecycleOwner(this)
            .build()
        val  adapter = object : FirebaseRecyclerAdapter<UserTravels, MyViewHolder>(firebaseRecyclerAdapter){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view= LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_items,parent,false)
                return MyViewHolder(view)

            }


            override fun onBindViewHolder(p0: MyViewHolder, p1: Int, p2: UserTravels) {
                p0.myview.deal_region.text = p2.region
                p0.myview.deal_place_name.text= p2.place
                p0.myview.deal_price.text = p2.price
                if (p2.image.isEmpty()){
                    p0.myview.deal_image.setImageResource(R.drawable.ic_image_black_24dp)

                }else {
                    Picasso.get()
                        .load(p2.image)
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .into(p0.myview.deal_image)
                }





            }

        }
        recycler_user.adapter = adapter
        recycler_user.layoutManager = LinearLayoutManager(this)
        adapter.startListening()






    }
    class MyViewHolder(var myview : View) : RecyclerView.ViewHolder(myview){
      val region = myview.findViewById<View>(R.id.deal_region)
        val price = myview.findViewById<View>(R.id.deal_price)
        val place_name = myview.findViewById<View>(R.id.deal_place_name)
        val image = myview.findViewById<View>(R.id.deal_image)



    }}


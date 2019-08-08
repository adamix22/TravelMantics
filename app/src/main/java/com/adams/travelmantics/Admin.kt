package com.adams.travelmantics

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.adams.travelmantics.models.UserData
import com.adams.travelmantics.models.UserTravels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_admin.*
import java.util.*

class Admin : AppCompatActivity() {
    companion object{
        var currentUser : UserData? = null
        var selectedPhoto : Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        supportActionBar?.title="Add places"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        select_image_button.setOnClickListener {
            val intent =Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

        }

    }

   // private fun saveDetails() {
      //  val place = city_name.text.toString()
      //  val price = price.text.toString()
       // val name_of_place = place_name.text.toString()

    //}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0 && resultCode == Activity.RESULT_OK && data!= null){
            selectedPhoto = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhoto)
            imageView.setImageBitmap(bitmap)
        }
    }
    private fun uploadImage(){
        if (selectedPhoto==null) return
        val uid = UUID.randomUUID().toString()
        val storage = FirebaseStorage.getInstance().getReference("/images/$uid")
        storage.putFile(selectedPhoto!!)
            .addOnSuccessListener {
                storage.downloadUrl.addOnSuccessListener {
                    saveUser(it.toString())



                }

            }
    }

    private fun saveUser(image: String) {
        val uid = FirebaseAuth.getInstance().uid
        val region = city_name.text.toString()
        val price = price.text.toString()
        val place = place_name.text.toString()
        val user = UserTravels(region, price, place,image)
        val storage =   FirebaseDatabase.getInstance().getReference("travels/$uid")
        storage.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"saved successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,User::class.java)
                startActivity(intent)

            }
            .addOnFailureListener{
                Toast.makeText(this, "failed to save, try again later",Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_item -> {
                val region = city_name.text.toString()
                val price = price.text.toString()
                val place = place_name.text.toString()
                if (region.isEmpty() || price.isEmpty() || place.isEmpty()){
                    Toast.makeText(this,"Field(s) cannot be empty",Toast.LENGTH_SHORT).show()
                }
                uploadImage()
            }
            R.id.exit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

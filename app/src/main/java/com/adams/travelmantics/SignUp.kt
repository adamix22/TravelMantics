package com.adams.travelmantics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adams.travelmantics.models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        create_account.setOnClickListener {
            inputs()

        }


    }

    private fun saveToDb() {
        val uid = FirebaseAuth.getInstance().uid
        val email = email.text.toString()
        val name = name.text.toString()
        val storage = FirebaseDatabase.getInstance().getReference("/users/$name").push()
        val user = UserData(email, name)
        storage.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "successfully created new user", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Admin::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
    }

    private fun inputs() {
        val email = email.text.toString()
        val name = name.text.toString()
        val password = password.text.toString()
        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "please Enter the required field", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Toast.makeText(this, "Successfully created user", Toast.LENGTH_SHORT).show()
                saveToDb()
            }
            .addOnFailureListener{
            Toast.makeText(this, "failed to create user", Toast.LENGTH_SHORT).show()
            return@addOnFailureListener
        }

    }


}

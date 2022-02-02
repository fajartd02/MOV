package com.fajar.mov.sign

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fajar.mov.R
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val button_sign = findViewById<Button>(R.id.button_sign)
        button_sign.setOnClickListener {
            // Write a message to the database
            // Write a message to the database
            val database = FirebaseDatabase.getInstance("https://mov-fajar-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val myRef = database.getReference("message")

            myRef.setValue("Hello, World!")
        }
    }
}
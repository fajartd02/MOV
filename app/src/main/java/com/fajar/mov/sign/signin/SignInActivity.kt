package com.fajar.mov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fajar.mov.HomeActivity
import com.fajar.mov.R
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String

    lateinit var mDatabase:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance("https://mov-fajar-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        val button_signIn = findViewById<Button>(R.id.btn_signIn)
        val et_username = findViewById<EditText>(R.id.et_username)
        val et_password = findViewById<EditText>(R.id.et_password)

        button_signIn.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if(iUsername.equals("")) {
                et_username.error = "Silahkan tulis username Anda"
                et_username.requestFocus() // memberi kursor
            } else if(iPassword.equals("")) {
                et_password.error = "Silahkan tulis password Anda"
                et_username.requestFocus() // memberi kursor
            } else {
                pushLogIn(iUsername, iPassword)
            }

        }
    }

    private fun pushLogIn(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message,
                    Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan",
                        Toast.LENGTH_LONG).show()
                } else {
                    if(user.password.equals(iPassword)) {
                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password Anda salah",
                            Toast.LENGTH_LONG).show()
                    }

                }

            }
        })
    }
}
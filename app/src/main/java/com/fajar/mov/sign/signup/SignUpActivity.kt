package com.fajar.mov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.fajar.mov.R
import com.fajar.mov.sign.SignUpPhotoscreenActivity
import com.fajar.mov.sign.signin.User
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sPassword:String
    lateinit var sName:String
    lateinit var sEmail:String

    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var btn_next = findViewById<Button>(R.id.btn_next)
        var next = findViewById<ImageView>(R.id.image_back)
        var et_username = findViewById<EditText>(R.id.et_username)
        var et_password = findViewById<EditText>(R.id.et_password)
        var et_name = findViewById<EditText>(R.id.et_name)
        var et_email = findViewById<EditText>(R.id.et_email)

        mFirebaseInstance = FirebaseDatabase.getInstance("https://mov-fajar-default-rtdb.asia-southeast1.firebasedatabase.app/")
        mDatabase = FirebaseDatabase.getInstance("https://mov-fajar-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btn_next.setOnClickListener {

            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sName = et_name.text.toString()
            sEmail = et_email.text.toString()

            if (sUsername.equals("")) {
                et_username.error = "Silahkan isi username Anda"
                et_username.requestFocus()
            } else if (sPassword.equals("")) {
                et_password.error = "Silahkan isi password Anda"
                et_password.requestFocus()
            } else if (sName.equals("")) {
                et_name.error = "Silahkan isi nama Anda"
                et_name.requestFocus()
            } else if (sEmail.equals("")) {
                et_email.error = "Silahkan isi email Anda"
                et_email.requestFocus()
            } else {
                saveUsername (sUsername, sPassword, sName, sEmail)
            }

        }

    }

    private fun saveUsername(sUsername: String, sPassword: String, sName: String, sEmail: String) {
        var user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sName
        user.email = sEmail

        if(sUsername != null) {
            checkingUsername(sUsername, user)
        }

    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if(user == null) {
                    mDatabaseReference.child(sUsername).setValue(data)

                    var goSignUpPhotoscreen = Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java)
                                                .putExtra("nama", data.nama)
                    startActivity(goSignUpPhotoscreen)

                } else {
                    Toast.makeText(this@SignUpActivity, "User already exist", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "" + databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}

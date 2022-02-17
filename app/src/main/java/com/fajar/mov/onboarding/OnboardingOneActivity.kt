package com.fajar.mov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fajar.mov.R
import com.fajar.mov.sign.signin.SignInActivity
import com.fajar.mov.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference = Preferences(this)

        if(preference.getValues("onboarding").equals("1")) {
            finishAffinity() // supaya ga bisa ke back, saat back langsung out aplikasi
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        val btn_primary = findViewById<Button>(R.id.btn_primary)
        val btn_secondary = findViewById<Button>(R.id.btn_secondary)

        btn_primary.setOnClickListener {
            var intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        btn_secondary.setOnClickListener {

            preference.setValues("onboarding", "1")
            finishAffinity() // supaya ga bisa ke back, saat back langsung out aplikasi
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
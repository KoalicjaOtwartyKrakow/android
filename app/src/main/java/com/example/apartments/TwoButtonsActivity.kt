package com.example.apartments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TwoButtonsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_buttons)

        val registerAptBtn: Button = findViewById(R.id.registerAptBtn)
        registerAptBtn.setOnClickListener {
            val intent = Intent(this, OfferApartmentActivity::class.java)
            startActivity(intent)
        }
        val volunteerEntryBtn: Button = findViewById(R.id.volunteerEntryBtn)
        volunteerEntryBtn.setOnClickListener {

        }
    }
}
package com.example.apartments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ReviewAddedAPTActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_added_a_p_t)

        val preferences = applicationContext.getSharedPreferences(getString(R.string.preferences), 0);
        val apt = preferences.getString(getString(R.string.aptKey), "")
        val reviewText: TextView = findViewById(R.id.review)
        reviewText.text = apt

        val registerAptBtn: Button = findViewById(R.id.registerAptBtn)
        registerAptBtn.setOnClickListener {
            val intent = Intent(this, OfferApartmentActivity::class.java)
            startActivity(intent)
        }
    }
}
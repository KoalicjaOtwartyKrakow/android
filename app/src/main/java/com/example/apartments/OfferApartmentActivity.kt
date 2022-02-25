package com.example.apartments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OfferApartmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_apartment)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://cu2kg3w6c1.execute-api.eu-west-1.amazonaws.com/dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApartmentService = retrofit.create(ApartmentService::class.java)

        val apartmentsList: Call<List<ApartmentDTO>> = service.listApartments()
    }
}
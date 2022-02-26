package com.example.apartments

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val CONTENT_MEDIA_TYPE = "application/json"

class OfferApartmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_apartment)


        val countyNameSpinner: Spinner = findViewById(R.id.county_name)
        countyNameSpinner.adapter = ArrayAdapter<Voivodeship>(
            this,
            android.R.layout.simple_spinner_item,
            Voivodeship.values()
        )
        val city: TextView = findViewById(R.id.city)
        val landlordName: TextView = findViewById(R.id.landlord_name)
        val landlord_street: TextView = findViewById(R.id.landlord_street)
        val landlord_street_number: TextView = findViewById(R.id.landlord_street_number)
        val landlord_phone: TextView = findViewById(R.id.landlord_phone)
        val landlord_email: TextView = findViewById(R.id.landlord_email)
        val description: TextView = findViewById(R.id.description)
        val places_amount: TextView = findViewById(R.id.places_amount)
        val zip: TextView = findViewById(R.id.zip)

        val button: Button = findViewById(R.id.button_id)
        button.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://cu2kg3w6c1.execute-api.eu-west-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: ApartmentService = retrofit.create(ApartmentService::class.java)

            val apartment = ApartmentDTO()
            apartment.CNT_NAME = countyNameSpinner.getSelectedItem().toString()
            apartment.CITY = city.text.toString()
            apartment.LANDLORD_NAME = landlordName.text.toString()
            apartment.ST_NAME = landlord_street.text.toString()
            apartment.ST_NUM = landlord_street_number.text.toString().toIntOrNull() ?: 0
            apartment.LANDLORD_PHONE = landlord_phone.text.toString()
            apartment.LANDLORD_EMAIL = landlord_email.text.toString()
            apartment.DESCRIPTION = description.text.toString()
            apartment.PLACES_NUM = places_amount.text.toString().toIntOrNull() ?: 0
            apartment.ZIP = zip.text.toString()

            postAnApartment(service, apartment)
        }
    }

    private fun getApartmentsList(
        service: ApartmentService,
        textView: TextView
    ) {
        val apartmentsListCall: Call<List<ApartmentDTO>> = service.listApartments()
        apartmentsListCall.enqueue(object : Callback<List<ApartmentDTO>> {

            override fun onResponse(
                call: Call<List<ApartmentDTO>>,
                response: Response<List<ApartmentDTO>>
            ) {
                textView.setText(response.body().toString())
            }

            override fun onFailure(call: Call<List<ApartmentDTO>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun postAnApartment(
        service: ApartmentService,
        apartment: ApartmentDTO
    ) {
        val postApartmentCall: Call<ApartmentDTO> = service.postAnApartment(apartment)
        postApartmentCall.enqueue(object : Callback<ApartmentDTO> {

            override fun onResponse(
                call: Call<ApartmentDTO>,
                response: Response<ApartmentDTO>
            ) {
                try {
                    Log.d("Response", response.body().toString());
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ApartmentDTO>, t: Throwable) {
                println(t.localizedMessage)
            }
        })
    }

    data class ApartmentData(
        val LANDLORD_PHONE: String,
        val ST_NAME: String
    )
}
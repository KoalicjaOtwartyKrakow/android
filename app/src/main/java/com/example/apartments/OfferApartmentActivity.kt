package com.example.apartments

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OfferApartmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_apartment)

        val button: Button = findViewById(R.id.button_id)
        val textView: TextView = findViewById(R.id.text_view_id)
        val landlordName: TextView = findViewById(R.id.landlord_name)
        val landlordStreet: TextView = findViewById(R.id.landlord_street)
        button.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://cu2kg3w6c1.execute-api.eu-west-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: ApartmentService = retrofit.create(ApartmentService::class.java)

            getApartmentsList(service, textView)
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
        textView: TextView,
        apartment: ApartmentDTO
    ) {
        val postApartmentCall: Call<ApartmentDTO> = service.postAnApartment(apartment)
        postApartmentCall.enqueue(object : Callback<ApartmentDTO> {

            override fun onResponse(
                call: Call<ApartmentDTO>,
                response: Response<ApartmentDTO>
            ) {
                textView.setText(response.body().toString())
            }

            override fun onFailure(call: Call<ApartmentDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}
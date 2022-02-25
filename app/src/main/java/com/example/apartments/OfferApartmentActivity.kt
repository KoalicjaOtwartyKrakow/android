package com.example.apartments

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
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

            val apartment = ApartmentDTO()
                apartment.LANDLORD_NAME = landlordName.text.toString()
                apartment.ST_NAME = landlordName.text.toString()

            postAnApartment(service, textView, apartment)
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
        val postApartmentCall: Call<String> = service.postAnApartment(apartment)
        postApartmentCall.enqueue(object : Callback<String> {

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                try {
                    textView.setText(response.body().toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                println(t.localizedMessage)
            }
        })
    }

    data class ApartmentData(
        val LANDLORD_PHONE: String,
        val ST_NAME: String
    )
}
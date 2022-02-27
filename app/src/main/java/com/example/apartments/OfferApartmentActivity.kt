package com.example.apartments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
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
        val landlord_name: TextView = findViewById(R.id.landlord_name)
        val landlord_street: TextView = findViewById(R.id.landlord_street)
        val apt_street_number: TextView = findViewById(R.id.apt_street_number)
        val apt_apt_number: TextView = findViewById(R.id.apt_apt_number)
        val landlord_phone: TextView = findViewById(R.id.landlord_phone)
        val landlord_email: TextView = findViewById(R.id.landlord_email)
        val apt_description: TextView = findViewById(R.id.apt_description)
        val places_amount: TextView = findViewById(R.id.places_amount)
        val apt_zip: TextView = findViewById(R.id.apt_zip)

        val button: Button = findViewById(R.id.button_id)
        button.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://cu2kg3w6c1.execute-api.eu-west-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: ApartmentService = retrofit.create(ApartmentService::class.java)

            //validation
            var valid = true
            if (TextUtils.isEmpty(landlord_name.getText())) {
                landlord_name.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(city.getText())) {
                city.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(landlord_street.getText())) {
                landlord_street.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(apt_street_number.getText())) {
                apt_street_number.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(landlord_phone.getText())) {
                landlord_phone.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(landlord_email.getText())) {
                landlord_email.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(apt_description.getText())) {
                apt_description.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(places_amount.getText())) {
                places_amount.setError("uzupełnij proszę to pole")
                valid = false
            }
            if (TextUtils.isEmpty(apt_zip.getText())) {
                apt_zip.setError("uzupełnij proszę to pole")
                valid = false
            }

            if (valid) {
                //read fields into DTO
                val apartment = ApartmentDTO()
                apartment.CNT_NAME = countyNameSpinner.getSelectedItem().toString()
                apartment.CITY = city.text.toString()
                apartment.LANDLORD_NAME = landlord_name.text.toString()
                apartment.ST_NAME = landlord_street.text.toString()
                apartment.ST_NUM = apt_street_number.text.toString()
                apartment.APT_NUM = apt_apt_number.text.toString()
                apartment.LANDLORD_PHONE = landlord_phone.text.toString()
                apartment.LANDLORD_EMAIL = landlord_email.text.toString()
                apartment.DESCRIPTION = apt_description.text.toString()
                apartment.PLACES_NUM = places_amount.text.toString().toIntOrNull() ?: 0
                apartment.ZIP = apt_zip.text.toString()

                //send
                postAnApartment(service, apartment, this)
            }
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

    private fun nextIntent() {
        finish()
        //open review activity
        val intent = Intent(this, ReviewAddedAPTActivity::class.java)
        startActivity(intent)
    }

    private fun postAnApartment(
        service: ApartmentService,
        apartment: ApartmentDTO,
        activity: Activity
    ) {
        val postApartmentCall: Call<ApartmentDTO> = service.postAnApartment(apartment)
        postApartmentCall.enqueue(object : Callback<ApartmentDTO> {

            override fun onResponse(
                call: Call<ApartmentDTO>,
                response: Response<ApartmentDTO>
            ) {
                try {
                    Log.d("Response", response.body().toString());

                    //save response (with id)
                    val preferences = applicationContext.getSharedPreferences(getString(R.string.preferences),0)
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString(getString(R.string.aptKey), response.body().toString())
                    editor.apply()

                    nextIntent()

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
package com.example.apartments;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApartmentService {
    @GET("/dev/apartments")
    Call<List<ApartmentDTO>> listApartments();

    @POST("/dev/apartments")
    Call<ApartmentDTO> postAnApartment(@Body ApartmentDTO apartmentDTO);
}

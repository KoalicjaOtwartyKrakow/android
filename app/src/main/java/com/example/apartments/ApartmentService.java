package com.example.apartments;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApartmentService {
    @GET("/apartments")
    Call<List<ApartmentDTO>> listApartments();
}

package com.cookey.cardinfofinder.interfaces

import com.cookey.cardinfofinder.model.CardDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    fun getCard(@Path("bin") id: String?): Call<CardDetails>

}
package com.cookey.cardinfofinder.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    fun getCard(@Path("bin") id: String?): Call<CardDetails>

}
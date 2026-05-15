package org.foodshare_android

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/offers")
    fun getOffres(): Call<List<OffreSimple>>
}
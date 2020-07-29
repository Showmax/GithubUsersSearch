package com.showmax.androidcrashcourse.api

import SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowmaxApi {

    @GET("catalogue/search")
    suspend fun search(@Query("q") query: String): SearchResponse

}
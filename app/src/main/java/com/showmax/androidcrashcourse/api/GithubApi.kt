package com.showmax.androidcrashcourse.api

import SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun search(@Query("q") query: String, @Query("per_page") perPage: Int = 100): SearchResponse

}
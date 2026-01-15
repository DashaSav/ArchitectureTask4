package com.example.architecturetask4.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("translate_a/t")
    suspend fun translate(
        @Query("client") client: String = "dict-chrome-ex",
        @Query("sl") sourceLanguage: String,
        @Query("tl") targetLanguage: String,
        @Query("q") text: String
    ): List<String>
}

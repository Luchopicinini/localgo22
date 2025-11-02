package com.localgo.localgo2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val authToken: String)
data class UserResponse(val id: String, val email: String, val name: String?)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @GET("auth/me")
    suspend fun getUser(@Header("Authorization") token: String): UserResponse
}

object ApiClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

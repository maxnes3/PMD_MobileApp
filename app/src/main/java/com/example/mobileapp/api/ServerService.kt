package com.example.mobileapp.api

import com.example.mobileapp.api.model.MailRemote
import com.example.mobileapp.api.model.StoryRemote
import com.example.mobileapp.api.model.UserRemote
import com.example.mobileapp.api.model.UserRemoteSignIn
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerService {
    //USER
    @GET("user/get/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
    ): UserRemote

    @POST("user/signup")
    suspend fun SignUp(
        @Body user: UserRemote,
    ): UserRemote

    @POST("user/signin")
    suspend fun SignIn(
        @Body user: UserRemoteSignIn
    ): UserRemote

    @POST("user/update")
    suspend fun updateUser(
        @Body user: UserRemote
    ): UserRemote

    @GET("user/getAll")
    suspend fun getUsers(): List<UserRemote>

    //STORY
    @GET("story/get/{id}")
    suspend fun getStory(
        @Path("id") id: Int,
    ): StoryRemote

    @GET("story/getAll")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): List<StoryRemote>

    @POST("story/create")
    suspend fun createStory(
        @Body service: StoryRemote,
    ): StoryRemote

    @PUT("story/update/{id}")
    suspend fun updateStory(
        @Path("id") id: Int,
        @Body service: StoryRemote
    ): StoryRemote

    @DELETE("story/delete/{id}")
    suspend fun deleteStory(
        @Path("id") id: Int
    )

    @GET("story/getByUser")
    suspend fun getUserStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("userId") userId: Int,
    ): List<StoryRemote>

    //MAIL
    @GET("mail/get/{id}")
    suspend fun getMail(
        @Path("id") id: Int,
    ): MailRemote

    @GET("mail/getAll")
    suspend fun getMails(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): List<MailRemote>

    @POST("mail/create")
    suspend fun createMail(
        @Body service: MailRemote,
    ): MailRemote

    @PUT("mail/update/{id}")
    suspend fun updateMail(
        @Path("id") id: Int,
        @Body service: MailRemote
    ): MailRemote

    @DELETE("mail/delete/{id}")
    suspend fun deleteMail(
        @Path("id") id: Int
    )

    //INSTANCE
    companion object {
        private const val BASE_URL = "https://7hz21fz1-8080.euw.devtunnels.ms/api/"

        @Volatile
        private var INSTANCE: ServerService? = null

        fun getInstance(): ServerService {
            return INSTANCE ?: synchronized(this) {
                val client = OkHttpClient.Builder()
                    .build()
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                    .build()
                    .create(ServerService::class.java)
                    .also { INSTANCE = it }
            }
        }
    }
}
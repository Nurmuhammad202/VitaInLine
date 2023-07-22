package uz.hayot.vitaInLine.data.remote

import retrofit2.Response
import retrofit2.http.*
import uz.hayot.vitaInLine.data.model.*
import uz.hayot.vitaInLine.data.model.advertising.AdvertisingModel


interface ApiInterface {

    @POST("auth/signup/patient")
    suspend fun createUser(
        @Body data: CreateDataPatient
    ): Response<ResponseCreatePatientModel>

    @POST("auth/signin/patient")
    suspend fun signIn(
        @Body data: SendSigInModel
    ): Response<SiginResponseModel>

    @GET("auth/user")
    suspend fun getUser(@Header("Authorization") authToken: String): Response<UserResponse>

    @GET("healings/patient")
    suspend fun getHealing(
        @Header("Authorization") authToken: String,
        @Query("type") type: String
    ): Response<HealingResponse>

    @GET("recommendations")
    suspend fun recommendations(
        @Header("Authorization") authToken: String,
        @Query("type") type: String
    ): Response<HealingResponse>

    @GET("advertisings")
    suspend fun advertising(): Response<AdvertisingModel>

    @GET("pills/{pillId}")
    suspend fun getPillById(@Path("pillId") variable: String): Response<DataPillModel>

}
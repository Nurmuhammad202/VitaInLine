package uz.hayot.vitaInLine.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.hayot.vitaInLine.data.model.*


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
        @Body type: HealingType
    ): Response<HealingResponse>


}
package uz.hayot.vitaInLine.data.remote

import retrofit2.Response
import retrofit2.http.*
import uz.hayot.vitaInLine.data.model.*
import uz.hayot.vitaInLine.data.model.advertising.AdvertisingModel
import uz.hayot.vitaInLine.data.model.confirmation.GetConfirmationModel
import uz.hayot.vitaInLine.data.model.confirmation.confirmation.ConfirmationModel
import uz.hayot.vitaInLine.data.model.doctorById.ApproveConfirmationModel
import uz.hayot.vitaInLine.data.model.doctorById.DoctorByIdModel


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

    @GET("confirmations/patient")
    suspend fun getConfirmations(@Header("Authorization") authToken: String): Response<GetConfirmationModel>

    @GET("doctors/{id}")
    suspend fun getDoctorById(
        @Path("id") id: String,
        @Header("Authorization") authToken: String,
    ): Response<DoctorByIdModel>

    @PUT("confirmations")
    suspend fun confirmations(
        @Header("Authorization") authToken: String,
        @Body approveConfirmationModel: ApproveConfirmationModel
    ): Response<ConfirmationModel>

}
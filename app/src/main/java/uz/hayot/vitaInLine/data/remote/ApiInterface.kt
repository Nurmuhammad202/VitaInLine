package uz.hayot.vitaInLine.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.hayot.vitaInLine.data.model.CreateDataPatient
import uz.hayot.vitaInLine.data.model.ResponseCreatePatientModel
import uz.hayot.vitaInLine.data.model.SendSigInModel
import uz.hayot.vitaInLine.data.model.SiginResponseModel


interface ApiInterface {

    @POST("auth/signup/patient")
    suspend fun createUser(
        @Body data: CreateDataPatient
    ): Response<ResponseCreatePatientModel>

    @POST("auth/signin/patient")
    suspend fun signIn(
        @Body data: SendSigInModel
    ):Response<SiginResponseModel>


}
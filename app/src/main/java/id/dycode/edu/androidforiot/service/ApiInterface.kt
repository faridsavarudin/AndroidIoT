package id.dycode.edu.androidforiot.service

import id.co.dycode.nb_iot.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("/api/applicationmgt/authenticate")
    fun getAuthenticate() : Call<AuthenticateResponse>

    @POST("/api/usermgt/v1/authenticate")
    fun postLogin(@Body bodyLogin: BodyLogin) : Call<LoginResponse>

}
package com.example.apptestesnode

import retrofit2.Call
import retrofit2.http.*

interface ClientesService {

    @GET("/api/clientes")
    fun getClientes(): Call<List<Cliente>>

    @GET("/api/clientes/{id}")
    fun getClientes(@Path("id") id: Int): Call<Cliente>

    @POST("/api/clientes")
    fun addCliente(@Body newCliente: Cliente): Call<Cliente>

    @PUT("/api/clientes/{id}")
    fun updateCliente(
        @Path("id") id: Int,
        @Body name: Cliente
    ): Call<Cliente>

    @DELETE("/api/clientes/{id}")
    fun deleteCliente(@Path("id") id: Int): Call<Unit>

}
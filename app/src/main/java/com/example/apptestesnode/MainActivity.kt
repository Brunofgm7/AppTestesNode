package com.example.apptestesnode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val listaClientes = ArrayList<Cliente>()
    private lateinit var clientesAdapter: ClientesAdapter
    lateinit var botaoAdicionar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botaoAdicionar = findViewById(R.id.botaoAdicionar)
        botaoAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarCliente::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        carregarClientes()

    }


    private fun carregarClientes(){

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewClientes)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        clientesAdapter = ClientesAdapter(listaClientes)

        val clientesService = ServiceBuilder.buildService(ClientesService::class.java)

        val requestCall = clientesService.getClientes()

        requestCall.enqueue(object : Callback<List<Cliente>> {

            override fun onResponse(call: Call<List<Cliente>>, response: Response<List<Cliente>>) {
                if (response.isSuccessful) {
                    val clientesList = response.body()!!
                    recyclerView.adapter = ClientesAdapter(clientesList)

                } else {
                    Toast.makeText(this@MainActivity, "Erro a carregar os clientes", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<Cliente>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Erro$t", Toast.LENGTH_SHORT).show()
            }

        })
    }

}
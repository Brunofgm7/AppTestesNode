package com.example.apptestesnode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdicionarCliente : AppCompatActivity() {

    lateinit var nomeCliente: EditText
    lateinit var botaoAdicionar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_cliente)

        nomeCliente = findViewById(R.id.editTextNome)
        botaoAdicionar = findViewById(R.id.botaoAdicionar)
        botaoAdicionar.setOnClickListener {
            val newCliente = Cliente(
                nome = nomeCliente.text.toString()
            )

            val clientesService = ServiceBuilder.buildService(ClientesService::class.java)
            val requestCall = clientesService.addCliente(newCliente)

            requestCall.enqueue(object : Callback<Cliente> {

                override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                    if (response.isSuccessful){
                        finish()
                        Toast.makeText(this@AdicionarCliente, "Adicionado com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AdicionarCliente, "Erro a adicionar o cliente", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Cliente>, t: Throwable) {
                    Toast.makeText(this@AdicionarCliente, "Erro$t", Toast.LENGTH_SHORT).show()
                }

            })
        }



    }



}
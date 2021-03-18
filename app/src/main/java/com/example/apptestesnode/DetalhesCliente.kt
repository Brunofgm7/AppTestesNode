package com.example.apptestesnode

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesCliente : AppCompatActivity() {

    lateinit var botaoAtualizar : Button
    lateinit var botaoEliminar : Button
    lateinit var editTextNomeDetalhes : EditText
    lateinit var textViewClienteId: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_cliente)

        botaoAtualizar = findViewById(R.id.botaoAtualizar)
        botaoEliminar = findViewById(R.id.botaoEliminar)
        textViewClienteId = findViewById(R.id.textViewClienteId)

        editTextNomeDetalhes = findViewById(R.id.editTextNomeDetalhes)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            carregarCliente(id)

            AtualizarCliente(id)

            EliminarCliente(id)

        }

    }

    private fun carregarCliente(id: Int) {

        val destinationService = ServiceBuilder.buildService(ClientesService::class.java)
        val requestCall = destinationService.getClientes(id)

        requestCall.enqueue(object: Callback<Cliente> {

            override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                if (response.isSuccessful) {
                    val cliente = response.body()
                    cliente?.let {
                        textViewClienteId.setText("Cliente Id " + cliente.id)
                        editTextNomeDetalhes.setText(cliente.nome)
                    }
                } else {
                    Toast.makeText(this@DetalhesCliente, "Erro a carregar o cliente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cliente>, t: Throwable) {
                Toast.makeText(this@DetalhesCliente, "Erro a carregar o cliente: $t",Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun AtualizarCliente(id: Int) {
        botaoAtualizar.setOnClickListener {
            val newCliente = Cliente(
                    nome = editTextNomeDetalhes.text.toString()
            )
//            val name = editTextNomeDetalhes.text.toString()

            val destinationService = ServiceBuilder.buildService(ClientesService::class.java)
            val requestCall = destinationService.updateCliente(id, newCliente)
            requestCall.enqueue(object: Callback<Cliente> {

                override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(this@DetalhesCliente,"Cliente atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetalhesCliente,"Erro a atualizar o cliente", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Cliente>, t: Throwable) {
                    Toast.makeText(this@DetalhesCliente,"Erro a atualizar o cliente: $t", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun EliminarCliente(id: Int) {
        botaoEliminar.setOnClickListener {
            val destinationService = ServiceBuilder.buildService(ClientesService::class.java)
            val requestCall = destinationService.deleteCliente(id)

            requestCall.enqueue(object: Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(this@DetalhesCliente,"Cliente eliminado com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetalhesCliente,"Erro a apagar o cliente", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@DetalhesCliente,"Erro a apagar o cliente: $t", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
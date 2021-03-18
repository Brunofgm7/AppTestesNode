package com.example.apptestesnode

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClientesAdapter(val listaClientes: List<Cliente>) : RecyclerView.Adapter<ClientesAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun getItemCount(): Int = listaClientes.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentCliente = listaClientes[position]

        holder.textViewNome.text = currentCliente.nome

        holder.itemView.setOnClickListener{ v ->
            val context = v.context
            val intent = Intent(context, DetalhesCliente::class.java)
            intent.putExtra(DetalhesCliente.ARG_ITEM_ID, currentCliente.id)
            context.startActivity(intent)
        }

    }

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNome)
        var cliente: Cliente? = null

    }

}
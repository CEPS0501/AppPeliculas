package com.example.apppeliculas.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apppeliculas.R
import com.example.apppeliculas.data.models.Data

class PeliculaAdapter(val lstPeliculas: MutableList<Data>,
                      private val onEditClick: (Data) -> Unit,
                      private val onDeleteClick: (Data) -> Unit): RecyclerView.Adapter<PeliculaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val binding = LayoutInflater.from(parent.context)
        return PeliculaViewHolder(binding.inflate(R.layout.itempelicula, parent, false))
    }

    override fun getItemCount(): Int = lstPeliculas.size

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val item = lstPeliculas[position]
        holder.bind(item, onEditClick, onDeleteClick)
    }
}
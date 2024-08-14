package com.example.apppeliculas.data.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.apppeliculas.R
import com.example.apppeliculas.data.models.Data
import com.example.apppeliculas.databinding.ItempeliculaBinding
import com.squareup.picasso.Picasso

class PeliculaViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItempeliculaBinding.bind(view)
    private val btnEdit = binding.btnEdit
    private val btnDelete = binding.btnDel

    fun bind(pelicula:Data, onEditClick: (Data) -> Unit, onDeleteClick: (Data) -> Unit) {
        binding.tvName.text = pelicula.titulo
        binding.tvDescription.text = pelicula.sinopsis
        Picasso.get()
            .load(pelicula.image)
            .placeholder(R.drawable.baseline_local_movies_24)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivImage)
        btnEdit.setOnClickListener { onEditClick(pelicula) }
        btnDelete.setOnClickListener { onDeleteClick(pelicula) }

    }

}
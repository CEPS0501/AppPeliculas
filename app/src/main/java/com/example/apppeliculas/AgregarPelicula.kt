package com.example.apppeliculas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.apppeliculas.data.APIService
import com.example.apppeliculas.data.RetrofitBuilder
import com.example.apppeliculas.data.models.Data
import com.example.apppeliculas.databinding.FragmentAgregarPeliculaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


class AgregarPelicula : Fragment() {

    private lateinit var binding: FragmentAgregarPeliculaBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAgregarPeliculaBinding.inflate(layoutInflater)
        initPelicula()
        return binding.root
    }

    private fun initPelicula() {
        binding.btnSave.setOnClickListener {
            savePelicula()
        }
    }

    private fun savePelicula() {
        val context = requireContext()
        CoroutineScope(Dispatchers.IO).launch {
            val pelicula = Data(
                id = 0,
                titulo = binding.tfTitulo.editText?.text?.trim().toString(),
                genero = binding.tfGenero.editText?.text?.trim().toString(),
                sinopsis = binding.tfSinopsis.editText?.text?.trim().toString(),
                clasificacion = binding.tfClasificacion.editText?.text?.trim().toString(),
                duracion = binding.tfDuracion.editText?.text?.trim().toString(),
                director = binding.tfDirector.editText?.text?.trim().toString(),
                image = binding.tfURLimagen.editText?.text?.trim().toString(),
                created_at = Date().toString(),
                updated_at = Date().toString()
            )
            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIService::class.java).savePelicula("peliculas", pelicula)
            val response = call.body()
            requireActivity().runOnUiThread {
                if (call.isSuccessful && response != null) {
                    Toast.makeText(context, "Registro guardado...", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Error al guardar...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}
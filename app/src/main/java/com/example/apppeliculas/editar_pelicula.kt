package com.example.apppeliculas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.apppeliculas.data.APIService
import com.example.apppeliculas.data.RetrofitBuilder
import com.example.apppeliculas.data.models.Data
import com.example.apppeliculas.databinding.FragmentEditarPeliculaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class editar_pelicula : Fragment() {
    private lateinit var binding: FragmentEditarPeliculaBinding
    private var peliculaId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditarPeliculaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments ?: return
        peliculaId = arguments.getInt("city_id")
        loadPelicula(peliculaId)

        binding.btnSave.setOnClickListener {
            updatePelicula()
        }
    }

    private fun updatePelicula() {
        CoroutineScope(Dispatchers.IO).launch {
            val city = Data(
                id = peliculaId,
                titulo = binding.tfTitulo.editText?.text?.trim().toString(),
                genero = binding.tfGenero.editText?.text?.trim().toString(),
                sinopsis = binding.tfSinopsis.editText?.text?.trim().toString(),
                clasificacion = binding.tfClasificacion.editText?.text?.trim().toString(),
                duracion = binding.tfDuracion.editText?.text?.trim().toString(),
                director = binding.tfDirector.editText?.text?.trim().toString(),
                image = binding.tfURLimagen.editText?.text?.trim().toString(),
            )

            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIService::class.java).updatePelicula("peliculas", city.id, city)
            val response = call.body()
            requireActivity().runOnUiThread {
                if(call.isSuccessful && response != null){
                    Toast.makeText(context, "Registro actualizado...", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadPelicula(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val retrofit = RetrofitBuilder.getRetrofit()
                val apiService = retrofit.create(APIService::class.java)
                val response = apiService.showPelicula("peliculas", id)
                if (response.isSuccessful) {
                    val pelicula = response.body()
                    if (pelicula != null) {
                        Log.e("Data", "Pelicula data received: $pelicula")

                        // Actualiza la UI con los datos de la ciudad en el hilo principal
                        binding.tfTitulo.editText?.setText(pelicula.titulo)
                        binding.tfGenero.editText?.setText(pelicula.genero)
                        binding.tfSinopsis.editText?.setText(pelicula.sinopsis)
                        binding.tfClasificacion.editText?.setText(pelicula.clasificacion)
                        binding.tfDuracion.editText?.setText(pelicula.duracion)
                        binding.tfDirector.editText?.setText(pelicula.director)
                        binding.tfURLimagen.editText?.setText(pelicula.image)
                    } else {
                        Toast.makeText(context, "No data found for the pelicula", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                Log.e("Error", "Exception: ${ex.message}")
                Toast.makeText(context, "Error: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        }
    }




}

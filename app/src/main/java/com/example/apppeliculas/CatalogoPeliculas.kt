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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apppeliculas.data.APIService
import com.example.apppeliculas.data.RetrofitBuilder
import com.example.apppeliculas.data.adapter.PeliculaAdapter
import com.example.apppeliculas.data.models.Data
import com.example.apppeliculas.databinding.FragmentCatalogoPeliculasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CatalogoPeliculas : Fragment() {
    private lateinit var binding: FragmentCatalogoPeliculasBinding
    private lateinit var adapter: PeliculaAdapter
    private val listado = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatalogoPeliculasBinding.inflate(inflater, container, false)

        initView()
        initRecyclerView()
        showPeliculas()

        return binding.root
    }

    private fun initView() {
        binding.btnAdd.setOnClickListener {
            goNewPelicula()
        }

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            searchPeliculas(query)
        }
    }

    private fun initRecyclerView() {
        adapter = PeliculaAdapter(listado, this::onEditClick, this::onDeleteClick)

        binding.RvLstPeliculas.layoutManager = LinearLayoutManager(context)
        binding.RvLstPeliculas.adapter = adapter
    }

    private fun onDeleteClick(data: Data) {
        val context = requireContext()

        lifecycleScope.launch {
            try {
                val retrofit = RetrofitBuilder.getRetrofit()
                val call = retrofit.create(APIService::class.java).deletePelicula("peliculas", data.id)
                val response = call.body()
                requireActivity().runOnUiThread {
                    if (call.isSuccessful) {
                        listado.remove(data)
                        adapter.notifyDataSetChanged()
                    } else Toast.makeText(context, "Error al eliminar", Toast.LENGTH_LONG).show()
                    Toast.makeText(context, "Registro eliminado...", Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                Log.e("errojd", ex.toString())
                Toast.makeText(context, "erro ${ex.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onEditClick(data: Data) {
        val bundle = Bundle()
        bundle.putInt("city_id", data.id)
        findNavController().navigate(R.id.action_catalogoPeliculas_to_editar_pelicula2, bundle)
    }

    private fun showPeliculas() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = RetrofitBuilder.getRetrofit()
            val call = retrofit.create(APIService::class.java).listPeliculas("peliculas")
            val response = call.body()
            requireActivity().runOnUiThread {
                if (call.isSuccessful && response != null) {
                    val listaCities = response.data.toMutableList()
                    listado.clear()
                    listado.addAll(listaCities)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("listaciu", "Hubo un error")
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
    }

    private fun goNewPelicula() {
        findNavController().navigate(R.id.action_catalogoPeliculas_to_agregarPelicula)
    }

    private fun searchPeliculas(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofit = RetrofitBuilder.getRetrofit()
                val call = retrofit.create(APIService::class.java).listPeliculas("peliculas", query)
                val response = call.body()
                requireActivity().runOnUiThread {
                    if (call.isSuccessful && response != null) {
                        Log.e("listapeli", "ok")
                        val listaCities = response.data.toMutableList()
                        listado.clear()
                        listado.addAll(listaCities)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.e("listapeli", "Hubo un error")
                        showError()
                    }
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Failure: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

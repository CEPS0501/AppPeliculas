package com.example.apppeliculas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.apppeliculas.databinding.FragmentCatalogoPeliculasBinding
import com.example.apppeliculas.databinding.FragmentMenuPrincipalBinding


class MenuPrincipal : Fragment() {
    private lateinit var binding: FragmentMenuPrincipalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.BtnAgregarPeliculas.setOnClickListener() {
            goNewPelicula()
        }

        binding.BtnCatalogoPeliculas.setOnClickListener(){
            goNewCatologo()
        }
    }

    private fun goNewCatologo() {
        findNavController().navigate(R.id.go_to_CatalogoPeliculas)
    }

    private fun goNewPelicula() {
        findNavController().navigate(R.id.go_to_agregarPelicula)
    }

}
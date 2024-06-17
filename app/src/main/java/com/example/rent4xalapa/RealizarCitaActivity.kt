package com.example.rent4xalapa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.RealizarCitaBinding

class RealizarCitaActivity :AppCompatActivity() {

    private lateinit var binding : RealizarCitaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarCitaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}
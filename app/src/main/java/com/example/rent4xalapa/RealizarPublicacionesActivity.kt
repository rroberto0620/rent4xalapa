package com.example.rent4xalapa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rent4xalapa.databinding.PrincipalPublicacionesBinding
import com.example.rent4xalapa.databinding.RealizarPublicacionesBinding

class RealizarPublicacionesActivity : AppCompatActivity() {
    private lateinit var binding : RealizarPublicacionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RealizarPublicacionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
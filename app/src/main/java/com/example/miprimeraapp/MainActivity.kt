package com.example.miprimeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.miprimeraapp.dosjugadores.DosJugadoresActivity
import com.example.miprimeraapp.unjugador.UnJugadorActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnUnJugador: Button
    private lateinit var btnDosJugadores: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        btnUnJugador = findViewById(R.id.botonUnJugador)
        btnDosJugadores = findViewById(R.id.botonDosJugadores)
    }

    private fun initListeners() {
        btnUnJugador.setOnClickListener {
            val intent = Intent(this, UnJugadorActivity::class.java)
            startActivity(intent)
        }

        btnDosJugadores.setOnClickListener {
            val intent = Intent(this, DosJugadoresActivity::class.java)
            startActivity(intent)
        }
    }
}
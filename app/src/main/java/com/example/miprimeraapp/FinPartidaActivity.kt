package com.example.miprimeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.miprimeraapp.JuegoActivity.Companion.K_NGANADOR
import com.example.miprimeraapp.JuegoActivity.Companion.NUM_COL
import com.example.miprimeraapp.JuegoActivity.Companion.NUM_FILAS
import com.example.miprimeraapp.JuegoActivity.Companion.tablero
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR1
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR2

class FinPartidaActivity : AppCompatActivity() {

    private lateinit var textoGanador: TextView // Muestra empate o jugador ganador
    private lateinit var imagenCentral: ImageView // Imagen central de felicitacion
    private lateinit var btnVolverInicio: Button // Boton para volver a inicio
    private lateinit var btnVolverJugar: Button // Boton para volver a jugar (mismos jugadores)

    private lateinit var nJugador1: String // Nombre del jugador 1
    private lateinit var nJugador2: String // Nombre del jugador 2
    private lateinit var nombreGanador: String // Nombre del jugador ganador (null si hay empate)

    lateinit var tableroFinal: Array<Array<TextView>> // Almacena y visualiza el tablero final

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fin_partida)

        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        textoGanador = findViewById(R.id.textoGanador)
        imagenCentral = findViewById(R.id.imagenCentral)
        btnVolverInicio = findViewById(R.id.botonVolverInicio)
        btnVolverJugar = findViewById(R.id.botonVolverJugar)

        nombreGanador = intent.extras?.getString(K_NGANADOR).toString()
        nJugador1 = intent.extras?.getString(K_NJUGADOR1).toString()
        nJugador2 = intent.extras?.getString(K_NJUGADOR2).toString()

        tableroFinal = Array(NUM_FILAS) { fila ->
            Array(NUM_COL) { columna ->
                findViewById(resources.getIdentifier("c${fila + 1}${columna + 1}", "id", packageName))
            }
        }

    }

    private fun initListeners() {
        btnVolverInicio.setOnClickListener {
            finish()
        }

        btnVolverJugar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initUI() {
        if (nombreGanador.equals("null")) {// Caso de empate
            textoGanador.text =
                "EMPATE ENTRE ${nJugador1.toUpperCase()} y ${nJugador2?.toUpperCase()}"
            imagenCentral.setImageResource(R.drawable.empate)
        } else { // Caso de victoria
            textoGanador.text =
                "HA GANADO EL JUGADOR *${nombreGanador?.toUpperCase()}*"
            imagenCentral.setImageResource(R.drawable.felicidades)
        }
        pintaTablero()
    }

    private fun pintaTablero() {
        for (fila in tablero.indices) {
            for (col in tablero[fila].indices) {
                tableroFinal[fila][col].text = tablero[fila][col].text
                tableroFinal[fila][col].setTextColor(tablero[fila][col].currentTextColor)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, JuegoActivity::class.java)
        intent.putExtra(K_NJUGADOR1, nJugador1)
        intent.putExtra(K_NJUGADOR2, nJugador2)
        startActivity(intent)
        finish()
    }
}
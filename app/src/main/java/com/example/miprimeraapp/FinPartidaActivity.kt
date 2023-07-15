package com.example.miprimeraapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C11
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C12
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C13
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C21
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C22
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C23
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C31
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C32
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_C33
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_NGANADOR
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_TGANADOR
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.K_VALORES_CASILLAS
import com.example.miprimeraapp.dosjugadores.DosJugadoresActivity.Companion.K_NJUGADOR1
import com.example.miprimeraapp.dosjugadores.DosJugadoresActivity.Companion.K_NJUGADOR2
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity

class FinPartidaActivity : AppCompatActivity() {

    private lateinit var textoGanador: TextView // Muestra empate o jugador ganador
    private lateinit var imagenCentral: ImageView // Imagen central de felicitacion
    private lateinit var btnVolverInicio: Button // Boton para volver a inicio
    private lateinit var btnVolverJugar: Button // Boton para volver a jugar (mismos jugadores)

    private lateinit var nJugador1: String // Nombre del jugador 1
    private lateinit var nJugador2: String // Nombre del jugador 2
    private lateinit var nombreGanador: String // Nombre del jugador ganador (null si hay empate)
    private var turnoGanador: Int = 0 // Turno en el que se gano la partida (1 o 2)

    private lateinit var c11: TextView // Casilla (1,1)
    private lateinit var c12: TextView // Casilla (1,2)
    private lateinit var c13: TextView // Casilla (1,3)
    private lateinit var c21: TextView // Casilla (2,1)
    private lateinit var c22: TextView // Casilla (2,2)
    private lateinit var c23: TextView // Casilla (2,3)
    private lateinit var c31: TextView // Casilla (3,1)
    private lateinit var c32: TextView // Casilla (3,2)
    private lateinit var c33: TextView // Casilla (3,3)

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

        turnoGanador = intent.extras?.getInt(K_TGANADOR) ?: 0
        nombreGanador = intent.extras?.getString(K_NGANADOR).toString()
        nJugador1 = intent.extras?.getString(K_NJUGADOR1).toString()
        nJugador2 = intent.extras?.getString(K_NJUGADOR2).toString()

        c11 = findViewById(R.id.c11)
        c12 = findViewById(R.id.c12)
        c13 = findViewById(R.id.c13)
        c21 = findViewById(R.id.c21)
        c22 = findViewById(R.id.c22)
        c23 = findViewById(R.id.c23)
        c31 = findViewById(R.id.c31)
        c32 = findViewById(R.id.c32)
        c33 = findViewById(R.id.c33)
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
                "HA GANADO EL JUGADOR $turnoGanador *${nombreGanador?.toUpperCase()}*"
            imagenCentral.setImageResource(R.drawable.felicidades)
        }
        pintaTablero()
    }

    private fun pintaTablero() {
        val sharedPreferences = getSharedPreferences(K_VALORES_CASILLAS, Context.MODE_PRIVATE)
        c11.text = sharedPreferences.getString(K_C11, "")
        c12.text = sharedPreferences.getString(K_C12, "")
        c13.text = sharedPreferences.getString(K_C13, "")
        c21.text = sharedPreferences.getString(K_C21, "")
        c22.text = sharedPreferences.getString(K_C22, "")
        c23.text = sharedPreferences.getString(K_C23, "")
        c31.text = sharedPreferences.getString(K_C31, "")
        c32.text = sharedPreferences.getString(K_C32, "")
        c33.text = sharedPreferences.getString(K_C33, "")
        if (nombreGanador != "null") buscaFilaGanadora()
    }

    private fun buscaFilaGanadora() {
        // Comprueba filas horizontales
        if (c11.text.toString() == c12.text.toString() && c11.text.toString() == c13.text.toString())
            estableceFilaGanadora(c11, c12, c13)
        if (c21.text.toString() == c22.text.toString() && c21.text.toString() == c23.text.toString())
            estableceFilaGanadora(c21, c22, c23)
        if (c31.text.toString() == c32.text.toString() && c31.text.toString() == c33.text.toString())
            estableceFilaGanadora(c31, c32, c33)

        // Comprueba filas verticales
        if (c11.text.toString() == c21.text.toString() && c11.text.toString() == c31.text.toString())
            estableceFilaGanadora(c11, c21, c31)
        if (c12.text.toString() == c22.text.toString() && c12.text.toString() == c32.text.toString())
            estableceFilaGanadora(c12, c22, c32)
        if (c13.text.toString() == c23.text.toString() && c13.text.toString() == c33.text.toString())
            estableceFilaGanadora(c13, c23, c33)

        // Comprueba filas en diagonal
        if (c11.text.toString() == c22.text.toString() && c11.text.toString() == c33.text.toString())
            estableceFilaGanadora(c11, c22, c33)
        if (c13.text.toString() == c22.text.toString() && c13.text.toString() == c31.text.toString())
            estableceFilaGanadora(c13, c22, c31)
    }

    private fun estableceFilaGanadora(ficha1: TextView, ficha2: TextView, ficha3: TextView) {
        ficha1.setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
        ficha2.setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
        ficha3.setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
    }

    override fun onBackPressed() {
        val intent = Intent(this, JuegoDosJugadoresActivity::class.java)
        intent.putExtra(K_NJUGADOR1, nJugador1)
        intent.putExtra(K_NJUGADOR2, nJugador2)
        startActivity(intent)
        finish()
    }
}
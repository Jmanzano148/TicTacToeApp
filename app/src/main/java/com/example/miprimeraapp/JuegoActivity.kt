package com.example.miprimeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR1
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR2

class JuegoActivity : AppCompatActivity() {

    private val TITULO_TURNO: String = "Turno jugador"

    companion object {

        val NUM_FILAS = 3 // Número de filas del tablero
        val NUM_COL = 3 // Número de columnas del tablero

        var FICHA1: String = "0"
        var FICHA2: String = "X"

        lateinit var tablero: Array<Array<TextView>> // Almacena y visualiza el tablero

        const val K_NGANADOR: String = "NGANADOR"
        const val K_TGANADOR: String = "TGANADOR"
    }

    private lateinit var tituloTurno: TextView // Muestra el turno del jugador

    private lateinit var nJugador1: String // Nombre del jugador 1
    private lateinit var nJugador2: String // Nombre del jugador 2



    private var fichaActual: String = FICHA1 // Almacena la ficha del turno actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        nJugador1 = intent.extras?.getString(K_NJUGADOR1).toString()
        nJugador2 = intent.extras?.getString(K_NJUGADOR2).toString()

        tituloTurno.text = "$TITULO_TURNO $fichaActual $nJugador1"
    }

    private fun initComponents() {
        tituloTurno = findViewById(R.id.turnoJugador)

        tablero = Array(NUM_FILAS) { fila ->
            Array(NUM_COL) { columna ->
                findViewById(resources.getIdentifier("c${fila + 1}${columna + 1}", "id", packageName))
            }
        }

        // Rellena cada TextView con espacios en blanco
        for (fila in tablero.indices) {
            for (columna in tablero[fila].indices) {
                tablero[fila][columna].text = " "
            }
        }

    }

    private fun initListeners() {
        for (fila in tablero.indices) {
            for (col in tablero[fila].indices) {
                tablero[fila][col].setOnClickListener {
                    colocarFicha(fila, col)
                    if(tableroLLeno() || compruebaVictoria())
                        navegarFinPartida()
                    else cambiarTurno()
                }
            }
        }
    }

    private fun colocarFicha(fila: Int, col: Int) {
        tablero[fila][col].text = fichaActual
        tablero[fila][col].isClickable = false
    }

    private fun cambiarTurno() {
        var nombreJugador: String
        if (fichaActual == FICHA1) {
            fichaActual = FICHA2
            nombreJugador = nJugador2
        } else {
            fichaActual = FICHA1
            nombreJugador = nJugador1
        }
        tituloTurno.text = "$TITULO_TURNO $fichaActual $nombreJugador"
    }

    /**
     * Función para verificar si el tablero está lleno (empate)
     */
    private fun tableroLLeno(): Boolean {
        for (i in 0 until NUM_FILAS) {
            for (j in 0 until NUM_COL) {
                if (tablero[i][j].text == " ") {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Función para verificar si un jugador ha ganado
     */
    fun compruebaVictoria(): Boolean {
        for (i in 0 until NUM_FILAS) {
            if (tablero[i][0].text == fichaActual && tablero[i][1].text == fichaActual && tablero[i][2].text == fichaActual) {
                tablero[i][0].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[i][1].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[i][2].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                return true // Ganó en una fila
            }
            if (tablero[0][i].text == fichaActual && tablero[1][i].text == fichaActual && tablero[2][i].text == fichaActual) {
                tablero[0][i].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[1][i].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[2][i].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                return true // Ganó en una columna
            }
        }
        if (tablero[0][0].text == fichaActual && tablero[1][1].text == fichaActual && tablero[2][2].text == fichaActual) {
            tablero[0][0].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            tablero[1][1].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            tablero[2][2].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            return true // Ganó en la diagonal principal
        }
        if (tablero[0][2].text == fichaActual && tablero[1][1].text == fichaActual && tablero[2][0].text == fichaActual) {
            tablero[0][2].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            tablero[1][1].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            tablero[2][0].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            return true // Ganó en la diagonal secundaria
        }
        return false
    }

    private fun navegarFinPartida() {
        var intent = Intent(this, FinPartidaActivity::class.java)
        var nombreGanador: String? = null

        // Si hay victoria
        if (!tableroLLeno()) {
            if (fichaActual == FICHA1) nombreGanador = nJugador1
            else nombreGanador = nJugador2
        }

        intent.putExtra(K_NJUGADOR1, nJugador1)
        intent.putExtra(K_NJUGADOR2, nJugador2)
        intent.putExtra(K_NGANADOR, nombreGanador)
        startActivity(intent)
        finish()
    }

}
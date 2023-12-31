package com.example.miprimeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR1
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR2
import kotlin.random.Random

class JuegoActivity : AppCompatActivity() {

    private val TITULO_TURNO: String = "Turno jugador"

    companion object {

        val NUM_FILAS = 3 // Número de filas del tablero
        val NUM_COL = 3 // Número de columnas del tablero

        var FICHA1: String = "0"
        var FICHA2: String = "X"

        lateinit var tablero: Array<Array<TextView>> // Almacena y visualiza el tablero

        const val K_NGANADOR: String = "NGANADOR"
        const val K_NUMJUGADORES: String = "NUMJUGADORES"
    }

    private lateinit var tituloTurno: TextView // Muestra el turno del jugador

    private lateinit var nJugador1: String // Nombre del jugador 1
    private lateinit var nJugador2: String // Nombre del jugador 2

    private var numJugadores: Int = 1// Indica el número de jugadores humanos
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
        numJugadores = intent.extras?.getInt(K_NUMJUGADORES)!!

        if (nJugador1.isEmpty()) nJugador1 = "1" // Jugador 1 en blanco
        if (nJugador2.equals("null") || nJugador2.isEmpty()) nJugador2 = "2" // Jugador 2 en blanco

        tituloTurno.text = "$TITULO_TURNO $nJugador1"
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
                    if(tableroLLeno() || compruebaVictoria(fichaActual, true))
                        navegarFinPartida()
                    else {
                        cambiarTurno()
                        // Si solo juega un jugador debe colocar la máquina
                        if (numJugadores == 1) {
                            val mejorMovimiento = minimax()
                            val fila = mejorMovimiento[0]
                            val col = mejorMovimiento[1]
                            colocarFicha(fila, col)
                            if(tableroLLeno() || compruebaVictoria(fichaActual, true))
                                navegarFinPartida()
                            else cambiarTurno()
                        }
                    }
                }
            }
        }
    }

    private fun minimax(): IntArray {
        var mejorMovimiento = intArrayOf(-1, -1)
        var mejorPuntuacion = Int.MIN_VALUE

        // Para la primera jugada la máquina juega en una casilla aleatoria
        if (getNumFichasColocadas() == 1) {
            var fila: Int
            var col: Int
            do {
                fila = Random.nextInt(3)
                col = Random.nextInt(3)
            } while (tablero[fila][col].text != " ")
            return intArrayOf(fila, col)
        }

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (tablero[i][j].text == " ") {
                    tablero[i][j].text = FICHA2
                    val puntuacion = minimaxHelper(false)
                    tablero[i][j].text = " " // Deshacer el movimiento

                    if (puntuacion > mejorPuntuacion) {
                        mejorPuntuacion = puntuacion
                        mejorMovimiento = intArrayOf(i, j)
                    }
                }
            }
        }
        return mejorMovimiento
    }

    fun minimaxHelper(estaMaximizando: Boolean): Int {
        // Casos base
        if (compruebaVictoria(FICHA2, false)) return 1
        if (compruebaVictoria(FICHA1, false)) return -1
        if (tableroLLeno()) return 0

        if (estaMaximizando) {
            var mejorPuntuacion = Int.MIN_VALUE
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (tablero[i][j].text == " ") {
                        tablero[i][j].text = FICHA2
                        val puntuacion = minimaxHelper(false)
                        tablero[i][j].text = " " // Deshacer el movimiento
                        mejorPuntuacion = maxOf(puntuacion, mejorPuntuacion)
                    }
                }
            }
            return mejorPuntuacion

        } else {
            var mejorPuntuacion = Int.MAX_VALUE
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (tablero[i][j].text == " ") {
                        tablero[i][j].text = FICHA1
                        val puntuacion = minimaxHelper(true)
                        tablero[i][j].text = " " // Deshacer el movimiento
                        mejorPuntuacion = minOf(puntuacion, mejorPuntuacion)
                    }
                }
            }
            return mejorPuntuacion
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
        tituloTurno.text = "$TITULO_TURNO $nombreJugador"
    }

    private fun getNumFichasColocadas(): Int {
        var fichasColocadas = 0
        for (i in 0 until NUM_FILAS) {
            for (j in 0 until NUM_COL) {
                if (tablero[i][j].text != " ") {
                    fichasColocadas++
                }
            }
        }
        return fichasColocadas
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
    fun compruebaVictoria(ficha: String, pintar: Boolean): Boolean {
        for (i in 0 until NUM_FILAS) {
            if (tablero[i][0].text == ficha && tablero[i][1].text == ficha && tablero[i][2].text == ficha) {
                if (pintar) {
                    tablero[i][0].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                    tablero[i][1].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                    tablero[i][2].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                }
                return true // Ganó en una fila
            }
            if (tablero[0][i].text == ficha && tablero[1][i].text == ficha && tablero[2][i].text == ficha) {
                if (pintar) {
                    tablero[0][i].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                    tablero[1][i].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                    tablero[2][i].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                }
                return true // Ganó en una columna
            }
        }
        if (tablero[0][0].text == ficha && tablero[1][1].text == ficha && tablero[2][2].text == ficha) {
            if (pintar) {
                tablero[0][0].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[1][1].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[2][2].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            }
            return true // Ganó en la diagonal principal
        }
        if (tablero[0][2].text == ficha && tablero[1][1].text == ficha && tablero[2][0].text == ficha) {
            if (pintar) {
                tablero[0][2].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[1][1].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
                tablero[2][0].setTextColor(ContextCompat.getColor(this, R.color.fichaGanadora))
            }
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
        intent.putExtra(K_NUMJUGADORES, numJugadores)
        startActivity(intent)
        finish()
    }

}
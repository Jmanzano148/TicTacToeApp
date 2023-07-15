package com.example.miprimeraapp.dosjugadores

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.miprimeraapp.FinPartidaActivity
import com.example.miprimeraapp.R
import com.example.miprimeraapp.dosjugadores.DosJugadoresActivity.Companion.K_NJUGADOR1
import com.example.miprimeraapp.dosjugadores.DosJugadoresActivity.Companion.K_NJUGADOR2

class JuegoDosJugadoresActivity : AppCompatActivity() {

    private val TITULO_TURNO: String = "Turno jugador"

    companion object {
        var FICHA1: String = "0"
        var FICHA2: String = "X"

        const val K_NGANADOR: String = "NGANADOR"
        const val K_TGANADOR: String = "TGANADOR"
        const val K_TABLERO_JUEGO: String = "TJUEGO"
        const val K_VALORES_CASILLAS: String = "VCASILLAS"
        const val K_C11: String = "C11"
        const val K_C12: String = "C12"
        const val K_C13: String = "C13"
        const val K_C21: String = "C21"
        const val K_C22: String = "C22"
        const val K_C23: String = "C23"
        const val K_C31: String = "C31"
        const val K_C32: String = "C32"
        const val K_C33: String = "C33"
    }

    private lateinit var tituloTurno: TextView // Muestra el turno del jugador
    private lateinit var c11: TextView // Casilla (1,1)
    private lateinit var c12: TextView // Casilla (1,2)
    private lateinit var c13: TextView // Casilla (1,3)
    private lateinit var c21: TextView // Casilla (2,1)
    private lateinit var c22: TextView // Casilla (2,2)
    private lateinit var c23: TextView // Casilla (2,3)
    private lateinit var c31: TextView // Casilla (3,1)
    private lateinit var c32: TextView // Casilla (3,2)
    private lateinit var c33: TextView // Casilla (3,3)

    private lateinit var nJugador1: String // Nombre del jugador 1
    private lateinit var nJugador2: String // Nombre del jugador 2

    private var turno: Int = 1 // Comienza el jugador 1
    private var turnos: Int = 0 // Número de turnos jugados
    private var finalizado: Boolean = false // Indica si ha terminado la partida

    private var tablero: Array<Array<Int>> = arrayOf(
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0),
        arrayOf(0, 0, 0)
    )

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

        tituloTurno.text = "$TITULO_TURNO $turno $nJugador1"
    }

    private fun initComponents() {
        tituloTurno = findViewById(R.id.turnoJugador)
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
        c11.setOnClickListener {
            if (tablero[0][0] == 0) {
                tablero[0][0] = turno // Marco la casilla en el tablero
                turnos++
                c11.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c12.setOnClickListener {
            if (tablero[0][1] == 0) {
                tablero[0][1] = turno // Marco la casilla en el tablero
                turnos++
                c12.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c13.setOnClickListener {
            if (tablero[0][2] == 0) {
                tablero[0][2] = turno // Marco la casilla en el tablero
                turnos++
                c13.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c21.setOnClickListener {
            if (tablero[1][0] == 0) {
                tablero[1][0] = turno // Marco la casilla en el tablero
                turnos++
                c21.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c22.setOnClickListener {
            if (tablero[1][1] == 0) {
                tablero[1][1] = turno // Marco la casilla en el tablero
                turnos++
                c22.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c23.setOnClickListener {
            if (tablero[1][2] == 0) {
                tablero[1][2] = turno // Marco la casilla en el tablero
                turnos++
                c23.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c31.setOnClickListener {
            if (tablero[2][0] == 0) {
                tablero[2][0] = turno // Marco la casilla en el tablero
                turnos++
                c31.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c32.setOnClickListener {
            if (tablero[2][1] == 0) {
                tablero[2][1] = turno // Marco la casilla en el tablero
                turnos++
                c32.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }

        c33.setOnClickListener {
            if (tablero[2][2] == 0) {
                tablero[2][2] = turno // Marco la casilla en el tablero
                turnos++
                c33.text = fichaSegunTurno()
                compruebaFinPartida()
                if (!finalizado) cambiaTurno()
            }
        }
    }

    private fun cambiaTurno() {
        var nombreJugador: String
        if (turno == 1) {
            turno = 2
            nombreJugador = nJugador2
        } else {
            turno = 1
            nombreJugador = nJugador1
        }
        tituloTurno.text = "$TITULO_TURNO $turno $nombreJugador"
    }

    private fun fichaSegunTurno(): String {
        if (turno == 1) return FICHA1
        return FICHA2
    }

    private fun fichaSegunPos(fila: Int, col: Int): String {
        if (tablero[fila - 1][col - 1] == 1) return FICHA1
        else if (tablero[fila - 1][col - 1] == 2) return FICHA2
        return ""
    }

    private fun compruebaFinPartida() {
        // Comprueba si ha ganado alguien
        if ((tablero[0][0] == turno && tablero[0][1] == turno && tablero[0][2] == turno) ||
            (tablero[1][0] == turno && tablero[1][1] == turno && tablero[1][2] == turno) ||
            (tablero[2][0] == turno && tablero[2][1] == turno && tablero[2][2] == turno) ||
            (tablero[0][0] == turno && tablero[1][0] == turno && tablero[2][0] == turno) ||
            (tablero[0][1] == turno && tablero[1][1] == turno && tablero[2][1] == turno) ||
            (tablero[0][2] == turno && tablero[1][2] == turno && tablero[2][2] == turno) ||
            (tablero[0][0] == turno && tablero[1][1] == turno && tablero[2][2] == turno) ||
            (tablero[0][2] == turno && tablero[1][1] == turno && tablero[2][0] == turno)
        ) {
            finalizado = true
            navegarAGanador()
        } else if (turnos == 9) { // Comprueba si hay un empate
            finalizado = true
            navegarAEmpate()
        }
    }

    private fun navegarAGanador() {
        var nombreGanador: String

        if (turno == 1) nombreGanador = nJugador1
        else nombreGanador = nJugador2

        var intent = Intent(this, FinPartidaActivity::class.java)
        intent.putExtra(K_NGANADOR, nombreGanador)
        intent.putExtra(K_TGANADOR, turno)
        intent.putExtra(K_NJUGADOR1, nJugador1)
        intent.putExtra(K_NJUGADOR2, nJugador2)
        intent.putExtra(K_TABLERO_JUEGO, R.id.tableroJuego)
        startActivity(intent)
        finish()
        guardaTablero()
    }

    private fun navegarAEmpate() {
        var intent = Intent(this, FinPartidaActivity::class.java)
        intent.putExtra(K_NJUGADOR1, nJugador1)
        intent.putExtra(K_NJUGADOR2, nJugador2)
        intent.putExtra(K_TABLERO_JUEGO, R.id.tableroJuego)
        startActivity(intent)
        finish()
        guardaTablero()
    }

    private fun guardaTablero() {
        val sharedPreferences = getSharedPreferences(K_VALORES_CASILLAS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(K_C11, fichaSegunPos(1, 1))
        editor.putString(K_C12, fichaSegunPos(1, 2))
        editor.putString(K_C13, fichaSegunPos(1, 3))
        editor.putString(K_C21, fichaSegunPos(2, 1))
        editor.putString(K_C22, fichaSegunPos(2, 2))
        editor.putString(K_C23, fichaSegunPos(2, 3))
        editor.putString(K_C31, fichaSegunPos(3, 1))
        editor.putString(K_C32, fichaSegunPos(3, 2))
        editor.putString(K_C33, fichaSegunPos(3, 3))
        editor.apply()
    }
}
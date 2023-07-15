package com.example.miprimeraapp.dosjugadores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.FICHA1
import com.example.miprimeraapp.dosjugadores.JuegoDosJugadoresActivity.Companion.FICHA2
import com.example.miprimeraapp.R

class DosJugadoresActivity : AppCompatActivity() {

    private val FICHA_TIPO1 = "O"
    private val FICHA_TIPO2 = "X"

    companion object {
        const val K_NJUGADOR1: String = "NJUGADOR1"
        const val K_NJUGADOR2: String = "NJUGADOR2"
    }

    private lateinit var barraNombre1: EditText
    private lateinit var btnFicha1Jug1: CardView
    private lateinit var btnFicha2Jug1: CardView

    private lateinit var barraNombre2: EditText
    private lateinit var btnFicha1Jug2: CardView
    private lateinit var btnFicha2Jug2: CardView

    private lateinit var botonComenzar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dos_jugadores)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        barraNombre1 = findViewById(R.id.barraNombre1)
        btnFicha1Jug1 = findViewById(R.id.selFicha1Jug1)
        btnFicha2Jug1 = findViewById(R.id.selFicha2Jug1)

        barraNombre2 = findViewById(R.id.barraNombre2)
        btnFicha1Jug2 = findViewById(R.id.selFicha1Jug2)
        btnFicha2Jug2 = findViewById(R.id.selFicha2Jug2)

        botonComenzar = findViewById(R.id.botonComenzar)
    }

    private fun initListeners() {
        btnFicha1Jug1.setOnClickListener {
            cambiaFondosFichas(1, 2)
        }

        btnFicha2Jug1.setOnClickListener {
            cambiaFondosFichas(2, 1)
        }

        btnFicha1Jug2.setOnClickListener {
            cambiaFondosFichas(2, 1)
        }

        btnFicha2Jug2.setOnClickListener {
            cambiaFondosFichas(1, 2)
        }

        botonComenzar.setOnClickListener {
            val intent = Intent(this, JuegoDosJugadoresActivity::class.java)
            intent.putExtra(K_NJUGADOR1, barraNombre1.text.toString())
            intent.putExtra(K_NJUGADOR2, barraNombre2.text.toString())
            startActivity(intent)
        }
    }

    private fun cambiaFondosFichas(fichaJug1: Int, fichaJug2: Int) {
        if (fichaJug1 == 1 && fichaJug2 == 2) {
            btnFicha1Jug1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.seleccionado))
            btnFicha2Jug1.setCardBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.sinselecionar
                )
            )
            btnFicha1Jug2.setCardBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.sinselecionar
                )
            )
            btnFicha2Jug2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.seleccionado))
            FICHA1 = FICHA_TIPO1
            FICHA2 = FICHA_TIPO2
        } else {
            btnFicha1Jug1.setCardBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.sinselecionar
                )
            )
            btnFicha2Jug1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.seleccionado))
            btnFicha1Jug2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.seleccionado))
            btnFicha2Jug2.setCardBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.sinselecionar
                )
            )
            FICHA1 = FICHA_TIPO2
            FICHA2 = FICHA_TIPO1
        }
    }
}
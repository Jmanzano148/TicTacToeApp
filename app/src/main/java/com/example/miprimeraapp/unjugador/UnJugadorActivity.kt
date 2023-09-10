package com.example.miprimeraapp.unjugador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.miprimeraapp.R
import com.example.miprimeraapp.JuegoActivity
import com.example.miprimeraapp.MainActivity.Companion.FICHA_TIPO1
import com.example.miprimeraapp.MainActivity.Companion.FICHA_TIPO2
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR1
import com.example.miprimeraapp.MainActivity.Companion.K_NJUGADOR2

class UnJugadorActivity : AppCompatActivity() {

    private lateinit var barraNombre1: EditText
    private lateinit var btnFicha1Jug1: CardView
    private lateinit var btnFicha2Jug1: CardView

    private lateinit var botonComenzar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_un_jugador)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        barraNombre1 = findViewById(R.id.barraNombre1)
        btnFicha1Jug1 = findViewById(R.id.selFicha1Jug1)
        btnFicha2Jug1 = findViewById(R.id.selFicha2Jug1)

        botonComenzar = findViewById(R.id.botonComenzar)
    }

    private fun initListeners() {
        btnFicha1Jug1.setOnClickListener {
            cambiaFondosFichas(1, 2)
        }

        btnFicha2Jug1.setOnClickListener {
            cambiaFondosFichas(2, 1)
        }

        botonComenzar.setOnClickListener {
            val intent = Intent(this, JuegoActivity::class.java)
            intent.putExtra(K_NJUGADOR1, barraNombre1.text.toString())
            intent.putExtra(K_NJUGADOR2, "IA")
            startActivity(intent)
        }
    }

    private fun cambiaFondosFichas(fichaJug1: Int, fichaJug2: Int) {
        if (fichaJug1 == 1 && fichaJug2 == 2) {
            btnFicha1Jug1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.seleccionado))
            btnFicha2Jug1.setCardBackgroundColor(ContextCompat.getColor( this, R.color.sinselecionar))

            JuegoActivity.FICHA1 = FICHA_TIPO1
            JuegoActivity.FICHA2 = FICHA_TIPO2
        } else {
            btnFicha1Jug1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.sinselecionar))
            btnFicha2Jug1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.seleccionado))

            JuegoActivity.FICHA1 = FICHA_TIPO2
            JuegoActivity.FICHA2 = FICHA_TIPO1
        }
    }
}
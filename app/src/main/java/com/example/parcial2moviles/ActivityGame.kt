package com.example.parcial2moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ActivityGame : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttons: Array<Array<Button>>
    private var playerOneTurn = true
    private var roundCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Creamos un arreglo bidimensional para almacenar los valores de cada celda (X | O)
        buttons = Array(3) { i -> Array(3) { j -> findViewById<Button>(resources.getIdentifier("button_$i$j", "id", packageName)) } }
        for (row in buttons) {
            for (button in row) {
                button.setOnClickListener(this) //Asignamos el evento de click con el parametro this, lo cual pasará el boton clickeado para validar el id el cual contiene la posicion de la matriz
            }
        }

        //Funcion donde se resetea el arreglo y los valores de cada celda
        val resetButton: Button = findViewById(R.id.button_reset)
        resetButton.setOnClickListener {
            resetGame()
        }
    }

    //Funcion que regresa a la actividad principal
    fun goToMenu(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Funcion que maneja cada click
    override fun onClick(view: View?) {
        if (view is Button) { //Al asignar una funcion global para cada view, debemos validar si es un boton para poder proceder
            val button = view
            if (button.text.isNotEmpty()) { //Validamos si el texto del boton esta vacío, lo que significa que no se ha clickeado aun
                return //En caso de ya haber sido clickeado, detenemos la funcion
            }
            if (playerOneTurn) { //Valor booleano para validar el turno de cada uno
                button.text = "X"
            } else {
                button.text = "O"
            }
            roundCount++
            if (checkForWin()) { //Revisamos si ya contamos con algun ganador para detener y reiniciar el juego
                if (playerOneTurn) {
                    playerOneWins()
                } else {
                    playerTwoWins()
                }
            } else if (roundCount == 9) { //En caso de que ya se hayan clickeado los 9 botones y no se haya encontrado ganador, reiniciamos y declaramos empate
                draw()
            } else {
                playerOneTurn = !playerOneTurn //Al final de cada click, invertimos el valor de los turnos para poder tener participacion de ambos jugadores
            }
        }
    }

    private fun checkForWin(): Boolean { //Funcion con valor booleano para validar combinaciones ganadoras
        val field = Array(3) { i -> Array(3) { j -> buttons[i][j].text.toString() } }
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0].isNotEmpty()) {
                return true
            }
            if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i].isNotEmpty()) {
                return true
            }
        }
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0].isNotEmpty()) {
            return true
        }
        if (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2].isNotEmpty()) {
            return true
        }
        return false
    }

    private fun playerOneWins() { //Usamos toast para mandar mensaje del jugador ganador
        Toast.makeText(this, "Gana jugador 1!", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun playerTwoWins() { //Usamos toast para mandar mensaje del jugador ganador
        Toast.makeText(this, "Gana Jugador 2!", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun draw() { //Usamos toast para mandar mensaje de empate
        Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun resetGame() { //Reseteamos turno, celdas clickeadas y valores del arreglo
        playerOneTurn = true
        roundCount = 0
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }
    }
}
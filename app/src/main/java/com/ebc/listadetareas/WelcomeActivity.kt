package com.ebc.listadetareas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ebc.listadetareas.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private val consejos = listOf(
        "El éxito es la suma de pequeños esfuerzos repetidos día tras día.",
        "La motivación es lo que te pone en marcha, el hábito es lo que hace que sigas.",
        "No mires el reloj; haz lo que él hace: sigue adelante.",
        "El único lugar donde el éxito viene antes del trabajo es en el diccionario.",
        "No dejes que lo que no puedes hacer interfiera con lo que puedes hacer."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la animación Lottie
        binding.lottieAnimation.setAnimation("welcome_animation.json")
        binding.lottieAnimation.playAnimation()

        // Mostrar un consejo motivacional aleatorio
        val consejoAleatorio = consejos.random()
        binding.tvConsejo.text = consejoAleatorio

        // Configura un temporizador para pasar a MainActivity después de unos segundos
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 10000) // 10 segundos de espera
    }
}



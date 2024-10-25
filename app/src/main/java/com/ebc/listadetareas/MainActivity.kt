package com.ebc.listadetareas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.ebc.listadetareas.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Tarea(val nombre: String, val categoria: String)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tareas = mutableListOf<Tarea>()
    private lateinit var adapter: TareasAdapter
    private val categorias = arrayOf("Personal", "Trabajo", "Estudio", "Otro")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme_Light)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoriasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategorias.adapter = categoriasAdapter

        loadTareas()

        adapter = TareasAdapter(this, tareas)
        binding.listViewTareas.adapter = adapter

        binding.btnAgregar.setOnClickListener {
            val nuevaTarea = binding.etNuevaTarea.text.toString()
            val categoria = binding.spinnerCategorias.selectedItem.toString()
            if (nuevaTarea.isNotEmpty()) {
                val tarea = Tarea(nuevaTarea, categoria)
                tareas.add(tarea)
                saveTareas()
                adapter.notifyDataSetChanged()
                binding.etNuevaTarea.text?.clear()
            }
        }

        binding.listViewTareas.setOnItemClickListener { _, view, position, _ ->
            val checkBoxTarea = view.findViewById<CheckBox>(R.id.cbTarea)
            checkBoxTarea.isChecked = !checkBoxTarea.isChecked
            view.alpha = if (checkBoxTarea.isChecked) 0.5f else 1.0f
        }

        binding.listViewTareas.setOnItemLongClickListener { _, _, position, _ ->
            tareas.removeAt(position)
            saveTareas()
            adapter.notifyDataSetChanged()
            true
        }

        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveTareas() {
        val sharedPreferences = getSharedPreferences("tareas_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(tareas)
        editor.putString("tareas", json)
        editor.apply()
    }

    private fun loadTareas() {
        val sharedPreferences = getSharedPreferences("tareas_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("tareas", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Tarea>>() {}.type
            val savedTareas: MutableList<Tarea> = gson.fromJson(json, type)
            tareas.addAll(savedTareas)
        }
    }
}


package com.ebc.listadetareas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class TareasAdapter(context: Context, private val tareas: MutableList<Tarea>) :
    ArrayAdapter<Tarea>(context, 0, tareas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_tarea, parent, false)
        val tarea = getItem(position)

        if (tarea != null) {
            val textViewTarea = view.findViewById<TextView>(R.id.tvTarea)
            val textViewCategoria = view.findViewById<TextView>(R.id.tvCategoria)
            val checkBoxTarea = view.findViewById<CheckBox>(R.id.cbTarea)

            textViewTarea.text = tarea.nombre
            textViewCategoria.text = tarea.categoria
            checkBoxTarea.isChecked = false

            checkBoxTarea.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    tareas.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        }
        return view
    }
}


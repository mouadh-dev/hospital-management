package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelordonanceReading
import com.example.stagepfe.R
import com.example.stagepfe.entite.MedicamentOrdonance
import com.example.stagepfe.entite.Ordonance

class MyAdapterOrdonanceReading(var mCtx: Context, var resources:Int, var items:List<MedicamentOrdonance>): BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var nameMedicament: TextView = view.findViewById(R.id.name_medicament_read)
        var quantity: TextView = view.findViewById(R.id.quantity_medicament_read)
        var descriptionOrd: TextView = view.findViewById(R.id.description_medicament_reading)


        var mItem: MedicamentOrdonance = items[position]

        nameMedicament.text = mItem.nameMedicament
        quantity.text = mItem.quantity
        descriptionOrd.text = mItem.description

        return view

    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

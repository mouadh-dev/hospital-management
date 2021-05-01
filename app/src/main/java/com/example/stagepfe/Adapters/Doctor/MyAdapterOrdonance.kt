package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelOrdonance
import com.example.stagepfe.R

class MyAdapterOrdonance(var mCtx: Context, var resources:Int, var items:List<ModelOrdonance>): ArrayAdapter<ModelOrdonance>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var nameMedicament: TextView = view.findViewById(R.id.name_medicament_list)
        var quantity: TextView = view.findViewById(R.id.quantity_medicament_list)
        var descriptionOrd: TextView = view.findViewById(R.id.description_ord_list)



        var mItem: ModelOrdonance = items[position]

        nameMedicament.text = mItem.nomMedicament
        quantity.text = mItem.quantity
        descriptionOrd.text = mItem.description

        return view

    }
//     override fun getItem(position: Int): Any {
//        return items[position]
//    }
}
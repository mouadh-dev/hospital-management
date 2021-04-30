package com.example.stagepfe.Adapters.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.Models.Doctors.ModelAddOrdonance
import com.example.stagepfe.Models.Doctors.ModelPatientList
import com.example.stagepfe.R

class MyAdapterAddOrdonance(var mCtx: Context, var resources:Int, var items:List<ModelAddOrdonance>): ArrayAdapter<ModelAddOrdonance>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)


        var nameMedicament: TextView = view.findViewById(R.id.nom_medicament)
        var quantity: TextView = view.findViewById(R.id.quantity_medicament)
        var descriptionOrd: TextView = view.findViewById(R.id.description_ord)



        var mItem: ModelAddOrdonance = items[position]

        nameMedicament.text = mItem.nomMedicament
        quantity.text = mItem.quantity
        descriptionOrd.text = mItem.description

        return view
    }
}
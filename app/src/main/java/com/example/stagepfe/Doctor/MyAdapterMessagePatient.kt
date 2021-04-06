package com.example.stagepfe.Doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.stagepfe.R

class MyAdapterMessagePatient(var mCtx: Context, var resources:Int, var items:List<ModelMessagePatient>): ArrayAdapter<ModelMessagePatient>(mCtx, resources, items)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        var view: View = layoutInflater.inflate(resources, null)

        var imageList: ImageView = view.findViewById(R.id.IVpatientImageList)
        var namePatient: TextView = view.findViewById(R.id.TVnamePatientList)
        var tempsMessager: TextView = view.findViewById(R.id.TvtimeMessagePatient)
        var messagePatient: TextView = view.findViewById(R.id.TVmessagePatientList)

        var mItem: ModelMessagePatient = items[position]
        imageList.setImageDrawable(mCtx.resources.getDrawable(mItem.pic))
        namePatient.text = mItem.nom
        messagePatient.text = mItem.message
        tempsMessager.text = mItem.temps

        return view
    }

}